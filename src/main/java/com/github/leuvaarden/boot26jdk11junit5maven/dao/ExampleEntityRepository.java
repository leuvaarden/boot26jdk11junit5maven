package com.github.leuvaarden.boot26jdk11junit5maven.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleEntityRepository extends JpaRepository<ExampleEntity, Long> {
}
