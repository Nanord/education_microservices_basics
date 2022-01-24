package com.stm.user.manager.db.repository;

import com.stm.user.manager.db.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RoleRepository extends AbstractRepository<RoleEntity> {
    @Query(nativeQuery = true, value = "select id from role where sysname = :sysname")
    Integer idBySysname(String sysname);
}
