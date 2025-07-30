package com.example.fone_hub.controller.web;

import com.example.fone_hub.dto.request.CreateUserRequest;
import com.example.fone_hub.dto.request.LoginRequest;
import com.example.fone_hub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("web/login")
                .addObject("login", new LoginRequest());
    }


    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("web/register")
                .addObject("newUser", new CreateUserRequest());
    }
}

