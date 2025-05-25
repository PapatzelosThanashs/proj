package com.project.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import com.project.demo.model.User;
import lombok.Data;


@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workAddress;

    private String homeAddress;

 


/**need for JPA and Jackson  or @NoArgsContructor <- Lombok*/
    public Address() {

    }
    


   
}
