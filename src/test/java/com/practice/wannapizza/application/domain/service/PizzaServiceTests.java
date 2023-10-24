package com.practice.wannapizza.application.domain.service;

import com.github.javafaker.Faker;
import com.practice.wannapizza.application.domain.model.Pizza;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.practice.wannapizza.port.out.PizzaRepositoryPort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTests {

    @Mock
    private PizzaRepositoryPort pizzaRepository;

    @InjectMocks
    private PizzaService pizzaService;

    private Pizza myPizza;

    @BeforeEach
    public void setup() {
        myPizza = generatePizzaObject();
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
    public void givenPizza_whenSaved_thenReturnPizzaSaved() {
        // Given
        given(pizzaRepository.createPizza(myPizza)).willReturn(Optional.of(myPizza));

        // When
        Optional<Pizza> savedPizza = pizzaService.createPizza(myPizza);

        // Then
        assertThat(savedPizza).isNotEmpty();
        assertThat(savedPizza.get()).isEqualTo(myPizza);
        verify(pizzaRepository, times(1)).createPizza(myPizza);
    }

    @Test
    public void givenWrongPizzaInfo_whenSaved_thenReturnNull() {
        // Given
        myPizza.setName("");
        given(pizzaRepository.createPizza(myPizza)).willReturn(Optional.empty());

        // When
        Optional<Pizza> savedPizza = pizzaService.createPizza(myPizza);

        // Then
        assertThat(savedPizza).isEmpty();
        verify(pizzaRepository, times(1)).createPizza(myPizza);
    }

    @Test
    public void givenValidAndExistedPizzaName_whenGetPizza_thenReturnPizza() {
        // Given
        given(pizzaRepository.getPizza(myPizza.getName())).willReturn(Optional.of(myPizza));

        // When
        String pizzaName = myPizza.getName();
        Optional<Pizza> retrievedPizza = pizzaService.getPizza(pizzaName);

        // Then
        assertThat(retrievedPizza).isNotEmpty();
        assertThat(retrievedPizza.get()).isEqualTo(myPizza);
        verify(pizzaRepository, times(1)).getPizza(pizzaName);
    }

    @Test
    public void givenPizzaName_whenGetPizza_thenReturnNull() {
        // Given
        String pizzaNotFoundName = "Cangre burger";
        given(pizzaRepository.getPizza(pizzaNotFoundName)).willReturn(Optional.empty());

        // When
        Optional<Pizza> retrievedPizza = pizzaService.getPizza(pizzaNotFoundName);

        // Then
        assertThat(retrievedPizza).isEmpty();
        verify(pizzaRepository, times(1)).getPizza(pizzaNotFoundName);
    }

    @Test
    public void givenPizzas_whenGetAllPizzas_thenReturnAllPizzas() {
        // Given
        Pizza pizza2 = generatePizzaObject();
        List<Pizza> myPizzas = List.of(myPizza, pizza2);

        given(pizzaRepository.getAllPizza()).willReturn(myPizzas);

        // When
        List<Pizza> retrievePizzas = pizzaService.getAllPizza();

        // Then
        assertThat(retrievePizzas.size()).isEqualTo(2);
        verify(pizzaRepository, times(1)).getAllPizza();
    }

    @Test
    public void givenNoPizzas_whenGetAllPizzas_thenReturnNoPizzas() {
        // Given
        given(pizzaRepository.getAllPizza()).willReturn(List.of());

        // When
        List<Pizza> retrievePizzas = pizzaService.getAllPizza();

        // Then
        assertThat(retrievePizzas.size()).isEqualTo(0);
        verify(pizzaRepository, times(1)).getAllPizza();
    }
}
