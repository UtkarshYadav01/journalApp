package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //1.CREATE //3.UPDATE
    public void saveUser(User user) {
        userRepository.save(user);
    }

    //1.2.CREATE
    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            //log.error("Error occurred for {}:", "\'"+ user.getUserName()+"\'", e);
            return false;
        }
    }

    //1.3.CREATE admin
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    //2.READ ALL
    public List<User> getAll() {
        return userRepository.findAll();
    }

    //2.2.READ BY ID
    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    //2.3.READ BY NAME
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    //4.DELETE
    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    //4.2.DELETE
    public void deleteByUserName(String name) {
        userRepository.deleteByUserName(name);
    }
}
