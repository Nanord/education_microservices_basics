package com.stm.user.manager.service;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

public interface MappingContext {

    @BeforeMapping
    <T> T getMappedInstance(Object source, @TargetType Class<T> targetType);


    @BeforeMapping
   void storeMappedInstance(Object source, @MappingTarget Object target);
}
