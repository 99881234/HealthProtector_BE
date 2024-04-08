package com.graduate.HealthProtector.controller;

import com.graduate.HealthProtector.dto.LoginDTO;
import com.graduate.HealthProtector.dto.UserDTO;
import com.graduate.HealthProtector.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if(joinSuceessful){
            return "success";
        }else{
            return "fail";
        }
    }

    @Operation(summary = "로그인 api")
    @GetMapping("/login")
    public String login(@RequestParam("loginId") String loginId, @RequestParam("password") String password){
        boolean loginSuccessful = userService.login(loginId, password);
        if(loginSuccessful){
            return "success";
        } else {
            return "fail";
        }
    }

    @Operation(summary = "아이디 중복체크 api")
    @GetMapping("join/checkId")
    public String checkId(@RequestParam("loginId") String loginId){
        boolean checkId = userService.checkId(loginId);
        return !checkId ? "success" : "fail";
        // 아이디가 이미 있을 경우 fail
    }

}


