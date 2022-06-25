package com.kash.controller;

import com.kash.dto.UserDto;
import com.kash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@RequestParam String id){
        return userService.getUser(id);
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userService.getUsers();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@RequestParam String id){
        userService.delete(id);
    }
}
