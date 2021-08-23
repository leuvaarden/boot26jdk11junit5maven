package com.github.leuvaarden.sample.service;

import com.github.leuvaarden.sample.dao.ExampleEntity;
import com.github.leuvaarden.sample.dao.ExampleEntityRepository;
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
