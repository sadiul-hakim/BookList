package com.hakimbooks.hakimbooks.controller;

import com.hakimbooks.hakimbooks.pojo.ApiResponse;
import com.hakimbooks.hakimbooks.pojo.UserResponse;
import com.hakimbooks.hakimbooks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getUserList(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "userNum", defaultValue = "50", required = false) int userNum
    ) {
        List<UserResponse> userList = userService.getUserList(page, userNum);
        return ResponseEntity.ok(userList);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable long userId) {
        boolean done = userService.deleteUser(userId);
        if (done) {
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
        }else{
            return ResponseEntity.ok(new ApiResponse("Could not delete user.", true));
        }
    }
}
