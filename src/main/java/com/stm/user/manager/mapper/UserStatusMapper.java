package com.stm.user.manager.mapper;

import com.stm.user.manager.db.entity.UserStatusEntity;
import com.stm.user.manager.db.model.enums.UserStatusEnum;
import com.stm.user.manager.dto.UserStatusDto;
import com.stm.user.manager.exception.ClientArgumentException;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserStatusMapper {

    UserStatusDto toDto(UserStatusEntity entity);

    // TODO: Если не указать методу @BeanMapping, то Mapstruct его не будет использовать при компиляции,
    //  что вызовало бы ошибку в данном примере
    @BeanMapping
    default String mapUserStatusToString(UserStatusEntity entity) {
        return Optional.ofNullable(entity)
                .map(UserStatusEntity::getName)
                .orElse(null);
    }

    @BeanMapping
    default UserStatusEntity mapStringToUserStatus(String status) {
        return Optional.ofNullable(status)
                .map(UserStatusEnum::idByName)
                //TODO: Хибернейту необязательно знать все поля во вложенной сущности, достаточно его идентификатора.
                .map(id -> new UserStatusEntity()
                        .setId(id)
                        .setName(status))
                .orElseThrow(() -> new ClientArgumentException("Cannot find user status by status: " + status));
    }
}
