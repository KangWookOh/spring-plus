//package org.example.expert.domain.user;
//
//import org.example.expert.domain.user.service.UserService;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    private static final String TEST_NICKNAME = "Userd56f584a";
//    private static final int EXECUTION_COUNT = 1000;
//
//    @Test
//    @WithMockUser
//    void testSearchUsersByNicknamePerformance() throws Exception {
//        long totalDuration = 0;
//
//        for (int i = 0; i < EXECUTION_COUNT; i++) {
//            long startTime = System.nanoTime();
//
//            MvcResult result = mockMvc.perform(get("/search")
//                            .param("nickname", TEST_NICKNAME))
//                    .andExpect(status().isOk())
//                    .andReturn();
//
//            long endTime = System.nanoTime();
//            totalDuration += (endTime - startTime);
//
//            // You can add more assertions here if needed
//        }
//
//        double averageTimeMs = (totalDuration / EXECUTION_COUNT) / 1_000_000.0;
//        System.out.printf("Average execution time: %.3f ms%n", averageTimeMs);
//    }
//}
