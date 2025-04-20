package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Step 1: Health Check", tags = {"Health Check"},
            description = "✅ Check if the service is up and running.")
    @GetMapping("/health-check")
    public String healthCheck() {
        return "✅ Service is up and running!";
    }

    //---------------------1.CREATE-------------------------------
    /*@PostMapping("/signup")
    public void signup(@RequestBody User user) {
        userService.saveNewUser(user);
    }*/
    @Operation(summary = "Step 2: Sign Up", tags = {"Authentication"},
            description = """
                        ✍️ Register a new user.
                    
                        ➤ Provide a username, email, and password.
                        ➤ Optionally, set `sentimentAnalysis` to `true` if you want to receive emails tailored to your sentiment analysis.
                        ➤ This is the first step before logging in.
                    """)
    @PostMapping("/signup")
    public void signup(@RequestBody UserDTO user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        userService.saveNewUser(newUser);
    }

    //---------------------1.CREATE-------------------------------
    @Operation(summary = "Step 3: Login", tags = {"Authentication"},
            description = """
                        🔑 Authenticate with username and password.
                    
                        ➤ Returns a **JWT token** on success.
                        ➤ Copy the token for authorization.
                    """)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
