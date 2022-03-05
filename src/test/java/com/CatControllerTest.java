package com;

import com.Controller.CatController;
import com.Model.Cat;
import com.Model.JdbcCatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith (SpringRunner.class)
@WebMvcTest (CatController.class)
public class CatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // mock the bean, to focus test on mockMvc
    @MockBean
    private JdbcCatRepository catRepository;

    @Test
    public void getCatById() throws Exception {
        when(catRepository.findCatById(1)).thenReturn(new Cat("Lucy", "female",
                true, false, false,
                "../static/images/cat1.jpeg", "images/cat1.jpeg"));

        mockMvc.perform(get("/api/cat/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lucy"));
    }

    @Test
    public void postCat() throws Exception {
        Cat cat = new Cat("Lily", "female",
                true, false, false,
                "../static/images/cat1.jpeg", "images/cat1.jpeg");
        String json = new ObjectMapper().writeValueAsString(cat);
        when(catRepository.saveCat(cat)).thenReturn(cat);
        mockMvc.perform(post("/api/addcat")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"));
    }

}
