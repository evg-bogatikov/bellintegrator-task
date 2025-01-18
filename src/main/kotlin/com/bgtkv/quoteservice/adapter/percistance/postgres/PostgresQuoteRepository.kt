package com.bgtkv.quoteservice.adapter.percistance.postgres

import com.bgtkv.quoteservice.adapter.percistance.postgres.entity.QuoteEntity
import com.bgtkv.quoteservice.adapter.percistance.postgres.entity.toDomain
import com.bgtkv.quoteservice.adapter.percistance.postgres.repository.PostgresQuoteEntityRepository
import com.bgtkv.quoteservice.domain.entity.Quote
import com.bgtkv.quoteservice.domain.repository.QuoteRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostgresQuoteRepository(
    private val postgresQuoteEntityRepository: PostgresQuoteEntityRepository
): QuoteRepository {

    @Transactional
    override fun saveQuote(quote: Quote): Quote {
        val quoteEntity = QuoteEntity()
            .apply {
                isin = quote.isin
                bid = quote.bid
                ask = quote.ask
            }

        return postgresQuoteEntityRepository.save(quoteEntity)
            .toDomain()
    }
}