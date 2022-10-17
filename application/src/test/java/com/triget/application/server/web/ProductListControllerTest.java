package com.triget.application.server.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductListController.class)
public class ProductListControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void returnPublicListResponseDto() throws Exception {
        Map<String, String> input = new HashMap<>();

        input.put("place", "Tokyo");
        input.put("theme", "테마");
        input.put("peopleNum", String.valueOf(2));
        input.put("departureDate", "2022-09-01");
        input.put("arrivalDate", "2022-09-04");
        input.put("budget", String.valueOf(4000000));
        input.put("flightsPrior", String.valueOf(3));
        input.put("accommodationsPrior", String.valueOf(5));
        input.put("restaurantsPrior", String.valueOf(4));
        input.put("attractionsPrior", String.valueOf(2));

        mvc.perform(
                post("/product/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
