package com.imozayozturk.accountmanagementsystem.config.swagger;

import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(value = SwaggerProperties.class)
@EnableSwagger2
public class SwaggerConfig {

    private static final ApiKey API_KEY;
    private static final SecurityContext SECURITY_CONTEXT;

    @Value("${spring.profiles.active} - ${version:RELEASE}")
    private String version;

    private final SwaggerProperties properties;

    static {
        SECURITY_CONTEXT = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(SwaggerConstants.ANY_PATH_REGEX))
                .build();
        API_KEY = new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, SwaggerConstants.PASS_AS);
    }

    public SwaggerConfig(SwaggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .protocols(getProtocols())
                .host(properties.getHost().getUrl())
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage(SwaggerConstants.SPRING_BASE_PACKAGE)))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(properties.getIgnoredParameterTypes())
                .securitySchemes(Collections.singletonList(API_KEY))
                .securityContexts(Collections.singletonList(SECURITY_CONTEXT))
                .apiInfo(apiInfo());
    }

    private Set<String> getProtocols() {
        return Sets.newHashSet(Optional.ofNullable(properties.getHost()).map(Host::getProtocol).orElse(SwaggerConstants.DEFAULT_PROTOCOL));
    }

    private ApiInfo apiInfo() {
        License license = properties.getLicense();
        return new ApiInfo(
                properties.getAppName(),
                properties.getDescription(),
                version,
                SwaggerConstants.EMPTY_VALUE,
                properties.getContact(),
                Optional.ofNullable(license).map(License::getName).orElse(SwaggerConstants.EMPTY_VALUE),
                Optional.ofNullable(license).map(License::getUrl).orElse(SwaggerConstants.EMPTY_VALUE),
                Collections.emptyList());
    }

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(SwaggerConstants.AUTH_SCOPE, SwaggerConstants.AUTH_SCOPE_DESC);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes));
    }

}

