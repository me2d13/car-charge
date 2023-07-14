package eu.me2d.carcharge

import eu.me2d.carcharge.dto.AppProps
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AppProps::class)
class AppConfiguration {
}