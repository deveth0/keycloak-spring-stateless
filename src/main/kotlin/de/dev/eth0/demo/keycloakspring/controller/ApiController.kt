package de.dev.eth0.demo.keycloakspring.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for the api
 */
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])

class ApiController {


  @GetMapping
  fun doSomething(): ResponseEntity<String> {
    val authentication = SecurityContextHolder.getContext().authentication
    return ResponseEntity.ok("Hello keycloak :) ${authentication.name}")
  }
}