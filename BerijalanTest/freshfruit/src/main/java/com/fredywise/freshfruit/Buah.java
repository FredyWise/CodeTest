package com.fredywise.freshfruit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Buah {
    private Integer id;
    private String name;
    private Integer price;
}
