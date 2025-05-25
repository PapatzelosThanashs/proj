//comment to trigger jenkins[pipeline] 123456789
package com.project.demo.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import com.project.demo.dto.validation.OnCreate;
import com.project.demo.dto.validation.OnPatch;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    //public enum Gender { M, F }

    private Long id;

    @NotBlank(message = "name is required", groups = OnCreate.class )
    @Pattern.List({
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Only letters, numbers, and dashes are allowed", groups = OnCreate.class),
        @Pattern(regexp = "^$|^[a-zA-Z0-9_-]+$", message = "Name must not be empty, letters, numbers, and dashes are allowed ", groups = OnPatch.class)
    })
    private String name;

    @NotBlank(message = "Surname is required", groups = OnCreate.class)
    @Size(max = 24, message = "Surname must be at most 24 characters", groups = OnCreate.class)
    @Pattern.List({
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Only letters, numbers, and dashes are allowed", groups = OnCreate.class),
        @Pattern(regexp = "^$|^[a-zA-Z0-9_-]+$", message = "Surname must not be empty, letters, numbers, and dashes are allowed ", groups = OnPatch.class )
    })
    private String surname;
    
    /* no need validation in patch, if is an empty string it will convert it to null */
    @NotNull(message = "Birthdate is required", groups = OnCreate.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;
    
    @NotBlank(message = "Gender is required", groups = OnCreate.class)
    @Pattern(regexp = "M|F", message = "Gender must be 'M' or 'F'", groups = {OnCreate.class,OnPatch.class} )
    private String gender;  

    @NotNull(message = "Version is required", groups = OnPatch.class)
    private Long version;

    @Valid
    private AddressDTO address;
    

}
