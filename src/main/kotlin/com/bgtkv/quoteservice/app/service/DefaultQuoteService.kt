package com.bgtkv.quoteservice.app.service

import com.bgtkv.quoteservice.app.usecase.ProcessQuoteUseCase
import com.bgtkv.quoteservice.app.usecase.SelectionElvlUseCase
import com.bgtkv.quoteservice.domain.entity.Elvl
import com.bgtkv.quoteservice.domain.entity.Quote
import com.bgtkv.quoteservice.domain.repository.ElvlRepository
import com.bgtkv.quoteservice.domain.repository.QuoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class DefaultQuoteService(
    private val quoteRepository: QuoteRepository,
    private val elvlRepository: ElvlRepository
) : ProcessQuoteUseCase, SelectionElvlUseCase {

    @Transactional
    override fun processQuote(quote: Quote) {
        validateQuote(quote)

        quoteRepository.saveQuote(quote)

        val currentElvl = elvlRepository.getElvlByIsinWithLock(quote.isin)?.value
        val newElvl = calculateElvl(currentElvl, quote.bid, quote.ask)
        elvlRepository.saveElvl(Elvl(quote.isin, newElvl))
    }

    private fun validateQuote(quote: Quote) {
        require(quote.isin.length == 12) { "ISIN must have 12 characters" }
        require(quote.bid == null || quote.ask == null || quote.bid < quote.ask) { "Bid must be less than ask" }
    }

    private fun calculateElvl(currentElvl: BigDecimal?, bid: BigDecimal?, ask: BigDecimal?) = when {
        currentElvl == null -> bid ?: ask!!
        bid != null && bid > currentElvl -> bid
        ask != null && ask < currentElvl -> ask
        else -> currentElvl
    }

    override fun getElvlByIsin(isin: String): Elvl? = elvlRepository.getElvlByIsin(isin)

    override fun getAllElvls(): List<Elvl> = elvlRepository.getAllElvls()
}