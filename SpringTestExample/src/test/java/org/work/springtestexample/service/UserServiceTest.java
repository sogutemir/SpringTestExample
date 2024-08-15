package org.work.springtestexample.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.work.springtestexample.dto.UserDTO;
import org.work.springtestexample.model.User;
import org.work.springtestexample.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(faker.number().randomNumber());
        user.setName(faker.name().fullName());
        user.setEmail(faker.internet().emailAddress());
        user.setAddress(faker.address().fullAddress());
        user.setPhoneNumber(faker.phoneNumber().phoneNumber());
        user.setDateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.getUserById(user.getId());

        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getPhoneNumber(), userDTO.getPhoneNumber());
        assertEquals(user.getDateOfBirth(), userDTO.getDateOfBirth());
    }

    @Test
    public void testSaveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(faker.name().fullName());
        userDTO.setEmail(faker.internet().emailAddress());
        userDTO.setAddress(faker.address().fullAddress());
        userDTO.setPhoneNumber(faker.phoneNumber().phoneNumber());
        userDTO.setDateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        User user = new User();
        user.setId(faker.number().randomNumber());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDateOfBirth(userDTO.getDateOfBirth());

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO savedUserDTO = userService.saveUser(userDTO);

        assertNotNull(savedUserDTO);
        assertEquals(user.getId(), savedUserDTO.getId());
        assertEquals(user.getName(), savedUserDTO.getName());
        assertEquals(user.getEmail(), savedUserDTO.getEmail());
        assertEquals(user.getAddress(), savedUserDTO.getAddress());
        assertEquals(user.getPhoneNumber(), savedUserDTO.getPhoneNumber());
        assertEquals(user.getDateOfBirth(), savedUserDTO.getDateOfBirth());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(faker.number().randomNumber());
        user.setName(faker.name().fullName());
        user.setEmail(faker.internet().emailAddress());
        user.setAddress(faker.address().fullAddress());
        user.setPhoneNumber(faker.phoneNumber().phoneNumber());
        user.setDateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        UserDTO userDTO = new UserDTO();
        userDTO.setName(faker.name().fullName());
        userDTO.setEmail(faker.internet().emailAddress());
        userDTO.setAddress(faker.address().fullAddress());
        userDTO.setPhoneNumber(faker.phoneNumber().phoneNumber());
        userDTO.setDateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO updatedUserDTO = userService.updateUser(user.getId(), userDTO);

        assertNotNull(updatedUserDTO);
        assertEquals(user.getId(), updatedUserDTO.getId());
        assertEquals(userDTO.getName(), updatedUserDTO.getName());
        assertEquals(userDTO.getEmail(), updatedUserDTO.getEmail());
        assertEquals(userDTO.getAddress(), updatedUserDTO.getAddress());
        assertEquals(userDTO.getPhoneNumber(), updatedUserDTO.getPhoneNumber());
        assertEquals(userDTO.getDateOfBirth(), updatedUserDTO.getDateOfBirth());
    }

    @Test
    public void testDeleteUser() {
        Long userId = faker.number().randomNumber();

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = Stream.generate(() -> {
            User user = new User();
            user.setId(faker.number().randomNumber());
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());
            user.setAddress(faker.address().fullAddress());
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setDateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            return user;
        }).limit(5).collect(Collectors.toList());

        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> userDTOs = userService.findAllUsers();

        assertNotNull(userDTOs);
        assertEquals(users.size(), userDTOs.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), userDTOs.get(i).getId());
            assertEquals(users.get(i).getName(), userDTOs.get(i).getName());
            assertEquals(users.get(i).getEmail(), userDTOs.get(i).getEmail());
            assertEquals(users.get(i).getAddress(), userDTOs.get(i).getAddress());
            assertEquals(users.get(i).getPhoneNumber(), userDTOs.get(i).getPhoneNumber());
            assertEquals(users.get(i).getDateOfBirth(), userDTOs.get(i).getDateOfBirth());
        }
    }

    @Test
    public void testFindUsersByName() {
        String name = faker.name().fullName();
        List<User> users = Stream.generate(() -> {
            User user = new User();
            user.setId(faker.number().randomNumber());
            user.setName(name);
            user.setEmail(faker.internet().emailAddress());
            user.setAddress(faker.address().fullAddress());
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setDateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            return user;
        }).limit(3).collect(Collectors.toList());

        when(userRepository.findByName(name)).thenReturn(users);

        List<UserDTO> userDTOs = userService.findUsersByName(name);

        assertNotNull(userDTOs);
        assertEquals(users.size(), userDTOs.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), userDTOs.get(i).getId());
            assertEquals(users.get(i).getName(), userDTOs.get(i).getName());
            assertEquals(users.get(i).getEmail(), userDTOs.get(i).getEmail());
            assertEquals(users.get(i).getAddress(), userDTOs.get(i).getAddress());
            assertEquals(users.get(i).getPhoneNumber(), userDTOs.get(i).getPhoneNumber());
            assertEquals(users.get(i).getDateOfBirth(), userDTOs.get(i).getDateOfBirth());
        }
    }

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setId(faker.number().randomNumber());
        user.setName(faker.name().fullName());
        user.setEmail(faker.internet().emailAddress());
        user.setAddress(faker.address().fullAddress());
        user.setPhoneNumber(faker.phoneNumber().phoneNumber());
        user.setDateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        UserDTO userDTO = userService.findByEmail(user.getEmail());

        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getPhoneNumber(), userDTO.getPhoneNumber());
        assertEquals(user.getDateOfBirth(), userDTO.getDateOfBirth());
    }

    @Test
    public void testSearchByName() {
        String keyword = faker.name().firstName();
        List<User> users = Stream.generate(() -> {
            User user = new User();
            user.setId(faker.number().randomNumber());
            user.setName(keyword + " " + faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setAddress(faker.address().fullAddress());
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setDateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            return user;
        }).limit(3).collect(Collectors.toList());

        when(userRepository.searchByName(keyword)).thenReturn(users);

        List<UserDTO> userDTOs = userService.searchByName(keyword);

        assertNotNull(userDTOs);
        assertEquals(users.size(), userDTOs.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), userDTOs.get(i).getId());
            assertEquals(users.get(i).getName(), userDTOs.get(i).getName());
            assertEquals(users.get(i).getEmail(), userDTOs.get(i).getEmail());
            assertEquals(users.get(i).getAddress(), userDTOs.get(i).getAddress());
            assertEquals(users.get(i).getPhoneNumber(), userDTOs.get(i).getPhoneNumber());
            assertEquals(users.get(i).getDateOfBirth(), userDTOs.get(i).getDateOfBirth());
        }
    }
}