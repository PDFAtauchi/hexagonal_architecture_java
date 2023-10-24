package com.practice.wannapizza.port.out;

import com.practice.wannapizza.application.domain.model.Pizza;

import java.util.List;
import java.util.Optional;

public interface PizzaRepositoryPort {
    Optional<Pizza> createPizza(Pizza pizza);

    Optional<Pizza> getPizza(String name);

    List<Pizza> getAllPizza();
}
