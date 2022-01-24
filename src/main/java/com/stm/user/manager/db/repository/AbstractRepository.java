package com.stm.user.manager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

//TODO
// Чаще всего абстрактный репозиторий создают для объединения двух интерфейсов JpaRepository и JpaSpecificationExecutor.
// JpaRepository - если не знаете какой репозиторий вам нужен, то выбераете его
// JpaSpecificationExecutor - Предоставляет методы для работы с CriteriaAPI. Динамические запросы страшно, но красиво.
@NoRepositoryBean
public interface AbstractRepository<E> extends JpaRepository<E, Integer>, JpaSpecificationExecutor<E> {
}
