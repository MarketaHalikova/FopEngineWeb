package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.commands.FontDTO;
import de.irs.fopengine.fopengineweb.enums.ProjectStatus;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.git.GitService;
import de.irs.fopengine.fopengineweb.mappers.FontMapper;
import de.irs.fopengine.fopengineweb.model.Font;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.model.User;
import de.irs.fopengine.fopengineweb.repositories.FontRepository;
import de.irs.fopengine.fopengineweb.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class FontServiceImpl implements FontService {

    public static final String COMMIT_MESSAGE = "Commit updated font";
    private final FontRepository fontRepository;
    private final ProjectRepository projectRepository;
    private final GitService gitService;
    private final FileSystemService fileSystemService;
    private final XmlService xmlService;
    private User user = new User("testUser", "");

    public FontServiceImpl(FontRepository fontRepository, ProjectRepository projectRepository,
                           GitService gitService, FileSystemService fileSystemService, XmlService xmlService) {
        this.fontRepository = fontRepository;
        this.projectRepository = projectRepository;
        this.gitService = gitService;
        this.fileSystemService = fileSystemService;
        this.xmlService = xmlService;
    }

    @Override
    public Set<Font> getFonts() {
        Set<Font> fonts = new LinkedHashSet<>();
        fonts.addAll(fontRepository.findAllByOrderByFontNameAsc());
        return fonts;
    }

    @Override
    public Font getFontById(Long id) throws FopEngineException {
        Optional<Font> result = fontRepository.findById(id);
        Font font = result.orElseThrow(() -> new FopEngineException("Font not found. Id = " + id));
        return font;
    }

    @Override
    public FontDTO getFontCommandById(Long id) throws FopEngineException {
        return FontMapper.INSTANCE.fontToFontDTO(getFontById(id));
    }

    @Override
    public void deleteFontById(Long id) throws FopEngineException {
        Font font = getFontById(id);
        fontRepository.deleteById(font.getId());
    }

    @Override
    public Font updateFont(Font detachedFont) throws FopEngineException {
        Optional<Font> result = fontRepository.findById(detachedFont.getId());
        Font backupFontFromDatabase = result.orElseThrow(() -> new FopEngineException("Font not found. Id = "
                + detachedFont.getId()));

        try {
            gitService.matchRemote(detachedFont.getProject().getProjectDirectory());
        } catch (GitException e) {
            String message = String.format("Error by matching project %s with git by updating font %s",
                    detachedFont.getProject().getProjectName(), detachedFont.getFontName());
            log.error(message);
            throw new FopEngineException(message, e);
        }
        try {
            xmlService.updateFontsInXml(detachedFont.getProject());
        } catch (XmlException e) {
            String message =  String.format("Error by writing to xml of project %s  by updating font %s",
                    detachedFont.getProject().getProjectName(), detachedFont.getFontName());
            log.error(message);
            throw new FopEngineException(message, e);
        }
        try {
            gitService.commitProject(detachedFont.getProject().getProjectDirectory(),COMMIT_MESSAGE, user);
        } catch (GitException e) {
            String message =  String.format("Error by committing project %s  by updating font %s",
                    detachedFont.getProject().getProjectName(), detachedFont.getFontName());
            log.error(message);
            try {
                xmlService.updateFontsInXml(backupFontFromDatabase.getProject());
            } catch (XmlException e1) {
                String message1 =  String.format("Can't roll back changes in project %s  by updating font %s",
                        detachedFont.getProject().getProjectName(), detachedFont.getFontName());
                log.error(message1);
                // do nothing. It should be solved by reload.
            }
            throw new FopEngineException(message, e);
        }
        detachedFont.getProject().setProjectStatus(ProjectStatus.CHANGED);
        Font savedFont = fontRepository.save(detachedFont);
        log.debug("Saved font id: " + savedFont.getId() +" name:" + savedFont.getFontName());
        return savedFont;
    }

    @Override
    public Font registerNewFont(FontDTO fontCommand, Long projectId) throws FopEngineException {
        Optional<Project> result = projectRepository.findById(projectId);
        Project project = result.orElseThrow(() -> new FopEngineException("Project not found. Id = "
                + projectId));
        try {
            gitService.matchRemote(project.getProjectDirectory());
        } catch (GitException e) {
            String message = "Error by matching project " + project.getProjectName() + " with git";
            log.error(message);
            throw new FopEngineException(message, e);
        }
        Font font = FontMapper.INSTANCE.fontDTOToFont(fontCommand);
        font = fontRepository.save(font);
        project.addFont(font);
        try {
            xmlService.updateFontsInXml(project);
        } catch (XmlException e) {
            String message = "Error by writing to xml of project " + project.getProjectName();
            log.error(message);
            throw new FopEngineException(message, e);
        }
        projectRepository.save(project);
        return font;
    }}