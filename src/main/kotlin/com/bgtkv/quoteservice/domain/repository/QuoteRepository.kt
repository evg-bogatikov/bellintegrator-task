package com.bgtkv.quoteservice.domain.repository

import com.bgtkv.quoteservice.domain.entity.Quote

interface QuoteRepository {
    fun saveQuote(quote: Quote): Quote
}