package com.jasekraft.splendor.mvc.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.jasekraft.splendor.mvc.models.LoginUser;
import com.jasekraft.splendor.mvc.models.User;
import com.jasekraft.splendor.mvc.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepo;
    
    @Autowired
    public UserService(UserRepository userRepo) {
    	this.userRepo = userRepo;
    }
    
    public User findUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }
    
    public User register(User newUser, BindingResult result) {
        if(userRepo.findByUsername(newUser.getUsername()).isPresent()) {
            result.rejectValue("email", "Unique", "This email is already in use!");
        }
        if(!newUser.getPassword().equals(newUser.getConfirm())) {
            result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
        }
        if(result.hasErrors()) {
            return null;
        } else {
            String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
            newUser.setPassword(hashed);
            return userRepo.save(newUser);
        }
    }
    
    public User login(LoginUser newLogin, BindingResult result) {
        if(result.hasErrors()) {
            return null;
        }
        Optional<User> potentialUser = userRepo.findByUsername(newLogin.getUsername());
        if(!potentialUser.isPresent()) {
            result.rejectValue("username", "Unique", "Unknown username!");
            return null;
        }
        User user = potentialUser.get();
        if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
            result.rejectValue("password", "Matches", "Invalid Password!");
        }
        if(result.hasErrors()) {
            return null;
        } else {
            return user;
        }
    }
}
