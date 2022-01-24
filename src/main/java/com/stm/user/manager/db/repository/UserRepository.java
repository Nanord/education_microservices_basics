package com.stm.user.manager.db.repository;

import com.stm.user.manager.db.entity.UserEntity;
import com.stm.user.manager.db.model.enums.UserStatusEnum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends AbstractRepository<UserEntity> {
    @Query(nativeQuery = true, value = "update user set user_status = 4")
    void deleteUser(int id);

    //todo: В данном сервисе нет такого, но, если вам нужно обновить часть объекта с определенными заранее полями со стороны клиента
    // То используйте кастомный update с аннотацией Modifying(работать иначе не будет). (Запрос в базу всегда быстрее hibernate entity)
    // Например что то вроде такого
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE user " +
                    "SET status = :status")
    void updateStatus(Integer id, String status);
}
