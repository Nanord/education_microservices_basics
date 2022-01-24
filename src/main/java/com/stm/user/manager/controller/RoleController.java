package com.stm.user.manager.controller;

import com.stm.user.manager.db.repository.RoleRepository;
import com.stm.user.manager.dto.RoleDto;
import com.stm.user.manager.dto.RoleWithUserDto;
import com.stm.user.manager.mapper.RoleMapper;
import com.stm.user.manager.service.MappingContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
@Validated
@Slf4j
@Tag(name = "Role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    private MappingContext mappingContext;

    @GetMapping
    public ResponseEntity<List<RoleWithUserDto>> getAll() {
        List<RoleWithUserDto> collect = roleRepository.findAll()
                .stream()
                .map(role -> roleMapper.toRoleWithUserDto(role, mappingContext))
                .collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

}
