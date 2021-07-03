package nl.hu.cisq1.lingo.trainer.presentation;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

@SpringBootTest
@ActiveProfiles("ci")
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class TrainerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("")
    void startNewGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/trainer/game");

        mockMvc.perform(request)
            .andDo(print())
            .andExpect(jsonPath("$.score", is(0)))
            .andExpect(jsonPath("$.feedbacks", hasSize(0)))
            .andExpect(jsonPath("$.status", is(GameStatus.DOGUESS.toString())))
            .andExpect(jsonPath("$.hint", is("p....")))
            .andExpect(status().isCreated());
    }
}
