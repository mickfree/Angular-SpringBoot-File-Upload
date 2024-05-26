package com.mla.controller;


import com.mla.entity.User;
import com.mla.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/file")
public class UserController {


    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Operation(summary = "Sirve para agregar los nombres y archivos")
    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestParam String name,
                                              @RequestPart("file")MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User user = User.builder().userName(name).displayPicture(file.getBytes()).build();
        userRepository.save(user);
        user.setDisplayPicture(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Sirve para obtener toda la lista de usuarios")
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }

    @Operation(summary = "Sirve para obtener la informacion de un id ")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserId(@PathVariable Long id){
        Optional<User> userId = userRepository.findById(id);
        return userId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Sirve borrar toda la informacion desde el id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserId(@PathVariable Long id){
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Sirve para actualizar la informacion del user")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserId(@PathVariable Long id,@RequestBody User updateUser ){
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        updateUser.setId(id);
        User savedUser = userRepository.save(updateUser);
        return ResponseEntity.ok(savedUser);
    }



}
