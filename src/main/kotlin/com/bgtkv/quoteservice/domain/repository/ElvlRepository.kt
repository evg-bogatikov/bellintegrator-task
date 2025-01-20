package com.bgtkv.quoteservice.domain.repository

import com.bgtkv.quoteservice.domain.entity.Elvl

interface ElvlRepository {
    fun getElvlByIsin(isin: String): Elvl?
    fun getElvlByIsinWithLock(isin: String): Elvl?
    fun saveElvl(elvl: Elvl)
    fun getAllElvls(): List<Elvl>
}