package com.practice.wannapizza.adapter.out.persistence;

import com.github.javafaker.Faker;
import com.practice.wannapizza.application.domain.model.Pizza;
import com.practice.wannapizza.port.out.PizzaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PizzaRepositoryTests {
    @Autowired
    private PizzaRepositoryPort pizzaRepository;

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
    public void givenPizzaObject_whenCreatePizza_thenReturnCreatedPizza() {
        // Given a pizza

        // When
        Optional<Pizza> createdPizza = pizzaRepository.createPizza(myPizza);

        // Then
        assertThat(createdPizza.isPresent());
        assertThat(createdPizza.get()).isEqualTo(myPizza);
        assertThat(createdPizza.get().getId()).isGreaterThanOrEqualTo(1L);
    }

    @Test
    public void givenPizzaName_whenGetPizza_thenReturnPizzaFound() {
        // Given a pizza
        pizzaRepository.createPizza(myPizza);

        // When
        Optional<Pizza> retrievedPizza = pizzaRepository.getPizza(myPizza.getName());

        // Then
        assertThat(retrievedPizza.isPresent());
        assertThat(retrievedPizza.get()).isEqualTo(myPizza);
    }

    @Test
    public void givenPizzaNameNoExist_whenGetPizza_thenReturnNull() {
        // Given pizza name not exist

        // When
        Optional<Pizza> retrievedPizza = pizzaRepository.getPizza(myPizza.getName());

        // Then
        assertThat(retrievedPizza.isEmpty());
    }

    @Test
    public void givenPizzas_whenGetAllPizza_thenReturnListOfPizzas() {
        // Given pizzas
        pizzaRepository.createPizza(myPizza);
        pizzaRepository.createPizza(generatePizzaObject());
        pizzaRepository.createPizza(generatePizzaObject());

        // When
        List<Pizza> pizzas = pizzaRepository.getAllPizza();

        // Then
        assertThat(pizzas.size()).isEqualTo(3);
        assertThat(pizzas.get(0).getId()).isGreaterThanOrEqualTo(1L);
        assertThat(pizzas.get(1).getId()).isGreaterThanOrEqualTo(1L);
        assertThat(pizzas.get(2).getId()).isGreaterThanOrEqualTo(1L);
    }
}
