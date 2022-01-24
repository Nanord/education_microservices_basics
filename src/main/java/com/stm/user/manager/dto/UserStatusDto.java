package com.stm.user.manager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Статус пользоватлея")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatusDto {
    @Schema(description = "Идентификтор обьекта")
    private Integer id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Описание")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
}
