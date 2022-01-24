package com.stm.user.manager.controller;

import com.stm.user.manager.db.entity.UserEntity;
import com.stm.user.manager.db.repository.UserRepository;
import com.stm.user.manager.dto.UserDto;
import com.stm.user.manager.dto.UserWithRoleDto;
import com.stm.user.manager.exception.CrudEOperationException;
import com.stm.user.manager.mapper.UserMapper;
import com.stm.user.manager.service.MappingContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/profiles")
//TODO: Без аннтации @Validated валидация метод проходить не будет.
// Данную аннотацию можно использовать и в сервисах. Выбрасывает ошибку MethodArgumentNotValidException
@Validated
@Slf4j
@Tag(name = "Profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    private MappingContext mappingContext;

    @Operation(description = "Получение объекта")
    @GetMapping("/{id}")
    public ResponseEntity<UserWithRoleDto> getUserById(@NotNull @PathVariable("id") Integer id) {
        return userRepository.findById(id)
                .map(user -> userMapper.toUserWithRoleDto(user, mappingContext))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(description = "Получение списка объектов. Постраничная разбивка, сортировка.",
            parameters = {
                    @Parameter(name = "size", example = "1", required = true),
                    @Parameter(name = "page", example = "0", required = true),
                    @Parameter(name = "sort", example = "created,id")
            })
    @GetMapping
    public Page<UserDto> getPageable(@PageableDefault Pageable page) {
        return userRepository.findAll(page)
                .map(userMapper::toMinDto);
    }

    @Operation(description = "Создание объекта")
    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> insertUser(@Valid @RequestBody UserDto dto) throws CrudEOperationException {
        return Optional.ofNullable(dto)
                .map(userMapper::fromDto)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CrudEOperationException("Cannot insert entity: " + dto));
    }

    @Operation(description = "Удаление объекта")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        userRepository.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Частичное редактирование объекта")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserDto> updatePartial(@PathVariable("id") Integer id, @RequestBody UserDto dto) throws CrudEOperationException {
        Optional<UserEntity> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return optional
                .map(entity -> userMapper.updatePartialFromDto(dto, entity))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CrudEOperationException("Cannot update entity by id:" + id));
    }

    @Operation(description = "Редактирование объекта")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateFull(@PathVariable("id") Integer id, @RequestBody @Valid UserDto dto) throws CrudEOperationException {
        Optional<UserEntity> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return optional
                .map(entity -> userMapper.updateFromDto(dto, entity))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CrudEOperationException("Cannot update entity by id:" + id));
    }
}