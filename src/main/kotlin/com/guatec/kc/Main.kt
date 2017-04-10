package com.guatec.kc

import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.logging.CallLogging
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.locations.*
import org.jetbrains.ktor.routing.routing

fun Application.module() {

    @location("/")
    class Index

    @location("/contact")
    data class Contact(val name: String, val email: String, val message: String, val g_recaptcha_response: String)

    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)
    routing {
        get<Index> {
            call.respondText("Hello, world!")
        }
        get<Contact> {
            call.respondText("Your message $it has been sent.")
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, module = Application::module).start()
}