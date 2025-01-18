package com.bgtkv.quoteservice.adapter.web.request

import java.math.BigDecimal

data class QuoteRequest (
    val isin: String,
    val bid: BigDecimal?,
    val ask: BigDecimal?
)