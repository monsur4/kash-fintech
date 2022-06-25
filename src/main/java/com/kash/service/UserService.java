package com.kash.service;

import com.kash.dto.UserDto;
import com.kash.entity.User;
import com.kash.repository.UserRepository;
import com.kash.utils.AppUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto addUser(UserDto userDto){
        return AppUtils.entityToDto(userRepository.save(AppUtils.dtoToEntity(userDto)));
    }

    public List<UserDto> getUsers(){
        return userRepository.findAll()
                .stream()
                .map(AppUtils::entityToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUser(String id){
        return AppUtils.entityToDto(userRepository.findById(id).orElse(null));
    }

    public void delete(String id){
        userRepository.deleteById(id);
    }

    public UserDto getUserByEmail(String email) {
        return AppUtils.entityToDto(userRepository.findByEmail(email));
    }

    public UserDto updateUser(UserDto userDto){
        User user = userRepository.findByEmail(userDto.getEmail());
        BeanUtils.copyProperties(userDto, user);
        userRepository.save(user);
        return AppUtils.entityToDto(user);
    }

    public void updateBalance(LinkedHashMap<String, Object> eventData) {
        Object emailObj =((LinkedHashMap<String, Object>)eventData.get("customer")).get("email");
        if(!(emailObj instanceof String)){
            return;
        }
        String email = (String) emailObj;
        UserDto userDto = getUserByEmail(email);
        double amountPaid = Double.parseDouble(eventData.get("amountPaid").toString());
        userDto.setBalance(userDto.getBalance() + amountPaid);
        updateUser(userDto);
    }
}
