package com.thexbyte.bioaqua.services;



 
import com.thexbyte.bioaqua.entites.User;
import com.thexbyte.bioaqua.config.JwtService;
import com.thexbyte.bioaqua.entites.Role;
import com.thexbyte.bioaqua.repositories.UserRepository;
import com.thexbyte.bioaqua.utils.AuthenticationResponse;
import com.thexbyte.bioaqua.utils.ConfirmMailRequest;
 import com.thexbyte.bioaqua.utils.ForgetPwdRequest;
import com.thexbyte.bioaqua.utils.LoginRequest;
import com.thexbyte.bioaqua.utils.ResponseMsg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private  UserRepository userRepo;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  EmailService emailService;


    public ResponseEntity<?> register(User user) {
        Role role = user.getRole();

        Optional<User> test = userRepo.findByEmail(user.getEmail());
        if (test.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseMsg("email already exist"));
        }
         user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        var userValidationCode =getActivationCode();
        user.setActivationCode(userValidationCode);
        emailService.sendConfirmationEmail(user.getEmail() , user.getActivationCode());
        userRepo.save(user);
        var JwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(new ResponseMsg("Please confirm your email"));
    }

    private String getActivationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private String generateRandomPwd() {
        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final Random random = new Random();
        StringBuilder password = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            password.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return password.toString();
    }


    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepo.findByEmail(loginRequest.getEmail());
        if (optionalUser.isPresent()) {
            UserDetails user = optionalUser.get();

            if (!user.isEnabled()) {
                return ResponseEntity.status(401).body(new ResponseMsg("Account not active yet"));
            }
            try {
                 authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                        )
                );
            } catch (BadCredentialsException e) {
              return ResponseEntity.status(401).body(new ResponseMsg("Bad Credentials"));
            }
            var JwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(
                    new
                    AuthenticationResponse(JwtToken));
        } else {
            return  ResponseEntity.status(401).body(new ResponseMsg("Bad Credentials"));
        }
    }

    public ResponseEntity<?> confirmMail(ConfirmMailRequest confirmMailRequest) {
        Optional<User> optionalUser = userRepo.findByEmail(confirmMailRequest.getEmail());
        if (optionalUser.isPresent()){
            var user = optionalUser.get();
            if (user.getActivationCode().equals(confirmMailRequest.getCode())){
                user.setEnabled(true);
                userRepo.save(user);
                emailService.sendConfirmedEmail(user.getEmail());
                return ResponseEntity.ok(new ResponseMsg("your account enabled you can login now"));
            }else {
                return ResponseEntity.status(400).body(new ResponseMsg("Wrong Code "));
            }
        }else {
            return ResponseEntity.status(400).body(new ResponseMsg("Wrong User "));
        }
    }

    public ResponseEntity<?> forgetPassword(ForgetPwdRequest forgetPwdRequest) {
       Optional<User> optionalUser = userRepo.findByEmail(forgetPwdRequest.getEmail());
       if (optionalUser.isPresent()){
            var user = optionalUser.get();
            var pwd = generateRandomPwd();
            user.setPassword(passwordEncoder.encode( pwd));

            if( emailService.sendResetPwdEmail(user.getEmail(), pwd)){
                userRepo.save(user);
                return ResponseEntity.status(200).body(new ResponseMsg("link sent to you email"));
            }else{
                return  ResponseEntity.status(500).body(new ResponseMsg("something went wrong please try again"));
            }

       }
       return ResponseEntity.status(400).body(new ResponseMsg("email is not registered"));
    }
}