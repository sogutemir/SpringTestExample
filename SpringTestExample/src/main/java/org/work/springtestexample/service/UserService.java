package org.work.springtestexample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.springtestexample.dto.UserDTO;
import org.work.springtestexample.model.User;
import org.work.springtestexample.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getDateOfBirth()))
                .orElse(null);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user = userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getDateOfBirth());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDTO.getName());
                    user.setEmail(userDTO.getEmail());
                    user.setAddress(userDTO.getAddress());
                    user.setPhoneNumber(userDTO.getPhoneNumber());
                    user.setDateOfBirth(userDTO.getDateOfBirth());
                    user = userRepository.save(user);
                    return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getDateOfBirth());
                })
                .orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getDateOfBirth()))
                .collect(Collectors.toList());
    }

    public List<UserDTO> findUsersByName(String name) {
        return userRepository.findByName(name).stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getDateOfBirth()))
                .collect(Collectors.toList());
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getDateOfBirth());
        }
        return null;
    }

    public List<UserDTO> searchByName(String keyword) {
        return userRepository.searchByName(keyword).stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getDateOfBirth()))
                .collect(Collectors.toList());
    }
}