package se.kth.ContextualRoleBasedAccessSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import se.kth.ContextualRoleBasedAccessSystem.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

   // @Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private EventService eventService;

    /*
    public boolean loginUser(String username, String password String ipAddress ) {
        // Sök efter användaren i databasen
        return userRepository.findByUsername(username)
                .map(user -> {
                    boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
                    Map<String, String> details = new HashMap<>();
                    details.put("ipAddress", ipAddress);
                    details.put("outcome", passwordMatch ? "success" : "failed");

                    // Logga händelsen oavsett resultat
                    eventService.logEvent("UserLoginAttempt", user.getId(), details);

                    return passwordMatch;
                })
                .orElseGet(() -> {
                    // Om användaren inte finns, logga ett misslyckat försök utan userId
                    Map<String, String> details = new HashMap<>();
                    details.put("ipAddress", ipAddress);
                    details.put("outcome", "failed");

                    eventService.logEvent("UserLoginAttempt", null, details);

                    return false;
                });
    }

     */

    public boolean loginUser(String username, String password) {
        // Sök efter användaren i databasen
        return userRepository.findByUsername(username)
                .map(user -> {
                    // Jämför det inskickade lösenordet med det lagrade lösenordet direkt
                    boolean passwordMatch = user.getPassword().equals(password);

                    // Förbered detaljer för eventloggen
                    Map<String, String> details = new HashMap<>();
                    details.put("outcome", passwordMatch ? "success" : "failed");

                    // Logga händelsen oavsett resultat
                    eventService.logEvent("UserLoginAttempt", user.getId(), details);

                    return passwordMatch;
                })
                .orElseGet(() -> {
                    // Om användaren inte finns, logga ett misslyckat försök utan userId
                    Map<String, String> details = new HashMap<>();
                    details.put("outcome", "failed");

                    eventService.logEvent("UserLoginAttempt", null, details);

                    return false;
                });
    }

}