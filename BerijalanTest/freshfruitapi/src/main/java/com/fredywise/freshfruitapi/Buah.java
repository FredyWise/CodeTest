package com.fredywise.freshfruitapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Entity
@Table(name = "buah")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Buah {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "soft", nullable = false)
    private Boolean softDelete;
}

