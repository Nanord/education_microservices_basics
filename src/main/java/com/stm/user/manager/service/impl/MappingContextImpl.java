package com.stm.user.manager.service.impl;

import com.stm.user.manager.service.MappingContext;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.IdentityHashMap;
import java.util.Map;

@Slf4j
@Component
@RequestScope
public class MappingContextImpl implements MappingContext {
    private Map<Object, Object> knownInstances = new IdentityHashMap<>();

    @Override
    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
        return (T) knownInstances.get( source );
    }

    @Override
    @AfterMapping
    public void storeMappedInstance(Object source, @MappingTarget Object target) {
        knownInstances.put( source, target );
    }
}