package com.bgtkv.quoteservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class QuoteServiceApplication

fun main(args: Array<String>) {
    runApplication<QuoteServiceApplication>(*args)
}
