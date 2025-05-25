package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.demo.model.User;
import org.springframework.stereotype.Repository;


/*@Repository annotation only included for exceptions, not really needed because spring uses proxy-based mechanisms to detect interfaces extending JpaRepository */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

 }