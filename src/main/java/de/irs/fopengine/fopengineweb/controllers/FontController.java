package de.irs.fopengine.fopengineweb.controllers;

import de.irs.fopengine.fopengineweb.commands.FontDTO;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Font;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.services.FontService;
import de.irs.fopengine.fopengineweb.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller to handle Font requests
 */
@Slf4j
@Controller
public class FontController {

    /**
     * project service bean
     */
    private final ProjectService projectService;
    /**
     * font service bean
     */
    private final FontService fontService;

    public FontController(ProjectService projectService, FontService fontService) {
        this.projectService = projectService;
        this.fontService = fontService;
    }

    /**
     * Prepare data template viewfont - font details
     * @param id id
     * @param model model
     * @return
     */
    @GetMapping("/font/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        Font font;
        try {
            font = fontService.getFontById(new Long(id));
        } catch (FopEngineException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // TODO handle exception
        } catch (GitException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // TODO handle exception
        }
        log.debug("controller getFontt By Id called. Id=" + id);
        model.addAttribute("font", font);
        return "font/viewfont";
    }

    /**
     * Prapare data for template newfont
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/project/{id}/font/new")
    public String newFont(@PathVariable String id, Model model) {
        Project project;
        try {
            project = projectService.getProjectById(Long.parseLong(id));
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        model.addAttribute("font", new FontDTO());
        model.addAttribute("project", project);
        log.debug("controller new Font called.");
        return "font/newfont";
    }

    /**
     * Create data for template newfont (it is used for editing font)
     * @param id id
     * @param project_id project id
     * @param model model
     * @return newfont template
     */
    @GetMapping("project/{project_id}/font/{id}/edit")
    public String editFont(@PathVariable String id,@PathVariable String project_id, Model model){
        FontDTO fontCommand = null;
        Project project;
        try {
            fontCommand = fontService.getFontCommandById(new Long(id));
            project = projectService.getProjectById(new Long(project_id));
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        model.addAttribute("font", fontCommand);
        model.addAttribute("project", project);
        log.debug("controller edit Font called. Id=" + id);
        return "font/newfont";
    }

    /**
     * Save edited font or create new font
     * @param fontCommand font DTO
     * @param project_id project id
     * @return template to show project with edited font
     */
    @PostMapping("project/{project_id}/font/save")
    public String saveOrUpdateFont(@ModelAttribute FontDTO fontCommand, @PathVariable String project_id) {
        try {
            if (fontCommand.getId() == null) {
                Font newFont = null;

                newFont = fontService.registerNewFont(fontCommand,Long.parseLong(project_id ));

                log.debug("controller new Font called. New Font id = " + newFont.getId());
                return "redirect:/project/"+project_id+"/show";
            } else {
                Font tmp = fontService.getFontById(fontCommand.getId());
                tmp.setFontName(fontCommand.getFontName());
                Font savedFont = fontService.updateFont(tmp);
                log.debug("controller update Font called. Updated Font id = " + savedFont.getId());
                return "redirect:/project/" +project_id+ "/show";
            }
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (XmlException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete font from project
     * @param projectId project id
     * @param id font id
     * @return template to show project with edited font
     */
    @GetMapping("project/{projectId}/font/{id}/delete")
    public String deleteById(@PathVariable String projectId, @PathVariable String id)  {

        log.debug("Deleting font id:" + id);
        try {
            fontService.deleteFontById(Long.valueOf(id));
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "redirect:/project/" + projectId + "/show";
    }
}
