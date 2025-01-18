package com.bgtkv.quoteservice.app.service

import com.bgtkv.quoteservice.app.usecase.ProcessQuoteUseCase
import com.bgtkv.quoteservice.app.usecase.SelectionElvlUseCase
import com.bgtkv.quoteservice.domain.entity.Elvl
import com.bgtkv.quoteservice.domain.entity.Quote
import com.bgtkv.quoteservice.domain.repository.ElvlRepository
import com.bgtkv.quoteservice.domain.repository.QuoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DefaultQuoteService(
    private val quoteRepository: QuoteRepository,
    private val elvlRepository: ElvlRepository
): ProcessQuoteUseCase, SelectionElvlUseCase {

    @Transactional
    override fun processQuote(quote: Quote) {
        require(quote.isin.length == 12) { "ISIN must have 12 characters" }
        require(quote.bid == null || quote.ask == null || quote.bid < quote.ask) { "Bid must be less than ask" }

        quoteRepository.saveQuote(quote)

        val currentElvl = elvlRepository.getElvlByIsin(quote.isin)?.value

        val newElvl = when {
            currentElvl == null -> quote.bid ?: quote.ask!!
            quote.bid != null && quote.bid > currentElvl -> quote.bid
            quote.ask != null && quote.ask < currentElvl -> quote.ask
            else -> currentElvl
        }

        elvlRepository.saveElvl(Elvl(quote.isin, newElvl))
    }

    override fun getElvlByIsin(isin: String): Elvl? = elvlRepository.getElvlByIsin(isin)

    override fun getAllElvls(): List<Elvl> = elvlRepository.getAllElvls()
}