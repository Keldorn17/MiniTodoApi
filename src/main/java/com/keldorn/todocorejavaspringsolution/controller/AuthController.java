package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.dto.user.UserRequest;
import com.keldorn.todocorejavaspringsolution.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("userRequest") @Valid UserRequest userRequest,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (userService.isUsernameTaken(userRequest.getUsername())) {
            model.addAttribute("userRequest", new UserRequest());
            model.addAttribute("global", "Username is taken");

            return "auth/register";
        }

        if (userService.isEmailTaken(userRequest.getEmail())) {
            model.addAttribute("userRequest", new UserRequest());
            model.addAttribute("global", "Email is taken");

            return "auth/register";
        }

        userService.createUser(userRequest);

        return "redirect:/login?registered";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
