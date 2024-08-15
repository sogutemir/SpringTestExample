package org.work.springtestexample.controller;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.work.springtestexample.dto.UserDTO;
import org.work.springtestexample.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private Faker faker;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        faker = new Faker();
    }

    @Test
    public void testGetUserById() throws Exception {
        UserDTO userDTO = new UserDTO(faker.number().randomNumber(), faker.name().fullName(), faker.internet().emailAddress());

        when(userService.getUserById(userDTO.getId())).thenReturn(userDTO);

        mockMvc.perform(get("/users/{id}", userDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.getId()))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO(null, faker.name().fullName(), faker.internet().emailAddress());
        UserDTO savedUserDTO = new UserDTO(faker.number().randomNumber(), userDTO.getName(), userDTO.getEmail());

        when(userService.saveUser(any(UserDTO.class))).thenReturn(savedUserDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + userDTO.getName() + "\", \"email\":\"" + userDTO.getEmail() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUserDTO.getId()))
                .andExpect(jsonPath("$.name").value(savedUserDTO.getName()))
                .andExpect(jsonPath("$.email").value(savedUserDTO.getEmail()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO(null, faker.name().fullName(), faker.internet().emailAddress());
        UserDTO updatedUserDTO = new UserDTO(faker.number().randomNumber(), userDTO.getName(), userDTO.getEmail());

        when(userService.updateUser(any(Long.class), any(UserDTO.class))).thenReturn(updatedUserDTO);

        mockMvc.perform(put("/users/{id}", updatedUserDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + userDTO.getName() + "\", \"email\":\"" + userDTO.getEmail() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUserDTO.getId()))
                .andExpect(jsonPath("$.name").value(updatedUserDTO.getName()))
                .andExpect(jsonPath("$.email").value(updatedUserDTO.getEmail()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = faker.number().randomNumber();

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<UserDTO> users = Stream.generate(() -> new UserDTO(faker.number().randomNumber(), faker.name().fullName(), faker.internet().emailAddress()))
                .limit(5).collect(Collectors.toList());

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @Test
    public void testSearchUsersByName() throws Exception {
        String keyword = faker.name().firstName();
        List<UserDTO> users = Stream.generate(() -> new UserDTO(faker.number().randomNumber(), keyword + " " + faker.name().lastName(), faker.internet().emailAddress()))
                .limit(3).collect(Collectors.toList());

        when(userService.searchByName(keyword)).thenReturn(users);

        mockMvc.perform(get("/users/search").param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @Test
    public void testFindUserByEmail() throws Exception {
        UserDTO userDTO = new UserDTO(faker.number().randomNumber(), faker.name().fullName(), faker.internet().emailAddress());

        when(userService.findByEmail(userDTO.getEmail())).thenReturn(userDTO);

        mockMvc.perform(get("/users/email").param("email", userDTO.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.getId()))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));
    }
}