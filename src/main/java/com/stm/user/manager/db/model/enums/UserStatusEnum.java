package com.stm.user.manager.db.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


//TODO Таким способом мы можем хранить id справочных таблиц
@RequiredArgsConstructor
public enum  UserStatusEnum {
    CREATED(1),
    ACTIVE(2),
    BLOCKED(3),
    DELETED(4);

    @Getter
    private final Integer id;

    public static Integer idByName(String status) {
        if (StringUtils.isEmpty(status)) {
            return null;
        }
        return Arrays.stream(UserStatusEnum.values())
                .filter(name -> StringUtils.equals(status, name.name()))
                .map(UserStatusEnum::getId)
                .findFirst()
                .orElse(null);
    }
}
