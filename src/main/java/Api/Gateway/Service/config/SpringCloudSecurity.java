package Api.Gateway.Service.config;

import Api.Gateway.Service.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringCloudSecurity {

    @Autowired
    JwtAuthenticationFilter filter;
    @Value("${auth_service}")
    private String AUTH_SERVICE;
    @Value("${file_service}")
    private String FILE_SERVICE;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authenticationService", r -> (Buildable<Route>) r.path("/api/v1/**")
                        .uri(AUTH_SERVICE))
                .route("fileStorageService", r -> (Buildable<Route>) r.path("/api/**")
                        .filters(f -> f.filter(filter))
                        .uri(FILE_SERVICE))
                .build();

    }

}