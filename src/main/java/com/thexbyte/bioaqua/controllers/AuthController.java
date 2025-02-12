package com.thexbyte.bioaqua.controllers;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/")
@Tag(name = "Auth", description = "Endpoints for authentication")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their unique ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("users/{uid}")
    public ResponseEntity<?> getUserById(@PathVariable("uid") Long uid) {
        return userService.getUserById(uid);
    }

    @Operation(summary = "Create a new user", description = "Register a new user in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping("users")
    public ResponseEntity<?> create(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Delete user by ID", description = "Delete a user by their unique ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User successfully deleted"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("users/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable("uid") Long uid) {
        return userService.deleteById(uid);
    }

    @Operation(summary = "User registration", description = "Register a new user into the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid registration data")
    })
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return authService.register(user);
    }

    @Operation(summary = "User login", description = "Authenticate a user and return a token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful, token issued"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "Confirm email address", description = "Confirm a user's email address during registration")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email confirmed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid confirmation request")
    })
    @PostMapping("confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestBody ConfirmMailRequest confirmMailRequest) {
        return authService.confirmMail(confirmMailRequest);
    }

    @Operation(summary = "Forget password", description = "Handle password reset requests for users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password reset email sent successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid reset request")
    })
    @PostMapping("forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPwdRequest forgetPwdRequest) {
        return authService.forgetPassword(forgetPwdRequest);
    }
}
