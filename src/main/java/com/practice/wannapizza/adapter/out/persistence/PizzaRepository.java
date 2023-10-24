package com.practice.wannapizza.adapter.out.persistence;

import com.practice.wannapizza.application.domain.model.Pizza;
import com.practice.wannapizza.port.out.PizzaRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PizzaRepository implements PizzaRepositoryPort {

    private Map<String, Pizza> pizzaStore = new HashMap<String, Pizza>();

    @Override
    public Optional<Pizza> createPizza(Pizza pizza) {
        pizzaStore.put(pizza.getName(), pizza);
        Pizza myPizza = pizzaStore.get(pizza.getName());
        return Optional.of(myPizza);
    }

    @Override
    public Optional<Pizza> getPizza(String name) {
        Pizza myPizza = pizzaStore.get(name);
        if (myPizza != null) {
            return Optional.of(myPizza);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Pizza> getAllPizza() {
        return pizzaStore.values().stream().collect(Collectors.toList());
    }
}
