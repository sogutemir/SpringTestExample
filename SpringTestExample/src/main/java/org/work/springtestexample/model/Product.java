package org.work.springtestexample.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String name;
    private Double price;

}
