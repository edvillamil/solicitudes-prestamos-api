package co.com.bancolombia.consumer.mapper;

import co.com.bancolombia.consumer.UserResponse;
import co.com.bancolombia.model.user.User;

public class UserMapper {

    public static User toEntity(UserResponse dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setBirthDate(dto.getBirthDate());
        user.setAddress(dto.getAddress());
        user.setMobileNumber(dto.getMobileNumber());
        user.setEmail(dto.getEmail());
        user.setBaseSalary(dto.getBaseSalary());
        return user;


        /*UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

        User toEntity(UserResponseDTO dto);

        UserResponseDTO toDTO(User user);*/
    }

}
