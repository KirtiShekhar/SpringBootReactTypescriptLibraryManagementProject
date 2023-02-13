package com.fullstack.library.management.backend.additional.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstack.library.management.backend.additional.SpringBootLibraryManagementApplicationTests;
import com.fullstack.library.management.backend.additional.entity.Book;
import com.fullstack.library.management.backend.additional.entity.History;
import com.fullstack.library.management.backend.additional.jwtsecurity.JwtProvider;
import com.fullstack.library.management.backend.additional.mockEntityDtoData.mockData;
import com.fullstack.library.management.backend.additional.repository.HistoryRepository;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import com.fullstack.library.management.backend.additional.security.UserPrinciple;
import com.fullstack.library.management.backend.additional.service.AdminService;
import com.fullstack.library.management.backend.additional.service.HistoryService;
import com.fullstack.library.management.backend.additional.utils.ExtractJWT;
import com.fullstack.library.management.backend.additional.utils.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = SpringBootLibraryManagementApplicationTests.class)
@ActiveProfiles("test")
public class HistoryControllerTest extends mockData
{
    @Value("${sql.create.table.history}")
    private String createTableHistoryQuery;

    @Value("${sql.insert.history}")
    private String insertDataHistoryQuery;

    @Value("${sql.delete.history}")
    private String deleteHistoryQuery;

    @Value("${sql.drop.history}")
    private String dropHistoryQuery;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger ControllerTestLogger = LoggerFactory.getLogger(HistoryControllerTest.class);

    @MockBean
    private HistoryService historyService;

    @InjectMocks
    private HistoryController historyController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @BeforeEach
    private void setDatabaseBeforeTransaction()
    {
        jdbcTemplate.execute(createTableHistoryQuery);
        jdbcTemplate.execute(insertDataHistoryQuery);
    }

    @Test
    @DisplayName("Get all History : Positive")
    void getAllHistoryPositiveTest() throws Exception
    {
        Set<GrantedAuthority> userAuthorities = Set.of(SecurityUtils.convertToAuthority("USER"));

        String jwtAuthToken = jwtProvider.generateToken(new UserPrinciple(mockUserData().getUserId(),mockUserData().getUserName(),mockUserData().getPassword(),mockUserData(),userAuthorities));
        ResponseMessages<List<History>> historyListPositiveResponseMessage = new ResponseMessages<>();
        historyListPositiveResponseMessage.setData(mockHistoryListPositiveData());
        when(historyService.getAllHistory()).thenReturn(historyListPositiveResponseMessage);
        // context
        ResultActions getAllHistoriesPositiveResult = mockMvc.perform(get("/api/history/service/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+ jwtAuthToken))
                .andExpect(status().isOk());
        // logger
        ControllerTestLogger.error("JSON RESPONSE {}",getAllHistoriesPositiveResult.andReturn().getResponse().getContentAsString());
        System.out.println("JSON RESPONSE {}"+getAllHistoriesPositiveResult.andReturn().getResponse().getContentAsString());
        // event
        getAllHistoriesPositiveResult.andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data",hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @DisplayName("Get all History : Negative")
    void getAllHistoryNegativeTest() throws Exception
    {
        Set<GrantedAuthority> userAuthorities = Set.of(SecurityUtils.convertToAuthority("USER"));

        String jwtAuthToken = jwtProvider.generateToken(new UserPrinciple(mockUserData().getUserId(),mockUserData().getUserName(),mockUserData().getPassword(),mockUserData(),userAuthorities));
        ResponseMessages<List<History>> historyListNegativeResponseMessage = new ResponseMessages<>();
        historyListNegativeResponseMessage.setData(mockHistoryListNegativeData());
        when(historyService.getAllHistory()).thenReturn(historyListNegativeResponseMessage);
        // context
        ResultActions getAllHistoriesNegativeResult = mockMvc.perform(get("/api/history/service/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+ jwtAuthToken))
                .andExpect(status().isOk());
        // logger
        ControllerTestLogger.error("JSON RESPONSE {}",getAllHistoriesNegativeResult.andReturn().getResponse().getContentAsString());
        System.out.println("JSON RESPONSE {}"+getAllHistoriesNegativeResult.andReturn().getResponse().getContentAsString());
        // event
        getAllHistoriesNegativeResult.andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.size()",is(mockHistoryListNegativeData().size())))
                .andExpect(jsonPath("$.data",hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @DisplayName("Get all History for Given User Page Wise : Positive")
    void getAllHistoryForGivenUserPageWisePositiveTest() throws Exception
    {
        Set<GrantedAuthority> userAuthorities = Set.of(SecurityUtils.convertToAuthority("USER"));

        String jwtAuthToken = jwtProvider.generateToken(new UserPrinciple(mockUserData().getUserId(),mockUserData().getUserName(),mockUserData().getPassword(),mockUserData(),userAuthorities));

        String userEmail = mockHistoryData().getUserEmail();

        ControllerTestLogger.info("userEmail Extracted {} ",userEmail);
        System.out.println("userEmail Extracted {} "+userEmail);

        // list and pages content
        Page<History> historyListPageWisePositive = new PageImpl<>(mockHistoryListPositiveData());
        ResponseMessages<Page<History>> historyPageWisePositiveResponseMessage = new ResponseMessages<>();
        historyPageWisePositiveResponseMessage.setData(historyListPageWisePositive);
        when(historyService.getBookCheckOutHistoryForUserPageWise(userEmail,0,2)).thenReturn(historyPageWisePositiveResponseMessage);
        // context
        ResultActions getAllHistoriesFOrGivenUserPositiveResult = mockMvc.perform(get("/api/history/service/all/byUser/pageWise?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+ jwtAuthToken))
                .andExpect(status().isOk());
        // logger
        ControllerTestLogger.error("JSON RESPONSE {}",getAllHistoriesFOrGivenUserPositiveResult.andReturn().getResponse().getContentAsString());
        System.out.println("JSON RESPONSE {}"+getAllHistoriesFOrGivenUserPositiveResult.andReturn().getResponse().getContentAsString());
        // event
        getAllHistoriesFOrGivenUserPositiveResult
                .andExpect(jsonPath("$.data.content").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.data.size").doesNotHaveJsonPath())
                .andExpect(jsonPath("$.data.totalElements").doesNotHaveJsonPath());
    }

    @Test
    @DisplayName("Get all History for Given User Page Wise : Negative")
    void getAllHistoryForGivenUserPageWiseNegativeTest() throws Exception
    {
        Set<GrantedAuthority> userAuthorities = Set.of(SecurityUtils.convertToAuthority("USER"));

        String jwtAuthToken = jwtProvider.generateToken(new UserPrinciple(mockUserData().getUserId(),mockUserData().getUserName(),mockUserData().getPassword(),mockUserData(),userAuthorities));

        String userEmail = mockUserData().getEmailAddress();

        ControllerTestLogger.info("userEmail Extracted {} ",userEmail);
        System.out.println("userEmail Extracted {} "+userEmail);

        // list and pages content
        List<History> historyListNegative = mockHistoryListNegativeData();
        Page<History> historyListPageWiseNegative = new PageImpl<>(historyListNegative);
        ResponseMessages<Page<History>> historyPageWiseNegativeResponseMessage = new ResponseMessages<>();
        historyPageWiseNegativeResponseMessage.setData(historyListPageWiseNegative);
        when(historyService.getBookCheckOutHistoryForUserPageWise(userEmail,0,5)).thenReturn(historyPageWiseNegativeResponseMessage);
        // context
        ResultActions getAllHistoriesFOrGivenUserNegativeResult = mockMvc.perform(get("/api/history/service/all/byUser/pageWise?page=0&size=5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+ jwtAuthToken))
                .andExpect(status().isOk());
        // logger
        ControllerTestLogger.error("JSON RESPONSE {}",getAllHistoriesFOrGivenUserNegativeResult.andReturn().getResponse().getContentAsString());
        System.out.println("JSON RESPONSE {}"+getAllHistoriesFOrGivenUserNegativeResult.andReturn().getResponse().getContentAsString());
        // event
        getAllHistoriesFOrGivenUserNegativeResult
                .andExpect(jsonPath("$.data.content").hasJsonPath())
                .andExpect(jsonPath("$.data.size").hasJsonPath())
                .andExpect(jsonPath("$.data.totalElements").hasJsonPath());
    }

    @AfterEach
    private void setDatabaseAfterTransaction()
    {
        jdbcTemplate.execute(deleteHistoryQuery);
        jdbcTemplate.execute(dropHistoryQuery);
    }


}
