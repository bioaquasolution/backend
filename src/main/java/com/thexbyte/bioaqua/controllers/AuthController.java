package com.thexbyte.bioaqua.controllers;
 
 
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thexbyte.bioaqua.entites.User;
import com.thexbyte.bioaqua.services.AuthService;
import com.thexbyte.bioaqua.services.UserService;
import com.thexbyte.bioaqua.utils.ConfirmMailRequest;
import com.thexbyte.bioaqua.utils.ForgetPwdRequest;
import com.thexbyte.bioaqua.utils.LoginRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/auth/")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoints for authentication")
public class AuthController {

    final UserService userService;

    private  final AuthService authService;

    @Operation(summary = "Get all", description = "Retrieve a list of all registered users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("users")
    ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }


    @GetMapping("users/{uid}")
    ResponseEntity<?> getUserByIdd(@PathVariable("uid")Long uid){
        return userService.getUserById(uid);
    }
    @PostMapping("users")
    ResponseEntity<?> create(@RequestBody User user){
        return userService.createUser(user);
    }
    @DeleteMapping("users/{uid}")
    ResponseEntity<?> deleteUser(@PathVariable("uid")Long uid){
        return userService.deleteById(uid);
    }


        @PostMapping("register")
        public ResponseEntity<?> register(@RequestBody User user) {
            return   authService.register(user);
        }

        @PostMapping("login")
        public  ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            return  authService.login(loginRequest);
        }

        @PostMapping("confirm-email")
        public ResponseEntity<?> confirmEmail(@RequestBody() ConfirmMailRequest confirmMailRequest){
            return authService.confirmMail(confirmMailRequest);
        }

    @PostMapping("forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody() ForgetPwdRequest ForgetPwdRequest){
        return authService.forgetPassword(ForgetPwdRequest);
    }

    }
