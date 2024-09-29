package com.sparesparts.config;


import com.sparesparts.entity.Cart;
import com.sparesparts.entity.Enum.Role;
import com.sparesparts.entity.User;
import com.sparesparts.entity.Wishlist;
import com.sparesparts.repositories.CartRepository;
import com.sparesparts.repositories.UserRepository;
import com.sparesparts.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthenticateController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
//    @Autowired
//    private EmailService emailService;

    @PostMapping("signup")
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> userRepository.delete(value));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("signupMulti")
    public ResponseEntity<List<User>> signupMulti(@RequestBody List<User> users) {

        List<User> newUsers = new ArrayList<>();
        for (User user : users) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            if (user.getUserRole() == null) {
                user.setUserRole(Role.CUSTOMER);
            }
            User save = userService.createUser(user);
            Cart cart = new Cart();
            cart.setUser(save);
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(save);
//            cartRepository.save(cart);

            newUsers.add(save);

        }
        return new ResponseEntity<>(newUsers, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {


        User newUser = userService.getUserById(user.getId());
        user.setPassword(newUser.getPassword());

        return updateUserDetails(user, newUser);
    }

    @PutMapping("/update-password")
    public ResponseEntity<User> updateUserPass(@RequestBody User user) {

        String password = user.getPassword();
        User newUser = userService.getUserById(user.getId());
        user.setPassword(passwordEncoder.encode(password));
        return updateUserDetails(user, newUser);
    }

    private ResponseEntity<User> updateUserDetails(@RequestBody User user, User newUser) {
        user.setEmail(newUser.getEmail());
        user.setUserRole(newUser.getUserRole());
        user.setUsername(newUser.getUsername());
        user.setCreatedAt(newUser.getCreatedAt());


        if (user.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            User updatedUser = userService.updateUser(user);
            if (updatedUser == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {
        System.out.println(u);
        User user;
        Authentication authentication;
        try {
            if (u.getEmail() != null) {
                user = userService.findByEmail(u.getEmail());
            } else if (u.getMobile() != null) {
                user = userService.findByMobile(u.getMobile());
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), u.getPassword()));
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername(), role);
        if(!user.isActive()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) this.userDetailsService.loadUserByUsername(principal.getName());
        String username = userDetails.getUsername();

        return userRepository.findByUsername(username).orElseThrow();
    }

//    @PostMapping("/send-otp")
//    public ResponseEntity<Map<String, String>> sendOtp(@RequestBody String email) {
//        String otp = emailService.sendOtpEmail(email);
//        Map<String, String> response = new HashMap<>();
//        response.put("otp", otp); // Creating a JSON-like map structure
//        return ResponseEntity.ok(response); // Will be converted to JSON automatically
//    }


    @PostMapping("/is-present")
    public ResponseEntity<User> isUserPresent(@RequestBody String email) {

        User user = userService.findByEmail(email);
        return ResponseEntity.ok(Objects.requireNonNullElseGet(user, User::new));

    }

}
