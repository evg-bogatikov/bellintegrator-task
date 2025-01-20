package com.bgtkv.quoteservice.adapter.percistance.postgres.repository

import com.bgtkv.quoteservice.adapter.percistance.postgres.entity.ElvlEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostgresElvlEntityRepository : JpaRepository<ElvlEntity, Long> {
    fun findByIsin(isin: String): ElvlEntity?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM ElvlEntity e WHERE e.isin = :isin")
    fun findByIsinWithLock(isin: String): ElvlEntity?
}