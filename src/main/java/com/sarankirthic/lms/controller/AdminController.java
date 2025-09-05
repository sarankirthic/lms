package com.sarankirthic.lms.controller;

import com.sarankirthic.lms.model.*;
import com.sarankirthic.lms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private LiveLectureService liveLectureService;

    @Autowired
    private StudyMaterialsService studyMaterialsService;
    @Autowired
    private AssignmentsService assignmentsService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;
    @Autowired
    private StuDetailsService stuDetailsService;
    private final String uploadNotificationDir = "notification/";


    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        // Retrieve the authenticated user's details
        String username = principal.getName(); // This gets the logged-in user's username

        // Assuming you have a UserService to fetch user details from the database
        User user = userService.findByUsername(username);

        // Add the user, lecture, and lectureList to the model
        model.addAttribute("user", new User());
        model.addAttribute("user",user);
        model.addAttribute("studentDetails", new StudyMaterials());
        model.addAttribute("studentDetailsList", stuDetailsService.getALlStudentDetails());
        model.addAttribute("userlist", userService.getAllUser());
        model.addAttribute("lecture", new LiveLecture());
        model.addAttribute("lectureList", liveLectureService.getAllLecture());
        model.addAttribute("material", new StudyMaterials());
        model.addAttribute("materiallist", studyMaterialsService.getAllMaterial());
        model.addAttribute("assignment", new Assignments());
        model.addAttribute("assignmentlist", assignmentsService.getAllAssignments());
        model.addAttribute("notification", new Notification());
        model.addAttribute("notificationlist", notificationService.getAllNotification());


        return "admin-dashboard"; // Return the view name
    }

    @GetMapping("/addNotification")
    public String addNotification(Model model){
        model.addAttribute("notification", new Notification());
        return "admnotification";
    }

    @GetMapping("/users")
    public String mangingUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("userList", userService.getAllUser());
        return "admusermgmt";
    }

    @GetMapping("/manage-user")
    public String manageUser(Model model){
        model.addAttribute("studentDetails", new User());
        model.addAttribute("studentDetailsList", stuDetailsService.getALlStudentDetails());
        return "manage-user";
    }

    @GetMapping("/deleteNotification/{id}")
    public String deleteNotification(@PathVariable Long id){
        notificationService.deleteNotification(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/editNotification/{id}")
    public String editNotification(@PathVariable Long id, Model model) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification != null) {
            model.addAttribute("notification", notification);
        } else {
            // Handle the case where the lecture is not found (optional)
        }
        return "admin-dashboard"; // Correct view name
    }

    @GetMapping("/deleteMaterial/{id}")
    public String deleteMaterial(@PathVariable Long id) {
        studyMaterialsService.deleteMaterial(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/deleteUserData/{id}")
    public String deleteUserData(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/deleteAssignment/{id}")
    public String deleteAssignment(@PathVariable Long id) {
        assignmentsService.deleteAssignments(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/deleteStudentData/{id}")
    public String deleteStudentData(@PathVariable Long id){
        stuDetailsService.deleteStudent(id);
        return "redirect:/admin/manage-user";
    }

    @GetMapping("/deleteStuDetails/{id}")
    public String deleteStuDetails(@PathVariable Long id) {
        stuDetailsService.deleteStudent(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/deleteLecture/{id}")
    public String deleteLecture(@PathVariable Long id) {
        liveLectureService.deleteLecture(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/viewLecture/{id}")
    public String viewLecture(@PathVariable Long id, Model model) {
        LiveLecture lecture = liveLectureService.getLectureById(id);
        model.addAttribute("lecture", lecture);
        return "lecture-view"; // Update this with the actual view name for displaying lecture details
    }

    @GetMapping("/admission_form") // Adjust your mapping as needed
    public String showAdmissionForm(Model model) {
        model.addAttribute("studentDetails", new StuDetails()); // Create a new instance of StuDetails
        return "admstdregistration"; // Ensure this matches your Thymeleaf template name
    }

    @PostMapping("/submitWork")
    public String submitWork(
            @ModelAttribute("studentDetails") StuDetails stuDetails,
            RedirectAttributes redirectAttributes) {

        try {
            // Save the work submission data to the database
            stuDetailsService.saveStudentDetails(stuDetails);
            redirectAttributes.addFlashAttribute("message", "Work submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to submit work. Please try again.");
            return "redirect:/admin/admission_form"; // Redirect back to the admission form on failure
        }

        return "redirect:/admin/admission_form"; // Redirect to the admission form on success
    }

    @GetMapping("/registerbyadmin")
    public String registerbyadmin(Model model){
        model.addAttribute("user", new User());
        return "registerbyadmin";

    }

    @PostMapping("/registerbyadmin")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Check if the role is valid
        try {
            Role role = Role.valueOf(user.getRole().toString());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid role selected.");
            return "registerbyadmin";
        }

        // Save the user using the service layer (with password encryption handled in the service)
        userService.saveUser(user);

        // Redirect to the login page after successful registration
        return "redirect:/admin/users";
    }

    @GetMapping("/editLecture/{id}")
    public String editLecture(@PathVariable Long id, Model model) {
        LiveLecture lecture = liveLectureService.getLectureById(id);
        if (lecture != null) {
            model.addAttribute("lecture", lecture);
        } else {
            // Handle the case where the lecture is not found (optional)
        }
        return "admin-dashboard"; // Correct view name
    }

    @GetMapping("/editAssignment/{id}")
    public String editAssignment(@PathVariable Long id, Model model) {
        Assignments assignments = assignmentsService.getAssignmentsById(id);
        if (assignments != null) {
            model.addAttribute("assignment", assignments);
        } else {
            // Handle the case where the lecture is not found (optional)
        }
        return "admin-dashboard"; // Correct view name
    }

    @GetMapping("/editMaterial/{id}")
    public String editMaterial(@PathVariable Long id, Model model) {
        StudyMaterials studyMaterials = studyMaterialsService.getMaterialById(id);
        if (studyMaterials != null) {
            model.addAttribute("material", studyMaterials);
        } else {
            // Handle the case where the lecture is not found (optional)

        }
        return "admin-dashboard"; // Correct view name
    }


    @PostMapping("/saveNotification")
    public String saveLecture(@ModelAttribute("notification") Notification notification,
                              @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            File directory = new File(uploadNotificationDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Create parent directories if needed
            }

            // Correct file path construction
            String filePath = uploadNotificationDir + filename;
            Files.copy(file.getInputStream(), Paths.get(filePath));
            notification.setFilePath(filePath);
        }

        notificationService.saveNotification(notification);
        return "redirect:/admin/addNotification";
    }
}
