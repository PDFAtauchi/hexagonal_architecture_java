package com.practice.wannapizza.adapter.in.web;


import com.github.javafaker.Faker;
import com.practice.wannapizza.application.domain.model.Pizza;
import com.practice.wannapizza.port.in.PizzaUseCasePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

import static org.hamcrest.CoreMatchers.is;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class PizzaRestUIControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PizzaUseCasePort pizzaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pizza myPizza;

    private String serviceUrl;

    @BeforeEach
    public void setup() {
        myPizza = generatePizzaObject();
        serviceUrl = "/api/pizza/";
    }

    public Pizza generatePizzaObject() {
        Faker faker = new Faker(new Locale("en-US"));
        long id = 0;
        String toppings[] = {faker.food().ingredient(), faker.food().ingredient(), faker.food().ingredient()};
        Pizza pizza = Pizza.builder()
                .id(id)
                .name(faker.food().dish())
                .toppings(toppings)
                .build();

        return pizza;
    }

    @Test
    public void givenPizzaObject_whenCreatePizza_thenReturnCreatedPizza() throws Exception {
        // Given
        given(pizzaService.createPizza(any(Pizza.class)))
                .willAnswer((invocation) -> {
                    Pizza pizzaArgument = invocation.getArgument(0);
                    return Optional.of(pizzaArgument);
                });

        // When
        ResultActions response = mockMvc.perform(post(serviceUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(myPizza)));

        // Then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(myPizza.getName())));

        MvcResult result = response.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Pizza responsePizza = objectMapper.readValue(contentAsString, Pizza.class);
        assertThat(responsePizza.getToppings()).isEqualTo(myPizza.getToppings());
    }

    @Test
    public void givenWrongPizza_whenCreatePizza_thenReturnBadRequest() throws Exception {
        // Given
        given(pizzaService.createPizza(any(Pizza.class)))
                .willAnswer((invocation) -> {
                    Pizza pizzaArgument = invocation.getArgument(0);
                    return Optional.empty();
                });

        // When
        ResultActions response = mockMvc.perform(post(serviceUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(myPizza)));

        // Then
        response
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPizzaName_whenGetPizza_thenReturnPizza() throws Exception {
        // Given
        given(pizzaService.getPizza(myPizza.getName())).willReturn(Optional.of(myPizza));

        // When
        ResultActions response = mockMvc.perform(get(serviceUrl + "{name}/", myPizza.getName()));

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(myPizza.getName())));

        MvcResult result = response.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Pizza responsePizza = objectMapper.readValue(contentAsString, Pizza.class);
        assertThat(responsePizza.getToppings()).isEqualTo(myPizza.getToppings());
    }

    @Test
    public void givenThereIsNoPizza_whenGetPizza_thenReturnNotFound() throws Exception {
        // Given
        given(pizzaService.getPizza(myPizza.getName())).willReturn(Optional.empty());

        // When
        ResultActions response = mockMvc.perform(get(serviceUrl + "{name}/", myPizza.getName()));

        // Then
        response
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenListOfPizzas_whenGetPizzas_thenReturnListOfPizzas() throws Exception {
        // Given
        Pizza pizza2 = generatePizzaObject();
        List<Pizza> myPizzas = List.of(myPizza, pizza2);

        given(pizzaService.getAllPizza()).willReturn(myPizzas);

        // When
        ResultActions response = mockMvc.perform(get(serviceUrl));

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(myPizzas.size())));
    }

    @Test
    public void givenThereIsNoPizzas_whenGetPizzas_thenReturnEmptyListOfPizzas() throws Exception {
        // Given
        List<Pizza> myPizzas = new ArrayList<>();
        given(pizzaService.getAllPizza()).willReturn(myPizzas);

        // When
        ResultActions response = mockMvc.perform(get(serviceUrl));

        // Then
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));
    }

}
