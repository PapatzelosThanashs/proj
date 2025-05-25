package com.project.demo.mapper;

import com.project.demo.model.User;
import com.project.demo.dto.UserDTO;
import com.project.demo.model.Address;
import com.project.demo.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.project.demo.dto.UserSummaryDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "gender", target = "gender", qualifiedByName = "dtoToEntityGender")
    User userDTOToUser(UserDTO dto);

    @Mapping(source = "gender", target = "gender", qualifiedByName = "entityToDtoGender")
    UserDTO userToUserDTO(User user);



    Address addressDTOToAddress(AddressDTO dto);

    AddressDTO addressToAddressDTO(Address address);
    

    /* Mapstruct can not automap enums, Custom enum mapping */

    @Named("dtoToEntityGender")
    default User.Gender mapDtoGender(String gender) {
        if (gender.equals("")) return null;
        try {
             return User.Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid gender value: must be 'M' or 'F'");
        }
       
    }

    @Named("entityToDtoGender")
    default String mapEntityGender(User.Gender gender) {
        if (gender == null) return null;
        return gender.name();
    }

/*Mapping for userToUserSummaryDTO */
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    UserSummaryDTO userToUserSummaryDTO(User user);


}
