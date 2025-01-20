package com.thexbyte.bioaqua.services;
 
 import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thexbyte.bioaqua.entites.User;
import com.thexbyte.bioaqua.repositories.UserRepository;
import com.thexbyte.bioaqua.utils.ResponseMsg;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    public   ResponseEntity<?> createUser(User user){
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()){
            var error = new ResponseMsg();
            error.setMessage("email Already Taken");
            return ResponseEntity.badRequest().body(error);
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<?> getUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }
        var error = new ResponseMsg();
        error.setMessage("No user found");
        return ResponseEntity.status(200).body(error);
    }
    public ResponseEntity<?> deleteById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            userRepository.deleteById(id);
            var error = new ResponseMsg();
            error.setMessage("user deleted successfully");
            return ResponseEntity.status(200).body(error);
        }
        var error = new ResponseMsg();
        error.setMessage("No user found");
        return ResponseEntity.status(200).body(error);
    }

}
