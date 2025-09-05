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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

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
    private final String uploadDir = "lecture/"; // Ensure this path is accessible
    private final String uploadMaterialDir = "material/";
    private final String uploadAssignmentDir = "assignment/";
    private final String uploadNotificationDir = "notification/";

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        // Retrieve the authenticated user's details
        String username = principal.getName(); // This gets the logged-in user's username

        // Assuming you have a UserService to fetch user details from the database
        User user = userService.findByUsername(username);

        // Add the user, lecture, and lectureList to the model
        model.addAttribute("user", user);
        model.addAttribute("lecture", new LiveLecture());
        model.addAttribute("lectureList", liveLectureService.getAllLecture());
        model.addAttribute("material", new StudyMaterials());
        model.addAttribute("materialList", studyMaterialsService.getAllMaterial());
        model.addAttribute("assignment", new Assignments());
        model.addAttribute("assignmentList", assignmentsService.getAllAssignments());
        model.addAttribute("notification", new Notification());
        model.addAttribute("notificationList", notificationService.getAllNotification());


        return "instructor-dashboard";
    }

    public String getFormattedDeadline(String deadlineString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(deadlineString, formatter);
        return deadline.format(formatter);
    }

    @GetMapping("/addLecture")
    public String addLecture(Model model) {
        model.addAttribute("lecture", new LiveLecture());
        return "instructor-dashboard"; // Correct view name
    }


    @GetMapping("/addMaterial")
    public String addMaterial(Model model){
        model.addAttribute("material", new StudyMaterials());
        return "instructor-dashboard";
    }
    @GetMapping("/addNotification")
    public String addNotification(Model model){
        model.addAttribute("notification", new Notification());
         return "instructor-dashboard";
    }

    @GetMapping("/addAssignment")
    public String addAssignment(Model model){
        model.addAttribute("assignment", new Assignments());
        return "instructor-dashboard";
    }

    @PostMapping("/saveLecture")
    public String saveLecture(@ModelAttribute("lecture") LiveLecture liveLecture,
                              @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Create parent directories if needed
            }

            // Correct file path construction
            String filePath = uploadDir + filename;
            Files.copy(file.getInputStream(), Paths.get(filePath));
            liveLecture.setFilePath(filePath);
        }

        liveLectureService.saveLecture(liveLecture);
        return "redirect:/instructor/dashboard";
    }


    @PostMapping("/saveMaterial")
    public String saveLecture(@ModelAttribute("material") StudyMaterials studyMaterials,
                              @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            File directory = new File(uploadMaterialDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Create parent directories if needed
            }

            // Correct file path construction
            String filePath = uploadMaterialDir + filename;
            Files.copy(file.getInputStream(), Paths.get(filePath));
            studyMaterials.setFilePath(filePath);
        }

        studyMaterialsService.saveMaterial(studyMaterials);
        return "redirect:/instructor/dashboard";
    }


    @PostMapping("/saveAssignment")
    public String saveLecture(@ModelAttribute("assignment") Assignments assignments,
                              @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            File directory = new File(uploadAssignmentDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Create parent directories if needed
            }

            // Correct file path construction
            String filePath = uploadAssignmentDir + filename;
            Files.copy(file.getInputStream(), Paths.get(filePath));
            assignments.setFilePath(filePath);
        }

        assignmentsService.saveAssignments(assignments);
        return "redirect:/instructor/dashboard";
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
        return "redirect:/instructor/dashboard";
    }



    @GetMapping("/editLecture/{id}")
    public String editLecture(@PathVariable Long id, Model model) {
        LiveLecture lecture = liveLectureService.getLectureById(id);
        if (lecture != null) {
            model.addAttribute("lecture", lecture);
        } else {
            // Handle the case where the lecture is not found (optional)
        }
        return "instructor-dashboard"; // Correct view name
    }


    @GetMapping("/editNotification/{id}")
    public String editNotification(@PathVariable Long id, Model model) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification != null) {
            model.addAttribute("notification", notification);
        } else {
            // Handle the case where the lecture is not found (optional)
        }
        return "instructor-dashboard"; // Correct view name
    }

    @GetMapping("/editAssignment/{id}")
    public String editAssignment(@PathVariable Long id, Model model) {
        Assignments assignments = assignmentsService.getAssignmentsById(id);
        if (assignments != null) {
            model.addAttribute("assignment", assignments);
        } else {
            // Handle the case where the lecture is not found (optional)
        }
        return "instructor-dashboard"; // Correct view name
    }

    @GetMapping("/editMaterial/{id}")
    public String editMaterial(@PathVariable Long id, Model model) {
        StudyMaterials studyMaterials = studyMaterialsService.getMaterialById(id);
        if (studyMaterials != null) {
            model.addAttribute("material", studyMaterials);
        } else {
            // Handle the case where the lecture is not found (optional)
        }
        return "instructor-dashboard"; // Correct view name
    }



    @GetMapping("/viewLecture/{id}")
    public String viewLecture(@PathVariable Long id, Model model) {
        LiveLecture lecture = liveLectureService.getLectureById(id);
        model.addAttribute("lecture", lecture);
        return "lecture-view"; // Update this with the actual view name for displaying lecture details
    }


    @GetMapping("/deleteLecture/{id}")
    public String deleteLecture(@PathVariable Long id) {
        liveLectureService.deleteLecture(id);
        return "redirect:/instructor/dashboard";
    }


    @GetMapping("/deleteNotification/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "redirect:/instructor/dashboard";
    }
    @GetMapping("/deleteMaterial/{id}")
    public String deleteMaterial(@PathVariable Long id) {
        studyMaterialsService.deleteMaterial(id);
        return "redirect:/instructor/dashboard";
    }
    @GetMapping("/deleteAssignment/{id}")
    public String deleteAssignment(@PathVariable Long id) {
        assignmentsService.deleteAssignments(id);
        return "redirect:/instructor/dashboard";
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