package com.sparesparts.controller.admin;

import com.sparesparts.entity.Enum.Role;
import com.sparesparts.entity.User;
import com.sparesparts.repositories.UserRepository;
import com.sparesparts.service.OrderItemService;
import com.sparesparts.service.OrderService;
import com.sparesparts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<User> newUsers = new ArrayList<>();
        for (User user : users) {
            user.setPassword(null);
            if (user.getUserRole() == Role.ADMIN) {
                continue;
            }
            newUsers.add(user);
        }
        return ResponseEntity.ok(newUsers);
    }

    @PostMapping("add-new-user")
    public ResponseEntity<User> signup(@RequestBody User user) {

        User userByMail = userRepository.findByEmail(user.getEmail());
        User userByPhone = userRepository.findByMobile(user.getMobile());
        if (userByMail != null || userByPhone != null) {
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);
        } else {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            if (user.getUserRole() == null) {
                user.setUserRole(Role.CUSTOMER);
            }
            String username = (user.getFirstName() + "." + user.getLastName() + "." + user.getEmail()).toLowerCase();
            user.setUsername(username);
            User save = userService.createUser(user);
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Optional<User> existingUser = userService.findById(user.getId());
        if(existingUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean active = user.isActive();
        existingUser.get().setActive(active);
        User updatedUser = userService.updateUser(existingUser.get());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/user-details/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> existingUser = userService.findById(id);
        return existingUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/total/{userId}")
    public Object[] getTotalOrdersCountAndSpent(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new Object[0];
        }
        return orderService.getTotalOrdersCountAndSpentByUser(user);
    }

    @GetMapping("/most-purchased-products/{userId}")
    public List<Object[]> getTopPurchasedProductsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ArrayList<>();
        }
        return orderItemService.getTopPurchasedProductsByUser(user);
    }


}
