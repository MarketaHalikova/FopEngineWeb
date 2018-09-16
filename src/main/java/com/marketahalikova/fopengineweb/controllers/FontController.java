package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.commands.FontDTO;
import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.services.FontService;
import com.marketahalikova.fopengineweb.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class FontController {

    private final ProjectService projectService;
    private final FontService fontService;

    public FontController(ProjectService projectService, FontService fontService) {
        this.projectService = projectService;

        this.fontService = fontService;
    }



    @GetMapping("/font/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        Font font = null;
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
        log.debug("controller getFonttById called. Id=" + id);
        model.addAttribute("font", font);
        return "font/viewfont";
    }


    @GetMapping("/project/{id}/font/new")
    public String newFont(@PathVariable String id,Model model) {
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
