package com.bgtkv.quoteservice.adapter.message.kafka

import com.bgtkv.quoteservice.app.usecase.ProcessQuoteUseCase
import com.bgtkv.quoteservice.domain.entity.Quote
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class QuoteListener(
    private val processQuoteUseCase: ProcessQuoteUseCase
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @KafkaListener(topics = ["quotes-topic"], groupId = "quote-consumers", concurrency = "5")
    fun listen(quote: Quote) {
        log.info("Processing quote for ISIN: ${quote.isin}")
        processQuoteUseCase.processQuote(quote)
    }
}