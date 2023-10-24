package com.practice.wannapizza.port.in;

import java.util.List;
import java.util.Optional;

import com.practice.wannapizza.application.domain.model.Pizza;

public interface PizzaUseCasePort {
    public Optional<Pizza> createPizza(Pizza pizza);

    public Optional<Pizza> getPizza(String name);

    public List<Pizza> getAllPizza();
}
