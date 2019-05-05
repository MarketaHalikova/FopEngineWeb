package de.irs.fopengine.fopengineweb.controllers;

import de.irs.fopengine.fopengineweb.commands.TripletDTO;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.model.Font;
import de.irs.fopengine.fopengineweb.model.FontTriplet;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.services.FontService;
import de.irs.fopengine.fopengineweb.services.TripletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

/**
 * Controller for triplet requests
 */
@Slf4j
@Controller
public class TripletController {

    private final FontService fontService;
    private final TripletService tripletService;

    public TripletController(FontService fontService, TripletService tripletService) {
        this.fontService = fontService;
        this.tripletService = tripletService;
    }

    /**
     * Prepare data for a new triplet
     * @param id id of parent font
     * @param model model
     * @return template newtriplet
     */
    @GetMapping("/font/{id}/triplet/new")
    public String newTriplet(@PathVariable String id, Model model) {
        Font font;
        Project project;
        Set<String> absentStyles;
        try {
            font = fontService.getFontById(Long.parseLong(id));
            project = font.getProject();
            absentStyles = font.getAbsentTripletStyles();
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        model.addAttribute("triplet", new TripletDTO());
        model.addAttribute("font", font);
        model.addAttribute("project", project);
        model.addAttribute("absentStyles", absentStyles);
        log.debug("controller new Triplet called.");
        return "font/newtriplet";
    }

    /**
     * Prepare data for editing of a triplet
     * @param id triplet id
     * @param model model
     * @return template edittriplet
     */
    @GetMapping("/triplet/{id}/edit")
    public String editTriplet(@PathVariable String id, Model model){
        TripletDTO tripletCommand = null;
        Font font;
        Project project;
        Set<String> absentStyles;

        try {
            tripletCommand = tripletService.getTripletDTOById(new Long(id));
            FontTriplet triplet = tripletService.getTripletById(new Long(id));
            font = fontService.getFontById(new Long(triplet.getFont().getId()));
            project = font.getProject();
            absentStyles = font.getAbsentTripletStyles();
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        model.addAttribute("triplet", tripletCommand);
        model.addAttribute("font", font);
        model.addAttribute("project", project);
        model.addAttribute("absentStyles", absentStyles);
        log.debug("controller edit Triplet called. Id=" + id);
        return "font/edittriplet";
    }


    /**
     * Delete triplet from project
     * @param id triplet id
     * @return template to show a parent font of deleted triplet
     * @throws FopEngineException
     */
    @GetMapping("/triplet/{id}/delete")
    public String deleteById(@PathVariable String id) throws FopEngineException {

        log.debug("Deleting triplet id:" + id);
        Long fontID = tripletService.getTripletById(Long.valueOf(id)).getFont().getId();
        try {
            tripletService.deleteFontTripletById(Long.valueOf(id));
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "redirect:/font/" + fontID + "/show";
    }

    /**
     * Save edited triplet or create a new one
     * @param tripletCommand TripletDTO
     * @param font_id font id
     * @return template to show font with new/edited triplet
     */
    @PostMapping("font/{font_id}/savetriplet")
    public String saveOrUpdateFont(@ModelAttribute TripletDTO tripletCommand, @PathVariable String font_id) {
        try {
            if (tripletCommand.getId() == null) {
                FontTriplet newTriplet = tripletService.registerNewTriplet(tripletCommand,Long.parseLong(font_id ));
                log.debug("controller new FontTriplet called. New FontTriplet id = " + newTriplet.getId());
                return "redirect:/font/"+font_id+"/show";
            } else {
                FontTriplet tmp = tripletService.getTripletById(tripletCommand.getId());
                tmp.setFontStyle(tripletCommand.getFontStyle());
                tmp.setInddName(tripletCommand.getInddName());
                tmp.setInddStyle(tripletCommand.getInddStyle());
                FontTriplet savedTriplet = tripletService.updateTriplet(tmp);
                log.debug("controller update FontTriplet called. Updated FontTriplet id = " + savedTriplet.getId());
                return "redirect:/font/"+font_id+"/show";
            }
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
