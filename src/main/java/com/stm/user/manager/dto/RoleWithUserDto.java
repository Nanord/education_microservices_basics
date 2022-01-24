package com.stm.user.manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Роль c пользователями")
public class RoleWithUserDto {
    @Schema(description = "Идентификтор обьекта")
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Дата и время создания")
    private ZonedDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Дата и время последнего изменения")
    private ZonedDateTime modified;
    @Schema(description = "Системное имя роли")
    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sysname;
    @Schema(description = "Название роли")
    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @Schema(description = "Описание роли")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @Schema(description = "Пользователи")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(value = "roles")
    private List<UserWithRoleDto> users;
}
