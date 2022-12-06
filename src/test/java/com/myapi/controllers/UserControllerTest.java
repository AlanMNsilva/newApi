/**
 * Created by : Alan Nascimento on 4/2/2022
 */
package com.myapi.controllers;


import com.myapi.data.Users;
import com.myapi.dto.UserDTO;
import com.myapi.models.User;
import com.myapi.repository.UserRepository;
import com.myapi.security.jwt.JwtUtils;
import com.myapi.security.services.RefreshTokenService;
import com.myapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
//@WebMvcTest(UserControllerNoAuth.class)
@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

  /*  @Autowired
    private UserControllerNoAuth uSerControllerNoAuth;*/

    @Autowired
    protected MockMvc restAccountMockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthenticationManager authenticationManager;

    private User user;

    String mockToken = "";

    private UserDTO userDTO = Users.aBuildUserDTO();

    private final String FIND_USER_PATH = "/api/noauth/user";
    private final String FIND_USER_PATH_ID = "/api/noauth/user/{id}";
    private final String SAVE_USER_PATH = "/api/noauth/user/save";
    private final String UPDATE_USER_PATH = "/api/noauth/user/update";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.ExpirationMs}")
    private int jwtExpirationMs;

    @BeforeEach
    public void setup(){
        user = Users.aUser();
        userRepository.saveAndFlush(user);
        //mockToken = jwtUtils.generateTokenFromUserCodAndUserName(String.valueOf(user.getId()), user.getUsername());
        mockToken = Jwts.builder().setSubject(String.valueOf(user.getId())).setId(user.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @AfterAll
    public void afterAll(){
        userRepository.delete(user);
    }

    @Nested
    public class whenGetting{

        @Test
        public void mustReturn200CodeWhenFindUserByBodyJson() throws Exception{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "1");
            when(userService.findUserById(1L)).thenReturn(new UserDTO(1l, "", "", "", ""));
            mockMvc.perform(
                    get(FIND_USER_PATH)
                            .content(String.valueOf(jsonObject))
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        public void mustFindAnUserAndReturn200Code() throws Exception {
         //   when(userService.findUserById(1L)).thenReturn(new UserDTO(1L, "", "", "", ""));

            User user = userRepository.saveAndFlush(Users.aUser());
            UserDTO userDTO = Users.aBuildUserDTO();
            userDTO.setId(user.getId());
            getUserById(userDTO)
                    .andExpect(status().isOk());
        }

        @Test
        public void mustReturn404WhenNotFindUser() throws Exception{
           // when(userService.findUserById(123L)).thenReturn(null);
            getUserById(userDTO)
                    .andExpect(status().isNotFound());

        }

        private ResultActions getUserById(UserDTO userDTO) throws Exception {
            return mockMvc.perform(
                    get(FIND_USER_PATH_ID + "", userDTO.getId())
                        .header("Authorization", mockToken));

        }
    }


    @Test
    public void mustReturn404WhenNotFindUserByBodyJson() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "123");
        when(this.userService.findUserById(null)).thenReturn(new UserDTO(null, "", "", "", ""));
        mockMvc.perform(
                get(FIND_USER_PATH)
                        .content(String.valueOf(jsonObject))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }




    @Test
    public void mustSaveAnUserAndReturn201Code() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Unit test");
        jsonObject.put("lastName", "jUnit5");
        jsonObject.put("email", "junit@junit.com");
        jsonObject.put("phoneNumber", "555 555 555");
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        User user = Mockito.mock(User.class);
        when(this.userService.saveUser(userDTO)).thenReturn(user);
        mockMvc.perform(
                post(SAVE_USER_PATH)
                        .content(String.valueOf(jsonObject))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void mustUpdateAnUserAndReturn204Cod() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "1");
        jsonObject.put("lastName", "jUnit5");
        jsonObject.put("email", "junit@junit.com");
        jsonObject.put("phoneNumber", "555 555 555");
        UserDTO userDTO = Mockito.mock(UserDTO.class);

        userService.updateUser(userDTO);
        mockMvc.perform(
                put(UPDATE_USER_PATH)
                        .content(String.valueOf(jsonObject))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}
