package com.stm.user.manager.controller;

import com.stm.user.manager.db.entity.UserEntity;
import com.stm.user.manager.db.repository.UserRepository;
import com.stm.user.manager.dto.UserDto;
import com.stm.user.manager.dto.UserWithRoleDto;
import com.stm.user.manager.mapper.UserMapper;
import org.hamcrest.core.IsEqual;
import org.hamcrest.junit.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


//todo: Тесты пускай лучше кто-нибудь другой расскажет, мне лень.
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProfileController.class, ControllerExceptionHandler.class})
@WebMvcTest(ProfileController.class)
class ProfileControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EntityManagerFactory entityManagerFactory;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserMapper userMapper;

    private Integer userId = 1;
    private UserEntity userEntity;
    private UserDto userDto;
    private UserWithRoleDto userWithRoleDto;

//    @BeforeEach
//    private void mockUserRepository() {
//        this.userEntity = new UserEntity();
//        userEntity.setId(userId);
//        when(userRepository.getById(userId)).thenReturn(userEntity);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
//        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntity));
//    }
//
//    @BeforeEach
//    private void mockUserMapper() {
//        this.userDto = new UserDto();
//        this.userWithRoleDto = new UserWithRoleDto();
//        when(userMapper.toDto(any())).thenReturn(userDto);
//        when(userMapper.toUserWithRoleDto(any())).thenReturn(userWithRoleDto);
//        when(userMapper.toMinDto(any())).thenReturn(userDto);
//    }


    @Test
    void getUserByIdSuccessTest() throws Exception {
        Integer id = 1;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        when(userRepository.getById(userId)).thenReturn(userEntity);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/profiles/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String dto = mapper.writeValueAsString(userWithRoleDto);
        MatcherAssert.assertThat(mvcResult.getResponse().getContentAsString(), IsEqual.equalTo(dto));
    }

    @Test
    void getUserByNotFoundTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/profiles/" + (userId + 1))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    
}