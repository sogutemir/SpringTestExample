package org.work.springtestexample.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;

}
