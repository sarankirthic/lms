package com.sarankirthic.lms.controller;

import com.sarankirthic.lms.service.StuDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private StuDetailsService stuDetailsService;

    @GetMapping("/admission_form")
    public String admissionForm() {
        return "admission_form";
    }

}
