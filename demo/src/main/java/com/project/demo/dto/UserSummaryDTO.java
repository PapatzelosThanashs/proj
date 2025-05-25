package com.project.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import com.project.demo.dto.validation.OnCreate;
import com.project.demo.dto.validation.OnPatch;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDTO {


    private Long id;

    @NotBlank(message = "name is required", groups = OnCreate.class )
    private String name;

    @NotBlank(message = "Surname is required", groups = OnCreate.class)
    @Size(max = 12, message = "Surname must be at most 12 characters", groups = OnCreate.class)
    private String surname;

    

}
