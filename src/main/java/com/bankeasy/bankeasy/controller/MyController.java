//package com.bankeasy.bankeasy.controller;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.bankeasy.bankeasy.entities.User;
//import com.bankeasy.bankeasy.services.SignupService;
//
//@RestController
//@RequestMapping("/users")
//public class MyController {
//
//    @Autowired
//    private SignupService signupService;
//
//    @GetMapping("/users")
//    public List<User> getUsers() {
//        return this.signupService.getUsers();
//    }
//
// @GetMapping("/users/{userId}")
//    public User getUser(@PathVariable String userId) {
//        return this.signupService.getUser(Long.parseLong(userId));
//    }
//    
//    
//  
//
//    @PostMapping("/users")
//    public User addUser(@RequestBody User user) {
//        return this.signupService.addUser(user);
//    }
//
//    @PutMapping("/users/{userId}")
//    public User updateUser(@PathVariable String userId, @RequestBody User user) {
//        return this.signupService.updateUser(Long.parseLong(userId), user);
//    }
//
//    @DeleteMapping("/users/{userId}")
//    public String deleteUser(@PathVariable String userId) {
//        this.signupService.deleteUser(Long.parseLong(userId));
//        return "User with ID " + userId + " has been deleted";
//    }
//    
//    
//
//}
