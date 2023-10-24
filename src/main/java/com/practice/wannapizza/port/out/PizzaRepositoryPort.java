package com.practice.wannapizza.port.out;

import com.practice.wannapizza.application.domain.model.Pizza;

import java.util.List;

public interface PizzaRepositoryPort {
    void createPizza(Pizza pizza);

    Pizza getPizza(String name);

    List<Pizza> getAllPizza();
}
