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
public class AddressDTO {

    private Long id;

    @Pattern(regexp = "^$|^[a-zA-Z0-9-_ ]+$", message = "workAddress must not be empty, letters, numbers, and dashes are allowed ", groups = OnPatch.class)
    private String workAddress;

    @Pattern(regexp = "^$|^[a-zA-Z0-9-_ ]+$", message = "homeAddress  must not be empty, letters, numbers, and dashes are allowed ", groups = OnPatch.class)
    private String homeAddress;


}
