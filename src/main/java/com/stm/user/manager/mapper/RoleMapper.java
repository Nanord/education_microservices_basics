package com.stm.user.manager.mapper;


import com.stm.user.manager.constants.DefaultConstants;
import com.stm.user.manager.db.entity.RoleEntity;
import com.stm.user.manager.db.repository.RoleRepository;
import com.stm.user.manager.dto.RoleDto;
import com.stm.user.manager.dto.RoleWithUserDto;
import com.stm.user.manager.exception.ClientArgumentException;
import com.stm.user.manager.service.MappingContext;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = UserMapper.class)
public abstract class RoleMapper {

    @Autowired
    private RoleRepository roleRepository;

    public abstract RoleDto toDto(RoleEntity entity);

    @Mapping(source = "users", target = "users", qualifiedByName = "toUserWithRoleDto")
    public abstract RoleWithUserDto toRoleWithUserDto(RoleEntity entity, @Context MappingContext context);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    public abstract RoleEntity fromDto(RoleDto dto);

    @IterableMapping(qualifiedByName = "mapSysnameToRoleEntity")
    public abstract List<RoleEntity> fromDtoList(List<String> name);

    @IterableMapping(qualifiedByName = "mapRoleEntityToSysname")
    public abstract List<String> toRoleList(List<RoleEntity> roleEntities);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    public abstract RoleEntity updateFromDto(RoleDto dto, @MappingTarget RoleEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    public abstract RoleEntity updatePartialFromDto(RoleDto dto, @MappingTarget RoleEntity entity);

    @Named("fromDtoListWithDefault")
    public List<RoleEntity> fromDtoListWithDefault(List<String> name) {
        if (CollectionUtils.isEmpty(name)) {
            name = Collections.singletonList(DefaultConstants.DEFAULT_ROLE);
        }
        return name.stream()
                .map(this::mapSysnameToRoleEntity)
                .collect(Collectors.toList());
    }


    @Named("mapSysnameToRoleEntity")
    public RoleEntity mapSysnameToRoleEntity(String sysname) {
        //TODO: В данном случае запрос делать нет необходимости, Можно реализовать этот метод как UserStatusMapper
        return Optional.of(sysname)
                .map(roleRepository::idBySysname)
                .map(id -> new RoleEntity()
                        .setId(id)
                        .setSysname(sysname))
                .orElseThrow(() -> new ClientArgumentException("Role by name '" + sysname + "' not found!"));
    }

    @Named("mapRoleEntityToSysname")
    public String mapRoleEntityToSysname(RoleEntity entity) {
        return Optional.ofNullable(entity)
                .map(RoleEntity::getSysname)
                .orElse(null);
    }
}
