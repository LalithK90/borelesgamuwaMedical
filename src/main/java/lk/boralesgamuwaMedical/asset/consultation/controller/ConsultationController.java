package lk.boralesgamuwaMedical.asset.consultation.controller;

import lk.boralesgamuwaMedical.asset.consultation.entity.Consultation;
import lk.boralesgamuwaMedical.asset.consultation.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/consultation")
public class ConsultationController {
    private final ConsultationService consultationService;

    @Autowired
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public String consultationPage(Model model) {
        model.addAttribute("consultations", consultationService.findAll());
        return "consultation/consultation";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("consultation", consultationService.findById(id));
        model.addAttribute("addStatus", false);
        return "consultation/addConsultation";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("consultation", new Consultation());
        return "consultation/addConsultation";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @PostMapping(value = {"/save", "/update"})
    public String addConsultation(@Valid @ModelAttribute Consultation consultation, BindingResult result, Model model) {
        if (result.hasErrors()) {

            model.addAttribute("addStatus", false);
            model.addAttribute("consultation", consultation);
            return "consultation/addConsultation";
        }
        consultationService.persist(consultation);
        return "redirect:/consultation";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        consultationService.delete(id);
        return "redirect:/consultation";
    }


}
