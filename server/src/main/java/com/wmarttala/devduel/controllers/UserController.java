package com.wmarttala.devduel.controllers;

import com.wmarttala.devduel.dtos.UserDto;
import com.wmarttala.devduel.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public UserDto getUserDto(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam("username") List<String> usernames) {
        return userService.getUsers(usernames);
    }

}
