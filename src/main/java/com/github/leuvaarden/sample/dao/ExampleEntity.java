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
@Table(name = "example_entity")
public class ExampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exampleGenerator")
    @SequenceGenerator(name = "exampleGenerator", sequenceName = "example_sequence")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "value", nullable = false)
    private String value;
}
