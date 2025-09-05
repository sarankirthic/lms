package com.sarankirthic.lms.controller;

import com.sarankirthic.lms.model.*;
import com.sarankirthic.lms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private LiveLectureService liveLectureService;

    @Autowired
    private StudyMaterialsService studyMaterialsService;
    @Autowired
    private AssignmentsService assignmentsService;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private StuDetailsService stuDetailsService;
    @Autowired
    private UserService userService;
    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        // Retrieve the authenticated user's details
        String username = principal.getName(); // This gets the logged-in user's username

        // Assuming you have a UserService to fetch user details from the database
        User user = userService.findByUsername(username);

        // Add the user, lecture, and lectureList to the model
        model.addAttribute("user", user);
        model.addAttribute("lecture", new LiveLecture());
        model.addAttribute("studentDetails", new StudyMaterials());
        model.addAttribute("studentDetailsList", stuDetailsService.getALlStudentDetails());

        model.addAttribute("lectureList", liveLectureService.getAllLecture());
        model.addAttribute("material", new StudyMaterials());
        model.addAttribute("materiallist", studyMaterialsService.getAllMaterial());
        model.addAttribute("assignment", new Assignments());
        model.addAttribute("assignmentlist", assignmentsService.getAllAssignments());
        model.addAttribute("notification", new Notification());
        model.addAttribute("notificationlist", notificationService.getAllNotification());
        return "student-dashboard"; // Return the view name
    }
    @GetMapping("/admission_form") // Adjust your mapping as needed
    public String showAdmissionForm(Model model) {
        model.addAttribute("studentDetails", new StuDetails()); // Create a new instance of StuDetails
        return "stdregister"; // Ensure this matches your Thymeleaf template name
    }
    @GetMapping("/notification") // Adjust your mapping as needed
    public String showNotification(Model model) {
        model.addAttribute("notification", new Notification()); // Create a new instance of StuDetails
        model.addAttribute("notificationlist", notificationService.getAllNotification());
        return "notification"; // Ensure this matches your Thymeleaf template name
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
            return "redirect:/student/admission_form"; // Redirect back to the admission form on failure
        }

        return "redirect:/student/admission_form"; // Redirect to the admission form on success
    }





    @GetMapping("/downloadLecture/{id}")
    public ResponseEntity<Resource> downloadLecture(@PathVariable Long id) {
        try {
            LiveLecture liveLecture = liveLectureService.getLectureById(id);
            Path filePath = Paths.get(liveLecture.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/downloadAssignment/{id}")
    public ResponseEntity<Resource> downloadAssignment(@PathVariable Long id) {
        try {
            Assignments assignments = assignmentsService.getAssignmentsById(id);
            Path filePath = Paths.get(assignments.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/downloadMaterial/{id}")
    public ResponseEntity<Resource> downloadMaterial(@PathVariable Long id) {
        try {
            StudyMaterials studyMaterials = studyMaterialsService.getMaterialById(id);
            Path filePath = Paths.get(studyMaterials.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/downloadNotification/{id}")
    public ResponseEntity<Resource> downloadNotification(@PathVariable Long id) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            Path filePath = Paths.get(notification.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(500).build();
        }
    }




}
