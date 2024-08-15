package org.work.springtestexample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.springtestexample.dto.UserDTO;
import org.work.springtestexample.model.User;
import org.work.springtestexample.repository.UserRepository;

import java.security.PublicKey;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id).orElse(null);

        if(user == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

    public UserDTO saveUser(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user = userRepository.save(user);
        userDTO.setId(user.getId());
        return userDTO;
    }
}
