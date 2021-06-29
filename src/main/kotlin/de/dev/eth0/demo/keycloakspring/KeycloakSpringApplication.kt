package de.dev.eth0.demo.keycloakspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeycloakSpringApplication

fun main(args: Array<String>) {
  runApplication<KeycloakSpringApplication>(*args)
}
