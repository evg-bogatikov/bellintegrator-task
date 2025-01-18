package com.bgtkv.quoteservice.adapter.percistance.postgres.entity

import com.bgtkv.quoteservice.domain.entity.Quote
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "quote")
class QuoteEntity : BaseEntity() {

    @Column(name = "isin", nullable = false)
    var isin: String? = null

    @Column(name = "bid")
    var bid: BigDecimal? = null

    @Column(name = "ask", nullable = false)
    var ask: BigDecimal? = null
}

fun QuoteEntity.toDomain(): Quote = Quote(
    isin!!,
    bid,
    ask
)