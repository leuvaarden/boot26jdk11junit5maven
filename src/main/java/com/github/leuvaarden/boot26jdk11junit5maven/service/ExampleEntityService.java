package com.github.leuvaarden.boot26jdk11junit5maven.service;

import com.github.leuvaarden.boot26jdk11junit5maven.dao.ExampleEntity;
import com.github.leuvaarden.boot26jdk11junit5maven.dao.ExampleEntityRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class ExampleEntityService {

    @Resource
    private ExampleEntityRepository exampleEntityRepository;

    @Transactional
    public ExampleEntity create(@NotNull String value) {
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setValue(value);
        return exampleEntityRepository.save(exampleEntity);
    }

    public Optional<ExampleEntity> get(long id) {
        return exampleEntityRepository.findById(id);
    }
}
