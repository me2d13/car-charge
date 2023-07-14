package eu.me2d.carcharge.log

import eu.me2d.carcharge.dto.AppProps
import mu.KotlinLogging
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}
@Service
class LoggingService(
    val appProps: AppProps
): InitializingBean {
    val logMessages: MutableList<LogMessage> = mutableListOf()

    fun log(message: String) {
        logMessages.add(LogMessage(message, LocalDateTime.now()))
        logger.debug { message }
    }

    override fun afterPropertiesSet() {
        log("Application started")
        log("maxLogEntries: ${appProps.maxLogEntries}")
    }

    fun logForCar(carId: String, message: String) {
        log("Car $carId: $message")
    }

}

data class LogMessage(val message: String, val timestamp: LocalDateTime)