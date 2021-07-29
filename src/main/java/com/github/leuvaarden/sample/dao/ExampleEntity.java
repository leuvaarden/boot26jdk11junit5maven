package com.github.leuvaarden.sample.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "EXAMPLE_ENTITY")
public class ExampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXAMPLE_SEQUENCE")
    @SequenceGenerator(name = "EXAMPLE_SEQUENCE", allocationSize = 1)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "VALUE", nullable = false)
    private String value;
}
