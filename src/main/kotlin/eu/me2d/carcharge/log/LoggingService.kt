package eu.me2d.carcharge.log

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service

@Service
class LoggingService: InitializingBean {
    val logMessages: MutableList<String> = mutableListOf()

    fun log(message: String) {
        logMessages.add(message)
    }

    override fun afterPropertiesSet() {
        log("Application started")
    }

}