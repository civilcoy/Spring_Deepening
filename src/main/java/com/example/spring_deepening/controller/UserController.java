package com.example.spring_deepening.controller;

import com.example.spring_deepening.dto.LoginRequestDto;
import com.example.spring_deepening.dto.ResponseDto;
import com.example.spring_deepening.dto.SignupRequestDto;
import com.example.spring_deepening.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController //Json형태로 객체 데이터를 반환하는데 사용됨
@RequestMapping("/api/auth")
@RequiredArgsConstructor //생성자를 만들어야 하는 번거로움을 없애줌
public class UserController {

    private final UserService userService; //의존성주입

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto); //userservice의 signup 메소드 실행(매개값 signupRequestDto)
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

}
