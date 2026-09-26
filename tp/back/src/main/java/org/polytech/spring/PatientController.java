package org.polytech.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {
    @GetMapping ("/hello")
    public String Hello(){
        return "Bonjour";
    }

    @GetMapping ("/patients")
    public Patient getPatients(){
        return new Patient("Alice", "Martin", "alice@mail.com");
    }
}