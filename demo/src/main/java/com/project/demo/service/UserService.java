package com.project.demo.service;

import com.project.demo.model.User;
import com.project.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.dao.DataAccessException;
import com.project.demo.exception.UserNotFoundException;
import com.project.demo.model.Address;
import com.project.demo.dto.UserDTO;
import com.project.demo.dto.AddressDTO;
import com.project.demo.mapper.UserMapper;
import java.util.stream.Collectors;
import com.project.demo.dto.UserSummaryDTO;
import org.springframework.dao.OptimisticLockingFailureException;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;








/**possible DB exception should be auto-thrown from spring */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    
    public  UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper = userMapper;
    }
    

    public List<UserSummaryDTO> findAll() {
        
        List<User> users = userRepository.findAll();
        List<UserSummaryDTO> summaryDTOs = new ArrayList<>();

        for (User user : users) {
            UserSummaryDTO dto = userMapper.userToUserSummaryDTO(user);
            summaryDTOs.add(dto);
        }

    return summaryDTOs;

    /* Alternative way
    return userRepository.findAll().stream().map(userMapper::userToUserSummaryDTO).collect(Collectors.toList());
     */
    }

    public UserDTO findUser(Long id) {

       // User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
       User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found"));

    return userMapper.userToUserDTO(user);  // Use MapStruct to map User to UserDTO
  
    }

    public UserDTO saveUser(UserDTO userDTO) {

        User user = userMapper.userDTOToUser(userDTO);  // Use MapStruct to map UserDTO to User

        User savedUser = userRepository.save(user);
    
    return userMapper.userToUserDTO(savedUser);  // Use MapStruct to map saved User to UserDTO
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id " + id + " to delete");
        }

            userRepository.deleteById(id);
 
  
    }

    /* update specific fields */
    public UserDTO patchUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id " + id + " to patch"));


        /* optimistic lock for version, throw exception */
    if (!existingUser.getVersion().equals(userDTO.getVersion())) {
        throw new OptimisticLockingFailureException("Version mismatch: current version is " + existingUser.getVersion());
        
    }

         /* Manually update only non-null fields from userDTO */
    if (userDTO.getName() != null) existingUser.setName(userDTO.getName());
    if (userDTO.getSurname() != null) existingUser.setSurname(userDTO.getSurname());
    if (userDTO.getBirthdate() != null) existingUser.setBirthdate(userDTO.getBirthdate());
    if (userDTO.getGender()!= null && !userDTO.getGender().trim().isEmpty() ) existingUser.setGender(userMapper.userDTOToUser(userDTO).getGender());
    if (userDTO.getAddress() != null) {
        Address address = userMapper.addressDTOToAddress(userDTO.getAddress());
        existingUser.setAddress(address);
    }

    User updatedUser = userRepository.save(existingUser);
    return userMapper.userToUserDTO(updatedUser);
    }

    /* update whole record */
    public UserDTO updateUser(Long id, UserDTO userDTO) {


        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id " + id + " to update"));

             /* optimistic lock for version, throw exception */
    if (!existingUser.getVersion().equals(userDTO.getVersion())) {
        throw new OptimisticLockingFailureException("Version mismatch: current version is " + existingUser.getVersion());
        
    }

        User updatedUser = userMapper.userDTOToUser(userDTO);
        

        updatedUser.setId(id);

        updatedUser = userRepository.save(updatedUser);
    
    return userMapper.userToUserDTO(updatedUser);  // Use MapStruct to map saved User to UserDTO
    }


}
