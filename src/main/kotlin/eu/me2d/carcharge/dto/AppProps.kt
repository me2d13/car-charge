package eu.me2d.carcharge.dto

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class AppProps(
    val maxLogEntries: Int = 5000,
)
