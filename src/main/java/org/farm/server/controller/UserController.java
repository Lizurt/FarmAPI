package org.farm.server.controller;

import org.farm.server.model.entities.UserEntity;
import org.farm.server.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets all users
     *
     * @return all users
     */
    @GetMapping("/get")
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    /**
     * Saves a user based on given info
     *
     * @param userEntity the user info
     * @return the saved user
     */
    @PutMapping("/save")
    public UserEntity save(@RequestBody UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * Deletes a user based on given id
     *
     * @param userId the user id
     */
    @DeleteMapping("/delete/{userId}")
    public void delete(@PathVariable Integer userId) {
        userRepository.deleteById(userId);
    }
}
