package com.practice.wannapizza.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

public class PizzaTest {

    @Test
    public void givenAttributes_whenBuildObject_thenReturnBuiltObject() {
        // Given
        long id = 1L;
        String name = "Pizza Peperoni";
        String[] toppings = {"cheese", "onion", "Tomato", "Mozzarella", "Basil"};

        // When
        Pizza myPizza = Pizza.builder()
                .id(id)
                .name(name)
                .toppings(toppings)
                .build();

        // Then
        assertThat(myPizza).isNotNull();
        assertThat(myPizza.getId()).isEqualTo(id);
        assertThat(myPizza.getName()).isEqualTo(name);
        assertThat(myPizza.getToppings()).isEqualTo(toppings);
    }
}
