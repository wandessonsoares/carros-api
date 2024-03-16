package dev.wandessonsoares.controller;

import dev.wandessonsoares.domain.user.AuthenticationDTO;
import dev.wandessonsoares.domain.user.LoginResponseDTO;
import dev.wandessonsoares.domain.user.User;
import dev.wandessonsoares.security.TokenService;
import dev.wandessonsoares.utils.ConvertJSONObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private ConvertJSONObject convertJSONObject;

    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        if (data.login().isEmpty() || data.password().isEmpty()){
            JSONObject json = convertJSONObject.convert(HttpStatus.BAD_REQUEST.value(), "Invalid Login or Password");
            return ResponseEntity.badRequest().body(json.toString());
        }

        try {
            var usernamePassoword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authenticationManager.authenticate(usernamePassoword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch(Exception e) {
            String msg = "Invalid login or password";
            JSONObject json = convertJSONObject.convert(HttpStatus.BAD_REQUEST.value(), msg);
            return ResponseEntity.badRequest().body(json.toString());
        }
    }
}
