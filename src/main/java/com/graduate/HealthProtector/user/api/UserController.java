package com.graduate.HealthProtector.user.api;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.user.api.dto.response.HealthInfoDto;
import com.graduate.HealthProtector.user.api.dto.response.JoinDto;
import com.graduate.HealthProtector.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public String join(JoinDto joinDTO){
        boolean joinSuccessful = userService.save(joinDTO);
        return joinSuccessful ? "success" : "fail";
    }

    @Operation(summary = "건강정보 기입 api")
    @PostMapping("/health/{id}")
    public String healthInfo(@PathVariable("id") Long id, @RequestBody HealthInfoDto healthInfoDto){
        boolean infoSaveSuccessful = userService.healthInfoSave(id, healthInfoDto);
        return infoSaveSuccessful ? "success" : "fail";
    }

    @Operation(summary = "건강정보 수정 api")
    @PutMapping("/health/{id}")
    public String healthInfoUpdate(@PathVariable("id") Long id, @RequestBody HealthInfoDto healthInfoDto){
        boolean infoUpdateSuccessful = userService.healthInfoSave(id, healthInfoDto);
        return infoUpdateSuccessful ? "success" : "fail";
    }

    @Operation(summary = "로그인 API", description = "사용자가 로그인하면 인증 정보를 반환합니다.")
    @PostMapping("/login")
    public BaseResponse<?> login(@RequestParam("loginId") String loginId,
                                 @RequestParam("password") String password) {
        try {
            JoinDto userResponse = userService.authenticateUser(loginId, password);

            return BaseResponse.success(userResponse);
        } catch (IllegalArgumentException e) {
            return BaseResponse.fail(e.getMessage());
        } catch (Exception e) {
            return BaseResponse.internalServerError("로그인 처리 중 오류가 발생했습니다.");
        }
    }


    @Operation(summary = "아이디 중복체크 api")
    @GetMapping("join/checkId")
    public String checkId(@RequestParam("loginId") String loginId){
        boolean checkId = userService.checkId(loginId);
        return !checkId ? "success" : "fail";
    }

    @Operation(summary = "회원탈퇴 api")
    @DeleteMapping("/deleteUser/{id}")
    public String deletUser(@PathVariable("id") Long id){
        boolean deleteUserSuccessful = userService.deleteUser(id);
        return deleteUserSuccessful ? "success" : "fail";
    }

}


