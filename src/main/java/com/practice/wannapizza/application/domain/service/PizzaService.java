package com.practice.wannapizza.application.domain.service;

import com.practice.wannapizza.application.domain.model.Pizza;
import com.practice.wannapizza.port.in.PizzaUseCasePort;
import com.practice.wannapizza.port.out.PizzaRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PizzaService implements PizzaUseCasePort {

    @Autowired
    private PizzaRepositoryPort pizzaRepository;


    @Override
    public Optional<Pizza> createPizza(Pizza pizza) {
        return pizzaRepository.createPizza(pizza);
    }

    @Override
    public Optional<Pizza> getPizza(String name) {
        return pizzaRepository.getPizza(name);
    }

    @Override
    public List<Pizza> getAllPizza() {
        return pizzaRepository.getAllPizza();
    }
}
