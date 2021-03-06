package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.commands.TripletDTO;
import de.irs.fopengine.fopengineweb.enums.ProjectStatus;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.git.GitService;
import de.irs.fopengine.fopengineweb.mappers.FontTripletMapper;
import de.irs.fopengine.fopengineweb.model.Font;
import de.irs.fopengine.fopengineweb.model.FontTriplet;
import de.irs.fopengine.fopengineweb.model.ProjectFileMapper;
import de.irs.fopengine.fopengineweb.model.User;
import de.irs.fopengine.fopengineweb.repositories.FontRepository;
import de.irs.fopengine.fopengineweb.repositories.FontTripletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TripletServiceImpl implements TripletService {

    public static final String COMMIT_MESSAGE = "Commit updated font";
    private final FontTripletRepository fontTripletRepository;
    private final FontRepository fontRepository;
    private final GitService gitService;
    private final XmlService xmlService;
    private final GeneratingService generatingService;

    private User user = new User("testUser", "");

    public TripletServiceImpl(FontTripletRepository fontTripletRepository, FontRepository fontRepository,
                              GitService gitService, XmlService xmlService, GeneratingService generatingService) {
        this.fontTripletRepository = fontTripletRepository;
        this.fontRepository = fontRepository;
        this.gitService = gitService;
        this.xmlService = xmlService;
        this.generatingService = generatingService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFontTripletById(Long id) throws FopEngineException {
        FontTriplet fontTriplet = getTripletById(id);
        fontTripletRepository.deleteById(fontTriplet.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FontTriplet updateTriplet(FontTriplet detachedTriplet) throws FopEngineException {
        Optional<FontTriplet> result = fontTripletRepository.findById(detachedTriplet.getId());
        FontTriplet backupFontTripletFromDatabase = result.orElseThrow(() -> new FopEngineException("FontTriplet not found. Id = "
                + detachedTriplet.getId()));

        try {
            gitService.matchRemote(detachedTriplet.getFont().getProject().getProjectDirectory());
        } catch (GitException e) {
            String message = String.format("Error by matching project %s with git by updating fontTriplet %s",
                    detachedTriplet.getFont().getProject().getProjectName(), detachedTriplet.getInddName());
            log.error(message);
            throw new FopEngineException(message, e);
        }
        try {
            // todo


            ProjectFileMapper metrics = generatingService.generateMetricsFile(detachedTriplet.getFontFiles());
            detachedTriplet.setMetricsFile(metrics);
            xmlService.updateFontsInXml(detachedTriplet.getFont().getProject());
        } catch (XmlException e) {
            String message = String.format("Error by writing to xml of project %s  by updating fontTriplet %s",
                    detachedTriplet.getFont().getProject().getProjectName(), detachedTriplet.getInddName());
            log.error(message);
            throw new FopEngineException(message, e);
        }
        try {
            gitService.commitProject(detachedTriplet.getFont().getProject().getProjectDirectory(), COMMIT_MESSAGE, user);
        } catch (GitException e) {
            String message = String.format("Error by committing project %s  by updating fontTriplet %s",
                    detachedTriplet.getFont().getProject().getProjectName(), detachedTriplet.getInddName());
            log.error(message);
            try {
                xmlService.updateFontsInXml(backupFontTripletFromDatabase.getFont().getProject());
            } catch (XmlException e1) {
                String message1 = String.format("Can't roll back changes in project %s  by updating fontTriplet %s",
                        detachedTriplet.getFont().getProject().getProjectName(), detachedTriplet.getInddName());
                log.error(message1);
                // do nothing. It should be solved by reload.
            }
            throw new FopEngineException(message, e);
        }
        detachedTriplet.getFont().getProject().setProjectStatus(ProjectStatus.CHANGED);
        FontTriplet savedFontTriplet = fontTripletRepository.save(detachedTriplet);
        log.debug("Saved font id: " + savedFontTriplet.getId() + " name:" + savedFontTriplet.getInddName());
        return savedFontTriplet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FontTriplet registerNewTriplet(TripletDTO triplet, Long fontID) throws FopEngineException {
        Optional<Font> result = fontRepository.findById(fontID);
        Font font = result.orElseThrow(() -> new FopEngineException("Font not found. Id = "
                + fontID));
        try {
            gitService.matchRemote(font.getProject().getProjectDirectory());
        } catch (GitException e) {
            String message = "Error by matching project " + font.getProject().getProjectName() + " with git";
            log.error(message);
            throw new FopEngineException(message, e);
        }
        // todo
        ProjectFileMapper file1 = new ProjectFileMapper("to be implemented.ttf", "fonts", "fonts");
        triplet.addFontFile(file1);

        FontTriplet fontTriplet = FontTripletMapper.INSTANCE.fontTripletDTOToFontTriplet(triplet);
        fontTriplet = fontTripletRepository.save(fontTriplet);
        font.addTriplet(fontTriplet);
        try {
            ProjectFileMapper metrics = generatingService.generateMetricsFile(fontTriplet.getFontFiles());
            fontTriplet.setMetricsFile(metrics);
            xmlService.updateFontsInXml(font.getProject());
        } catch (XmlException e) {
            String message = "Error by writing to xml of project " + font.getProject().getProjectName();
            log.error(message);
            throw new FopEngineException(message, e);
        }
        fontRepository.save(font);
        return fontTriplet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FontTriplet getTripletById(Long id) throws FopEngineException {
        Optional<FontTriplet> result = fontTripletRepository.findById(id);
        FontTriplet fontTriplet = result.orElseThrow(() -> new FopEngineException("FontTriplet not found. Id = " + id));
        return fontTriplet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TripletDTO getTripletDTOById(Long id) throws FopEngineException, GitException {
        return FontTripletMapper.INSTANCE.fontTripletToFontTripletDTO(getTripletById(id));
    }
}
