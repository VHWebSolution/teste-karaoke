package com.vhws.karaoke.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("")
public class PresentationController {
    @GetMapping("/")
    public String presentation(){
        return "ola";
    }
}
