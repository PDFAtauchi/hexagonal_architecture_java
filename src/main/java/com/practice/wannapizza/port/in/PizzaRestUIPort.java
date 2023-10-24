package com.practice.wannapizza.port.in;

import com.practice.wannapizza.application.domain.model.Pizza;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public interface PizzaRestUIPort {
    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizza);

    @GetMapping("{name}/")
    public ResponseEntity<Pizza> getPizza(@PathVariable String name);

    @GetMapping
    public List<Pizza> getAllPizza();
}
