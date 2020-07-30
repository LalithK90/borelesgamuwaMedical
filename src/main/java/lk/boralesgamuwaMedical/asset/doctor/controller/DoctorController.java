package lk.boralesgamuwaMedical.asset.doctor.controller;

import lk.boralesgamuwaMedical.asset.commonAsset.model.Enum.Gender;
import lk.boralesgamuwaMedical.asset.commonAsset.model.Enum.Title;
import lk.boralesgamuwaMedical.asset.consultation.service.ConsultationService;

import lk.boralesgamuwaMedical.asset.doctor.entity.Doctor;
import lk.boralesgamuwaMedical.asset.doctor.service.DoctorService;
import lk.boralesgamuwaMedical.asset.userManagement.service.UserService;
import lk.boralesgamuwaMedical.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final ConsultationService consultationService;

    @Autowired
    public DoctorController(DoctorService doctorService, ConsultationService consultationService, DateTimeAgeService dateTimeAgeService, UserService userService) {
        this.doctorService = doctorService;
        this.consultationService = consultationService;
    }

    @GetMapping
    public String doctorPage(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        return "doctor/doctor";
    }

    @GetMapping("/{id}")
    public String doctorView(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("doctorDetail", doctorService.findById(id));
        return "doctor/doctor-detail";
    }

    @GetMapping("/edit/{id}")
    public String editDoctorFrom(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("doctor", doctorService.findById(id));
        model.addAttribute("addStatus", false);
        model.addAttribute("consultations", consultationService.findAll());
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        return "doctor/addDoctor";
    }

    @GetMapping(value = "/add")
    public String doctorAddFrom(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("consultations", consultationService.findAll());
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        return "doctor/addDoctor";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @PostMapping(value = {"/save", "/update"})
    public String addDoctor(@Valid @ModelAttribute Doctor doctor, BindingResult result, Model model) {

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", true);
            model.addAttribute("consultations", consultationService.findAll());
            model.addAttribute("doctor", doctor);
            model.addAttribute("title", Title.values());
            model.addAttribute("gender", Gender.values());
            return "doctor/addDoctor";
        }

        if (doctor.getId() != null) {
            doctorService.persist(doctor);
            model.addAttribute("addStatus", false);
            return "redirect:/doctor";
        }


        doctorService.persist(doctor);
        return "redirect:/doctor";
    }

    @GetMapping(value = "/remove/{id}")
    public String removeDoctor(@PathVariable Integer id) {
        doctorService.delete(id);
        return "redirect:/doctor";
    }

}
