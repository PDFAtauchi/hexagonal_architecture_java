package com.practice.wannapizza.adapter.in.web;

import com.practice.wannapizza.application.domain.model.Pizza;
import com.practice.wannapizza.port.in.PizzaRestUIPort;
import com.practice.wannapizza.port.in.PizzaUseCasePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pizza/")
public class PizzaRestUIController implements PizzaRestUIPort {
    private PizzaUseCasePort pizzaService;

    public PizzaRestUIController(PizzaUseCasePort pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Override
    public ResponseEntity<Pizza> createPizza(Pizza pizza) {
        return pizzaService.createPizza(pizza)
                .map(createdPizza ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(createdPizza))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<Pizza> getPizza(String name) {
        return pizzaService.getPizza(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public List<Pizza> getAllPizza() {
        return pizzaService.getAllPizza();
    }
}
