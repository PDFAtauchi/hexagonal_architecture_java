package com.practice.wannapizza.port.in;

import java.util.List;

import com.practice.wannapizza.application.domain.model.Pizza;

public interface PizzaUseCasePort {
    public void createPizza(Pizza pizza);

    public Pizza getPizza(String name);

    public List<Pizza> getAllPizza();
}
