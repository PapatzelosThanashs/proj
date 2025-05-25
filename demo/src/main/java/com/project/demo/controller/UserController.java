package com.project.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.project.demo.model.User;
import com.project.demo.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import jakarta.validation.Valid;
import com.project.demo.dto.UserDTO;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.validation.annotation.Validated; // for Validate
import com.project.demo.dto.validation.OnCreate;
import com.project.demo.dto.validation.OnPatch;
import com.project.demo.dto.UserSummaryDTO;
// OpenAPI 3/swagger annotations
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

 

@RestController
@RequestMapping("api/users")
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService= userService;
    } 

    

    /* Get all users */
    @Operation(summary = "Get all users", description = "Returns a summary list of all users, only id, name and surname are returned")
    @GetMapping
    public List<UserSummaryDTO> all() {
        return userService.findAll();
    }

    /* Create new user */
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @PostMapping
    public UserDTO create(@RequestBody @Validated(OnCreate.class) UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    /* Get user with id=?*/
    @Operation(summary = "Get user by ID", description = "Fetches user details for the given ID")
    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id) {
        return userService.findUser(id);
    }

    /* Delete user with id=?*/
    @Operation(summary = "Delete user by ID", description = "Deletes the user for the given ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
         userService.deleteUser(id);
    }

    /* Patch user */
    @Operation(summary = "Partially update a user", description = "Updates specific fields of a user using PATCH, version field is mandatory")
    @PatchMapping("/{id}")
    public UserDTO patch(@PathVariable Long id, @RequestBody @Validated(OnPatch.class) UserDTO userDTO) {
      return  userService.patchUser(id,userDTO);
        
    }

      /* Update user */
    @Operation(summary = "Fully update a user", description = "Replaces all user details with provided data")
    @PutMapping("/{id}")
    public UserDTO put(@PathVariable Long id, @RequestBody @Validated(OnCreate.class) UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }


}
