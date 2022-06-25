package com.kash.utils;

import com.kash.dto.UserDto;
import com.kash.entity.User;
import org.springframework.beans.BeanUtils;

public class AppUtils {
    public static User dtoToEntity(UserDto userDto){
        if(userDto == null) return null;
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static UserDto entityToDto(User user){
        if(user == null) return null;
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
