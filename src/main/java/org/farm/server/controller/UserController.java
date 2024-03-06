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

    @GetMapping("/get-all")
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @PutMapping("/save")
    public UserEntity save(@RequestBody UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}
