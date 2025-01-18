package com.bgtkv.quoteservice.adapter.percistance.postgres.entity

import com.bgtkv.quoteservice.domain.entity.Elvl
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "elvl")
class ElvlEntity : BaseEntity(){

    @Column(name = "isin", nullable = false)
    var isin: String? = null

    @Column(name = "value", nullable = false)
    var value: BigDecimal? = null
}

fun ElvlEntity.toDomain(): Elvl = Elvl(
    isin!!,
    value!!
)