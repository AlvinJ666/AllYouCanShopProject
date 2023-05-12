package com.allyoucanshop.common.persistence.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 32, nullable = false, unique = true, columnDefinition = " varchar(32)")
    private String name;

    @Column(length = 256, nullable = false, columnDefinition = " varchar(256)")
    private String description;
}
