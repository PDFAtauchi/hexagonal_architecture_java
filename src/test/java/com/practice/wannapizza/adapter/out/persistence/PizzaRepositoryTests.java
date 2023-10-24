package com.practice.wannapizza.adapter.out.persistence;

import com.practice.wannapizza.application.domain.model.Pizza;
import com.practice.wannapizza.port.out.PizzaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PizzaRepositoryTests {
    @Autowired
    private PizzaRepositoryPort pizzaRepository;

    private Pizza myPizza;

    @BeforeEach
    public void setup() {
        long id = 1L;
        String name = "My Pizza";
        String toppings[] = {"Tomato", "Mozzarella", "Basil"};
        myPizza = Pizza.builder()
                .id(id)
                .name(name)
                .toppings(toppings)
                .build();
    }

    @Test
    public void givenPizzaObject_whenCreatePizza_thenReturnCreatedPizza() {
        // Given a pizza

        // When
        Optional<Pizza> createdPizza = pizzaRepository.createPizza(myPizza);

        // Then
        assertThat(createdPizza.isPresent());
        assertThat(createdPizza.get()).isEqualTo(myPizza);
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
        myPizza.setName("New Pizza");
        pizzaRepository.createPizza(myPizza);
        myPizza.setName("Other Pizza");
        pizzaRepository.createPizza(myPizza);

        // When
        List<Pizza> pizzas = pizzaRepository.getAllPizza();

        // Then
        assertThat(pizzas.size()).isEqualTo(3);
    }
}
