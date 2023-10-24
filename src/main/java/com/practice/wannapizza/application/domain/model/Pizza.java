package com.practice.wannapizza.application.domain.model;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pizza {

    private long id;
    private String name;
    private String[] toppings;
}
