package lk.boralesgamuwaMedical.asset.patient.controller;

import lk.boralesgamuwaMedical.asset.commonAsset.service.CommonService;
import lk.boralesgamuwaMedical.asset.patient.entity.Patient;
import lk.boralesgamuwaMedical.asset.patient.service.PatientService;
import lk.boralesgamuwaMedical.asset.userManagement.service.UserService;
import lk.boralesgamuwaMedical.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequestMapping("/patient")
@Controller
public class PatientController {

    private final PatientService patientService;
    private final DateTimeAgeService dateTimeAgeService;
    private final CommonService commonService;
    private final UserService userService;

    @Autowired
    public PatientController(PatientService patientService,DateTimeAgeService dateTimeAgeService, CommonService commonService,
                              UserService userService) {
        this.patientService = patientService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.commonService = commonService;
        this.userService = userService;
    }
//----> Employee details management - start <----//

    // Common things for an employee add and update
    private String commonThings(Model model) {
        commonService.commonEmployeeAndOffender(model);
        return "patient/addPatient";
    }


    //Send all employee data
    @RequestMapping
    public String patientPage(Model model) {
        model.addAttribute("patients", patientService.findAll());
        return "patient/patient";
    }

    //Send on employee details
    @GetMapping( value = "/{id}" )
    public String patientView(@PathVariable( "id" ) Integer id, Model model) {
        Patient patient = patientService.findById(id);
        model.addAttribute("patientDetail", patient);
        model.addAttribute("addStatus", false);
        return "patient/patient-detail";
    }

    //Send employee data edit
    @GetMapping( value = "/edit/{id}" )
    public String editPatientForm(@PathVariable( "id" ) Integer id, Model model) {
        Patient patient = patientService.findById(id);
        model.addAttribute("patient", patient);
        model.addAttribute("newPatient", patient.getCode());
        model.addAttribute("addStatus", false);
        return commonThings(model);
    }

    //Send an employee add form
    @GetMapping( value = {"/add"} )
    public String patientAddForm(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("patient", new Patient());
        return commonThings(model);
    }

    //Employee add and update
    @PostMapping( value = {"/save", "/update"} )
    public String addPatient(@Valid @ModelAttribute Patient patient, BindingResult result, Model model
    ) {

        if ( result.hasErrors() ) {
            model.addAttribute("addStatus", true);
            model.addAttribute("patient", patient);
            return commonThings(model);
        }

        patient.setMobileOne(commonService.commonMobileNumberLengthValidator(patient.getMobileOne()));
        patient.setMobileTwo(commonService.commonMobileNumberLengthValidator(patient.getMobileTwo()));
        //after save employee files and save employee
        patientService.persist(patient);


        return "redirect:/patient";
    }

    //If need to employee {but not applicable for this }
    @GetMapping( value = "/remove/{id}" )
    public String removePatient(@PathVariable Integer id) {
        patientService.delete(id);
        return "redirect:/patient";
    }

    //To search employee any giving employee parameter
    @GetMapping( value = "/search" )
    public String search(Model model, Patient patient) {
        model.addAttribute("patientDetail", patientService.search(patient));
        return "patient/patient-detail";
    }


}

