package com.bgtkv.quoteservice.app.usecase

import com.bgtkv.quoteservice.domain.entity.Quote

interface ProcessQuoteUseCase {
    fun processQuote(quote: Quote)
}