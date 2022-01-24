package com.stm.user.manager.mapper;

import com.stm.user.manager.constants.DefaultConstants;
import com.stm.user.manager.db.entity.UserEntity;
import com.stm.user.manager.dto.UserDto;
import com.stm.user.manager.dto.UserWithRoleDto;
import com.stm.user.manager.service.MappingContext;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


//TODO: Маперы во время компиляции не умеют взаимодействовать друг с другом и искать нужные методы мапинга
// Для этого мы используем 'uses = {RoleMapper.class, UserStatusMapper.class}'.
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {RoleMapper.class, UserStatusMapper.class})
public interface UserMapper {
    UserDto toDto(UserEntity entity);

    @Mapping(target = "roles", source = "roles", ignore = true)
    UserDto toMinDto(UserEntity entity);

    @Named("toUserWithRoleDto")
    UserWithRoleDto toUserWithRoleDto(UserEntity entity, @Context MappingContext mappingContext);


    //TODO: В случае, когда у нас 2 сигнатурно одинаковых маппинга, которые используются в других маперах,
    // Mapstruct может выдать ошибку. Необходимо будет выбрать нужный, используя аннотацию @Named("nameMethod")
    // И указать его в qualifiedByName. Как в данном примере с маппингом ролей.
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(source = "status", target = "status", defaultValue = DefaultConstants.DEFAULT_STATUS)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "fromDtoListWithDefault")
    UserEntity fromDto(UserDto dto);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "fromDtoListWithDefault")
    UserEntity updateFromDto(UserDto dto, @MappingTarget UserEntity entity);

    //TODO nullValuePropertyMappingStrategy указывается не только на весь маппинг,
    // но и при маппинге отдельных полей аннотацией @Mapping(nullValuePropertyMappingStrategy=)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    //TODO mapstruct по умолчанию чистит список объектов перед вставкой, но в данном случае, хочу показать, как этого избежать
    @Mapping(source = "roles", target = "roles", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    UserEntity updatePartialFromDto(UserDto dto, @MappingTarget UserEntity entity);
}
