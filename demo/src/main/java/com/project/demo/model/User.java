package com.project.demo.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.project.demo.model.Address;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import jakarta.persistence.Version;




@Data
@Entity
public class User {

    public enum Gender { M, F }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String name;

    
    private String surname;


    private LocalDate birthdate;

    
    
    @Enumerated(EnumType.STRING)
    private Gender gender;


/**need for JPA and Jackson  or @NoArgsContructor <- Lombok*/
    public User() {

    }

    @Version
    private Long version; //Optimistic lock field

 
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
    private Address address;
  


}
