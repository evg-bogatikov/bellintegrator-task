package com.bgtkv.quoteservice.domain.entity

import java.math.BigDecimal

data class Quote(
    val isin: String,
    val bid: BigDecimal?,
    val ask: BigDecimal?
)
