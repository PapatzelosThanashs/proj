package com.project.demo.exception;

/**
 * Custom exception thrown when a user with a specific ID is not found.
 */
public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException(Long id) {

       super("User with id " + id + " not found");
    }


}
