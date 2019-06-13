package org.syngenta.trainings.websockets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SensorDataController {

    @GetMapping("/")
    public String sensorsData() {
        return "content.html";
    }
}