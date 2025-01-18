package com.bgtkv.quoteservice.adapter.percistance.postgres.repository

import com.bgtkv.quoteservice.adapter.percistance.postgres.entity.ElvlEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostgresElvlEntityRepository : JpaRepository<ElvlEntity, Long> {
    fun findByIsin(isin: String): ElvlEntity?
}