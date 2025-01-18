package com.bgtkv.quoteservice.app.usecase

import com.bgtkv.quoteservice.domain.entity.Elvl

interface SelectionElvlUseCase {
    fun getElvlByIsin(isin: String): Elvl?
    fun getAllElvls(): List<Elvl>
}