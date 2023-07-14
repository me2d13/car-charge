package eu.me2d.carcharge.log

import eu.me2d.carcharge.dto.AppProps
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LoggingService(
    val appProps: AppProps
): InitializingBean {
    val logMessages: MutableList<LogMessage> = mutableListOf()

    fun log(message: String) {
        logMessages.add(LogMessage(message, LocalDateTime.now()))
    }

    override fun afterPropertiesSet() {
        log("Application started")
        log("maxLogEntries: ${appProps.maxLogEntries}")
    }

}

data class LogMessage(val message: String, val timestamp: LocalDateTime)