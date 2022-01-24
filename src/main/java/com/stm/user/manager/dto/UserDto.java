package com.stm.user.manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Пользователь")
public class UserDto {
    @Schema(description = "Идентификтор обьекта")
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Schema(description = "Дата и время создания")
    private ZonedDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Schema(description = "Дата и время последнего изменения")
    private ZonedDateTime modified;
    @NotEmpty
    @Schema(description = "Имя пользователя")
    private String name;
    @Schema(description = "Статус пользователя")
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Список ролей")
    private List<String> roles;
}
