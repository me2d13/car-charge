package eu.me2d.carcharge

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "api", description = "API for controlling the car charger via Home Assistant")
class ApiRestController {

    @GetMapping("/status")
    fun status(): String {
        return "OK"
    }
}