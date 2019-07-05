package broadcom.rest;

import broadcom.rest.controller.ApiController;
import broadcom.rest.controller.request.RequestForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ApiController.class)
@ContextConfiguration(classes = BroadcomConfig.class)
class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void scenario_01_deposit_out_of_capacity() throws Exception {
        RequestForm requestForm = new RequestForm();
        requestForm.setType("Soda");

        check(post("/api/deposit"), requestForm, status().isOk(),"{\"status\":\"OK\",\"id\":1}");
        check(post("/api/deposit"), requestForm, status().isOk(), "{\"status\":\"OK\",\"id\":2}");
        check(post("/api/deposit"), requestForm, status().isBadRequest(),"{\"status\":\"Out of vending machine capacity\"}");

        check(get("/api/getlist"), requestForm, status().isOk(), "[{\"id\":1,\"type\":\"Soda\"},{\"id\":2,\"type\":\"Soda\"}]");

        check(post("/api/withdraw"), requestForm, status().isOk(),"{\"status\":\"OK\"}");

        check(get("/api/getlist"), requestForm, status().isOk(), "[{\"id\":2,\"type\":\"Soda\"}]");

        check(post("/api/withdraw"), requestForm, status().isOk(),"{\"status\":\"OK\"}");
        check(post("/api/withdraw"), requestForm, status().isOk(),"{\"status\":\"N/A\"}");
    }

    @Test
    void scenario_02_deposit_not_allowed() throws Exception {
        RequestForm requestForm = new RequestForm();
        requestForm.setType("NOT ALLOWED");

        check(post("/api/deposit"), requestForm, status().isBadRequest(),"{\"status\":\"Item type 'NOT ALLOWED' not allowed\"}");

        requestForm.setType("Soda");
        check(post("/api/deposit"), requestForm, status().isOk(),"{\"status\":\"OK\",\"id\":1}");

        requestForm.setType("Candy");
        check(post("/api/deposit"), requestForm, status().isOk(),"{\"status\":\"OK\",\"id\":2}");

        requestForm.setType("Toy");
        check(post("/api/deposit"), requestForm, status().isOk(),"{\"status\":\"OK\",\"id\":3}");
    }

    private void check(MockHttpServletRequestBuilder builder, RequestForm requestForm, ResultMatcher matcher, String response) throws Exception {
        mockMvc.perform(builder
                .content(objectMapper.writeValueAsString(requestForm))
                .contentType("application/json"))
                .andExpect(matcher)
                .andExpect(content().json(response));
    }
}
