package com.graduate.HealthProtector.controller;

import com.graduate.HealthProtector.dto.UserDTO;
import com.graduate.HealthProtector.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "회원가입 api")
    @PostMapping("/join")
    public String join(UserDTO userDTO){
        boolean joinSuceessful = userService.save(userDTO);
        if(!joinSuceessful){
            return "success";
        }else{
            return "fail";
        }
    }


}
