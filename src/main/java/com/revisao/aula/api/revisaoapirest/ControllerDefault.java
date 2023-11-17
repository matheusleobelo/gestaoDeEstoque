package com.revisao.aula.api.revisaoapirest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ControllerDefault {
    
    @GetMapping(value="/")
    public String main() {
        return "Minha primeira API REST";
    }
    
}
