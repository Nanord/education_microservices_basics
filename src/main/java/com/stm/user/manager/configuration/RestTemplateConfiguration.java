package com.stm.user.manager.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
//todo: настраивайте restTemplate правильно
// Бонусом пример настройки с автоматической авторизацией.
public class RestTemplateConfiguration {

    private static String BEARER_HEADER = "Bearer %s";

    @Value("${custom.api.access-token:123}")
    private String accessToken;

    @Bean
    @Primary
    public RestTemplate restTemplateForCustomApi(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(getAuthRequestInterceptor())
                .build();
    }

    private ClientHttpRequestInterceptor getAuthRequestInterceptor() {
        return (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            if (StringUtils.isNotEmpty(accessToken)) {
                headers.putIfAbsent(HttpHeaders.AUTHORIZATION, Collections.singletonList(String.format(BEARER_HEADER, accessToken)));
            }
            return execution.execute(request, body);
        };
    }
}
