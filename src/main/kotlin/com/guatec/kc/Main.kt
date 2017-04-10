package com.guatec.kc

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.runMain
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.logging.CallLogging
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.locations.*
import org.jetbrains.ktor.routing.routing
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@location("/")
class Index

@location("/contact")
data class Contact(val name: String,
                   val email: String,
                   val message: String,
                   val sendTo: String,
                   val `g-recaptcha-response`: String)

fun main(args: Array<String>) {
    CliArguments(ArgParser(args)).runMain("contactus") {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.google.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(ReCaptchaService::class.java)

        embeddedServer(Netty, port) {
            install(DefaultHeaders)
            install(CallLogging)
            install(Locations)
            routing {
                get<Index> {
                    call.respondText("Contact-us service is up")
                }
                get<Contact> { contact ->
                    val verification = service.verify(secret, contact.`g-recaptcha-response`).execute()
                    if (verification.isSuccessful) {

                        println("todo: send email")

                        call.respondText("Your message has been sent.")
                    }
                }
            }
        }.start()
    }
}