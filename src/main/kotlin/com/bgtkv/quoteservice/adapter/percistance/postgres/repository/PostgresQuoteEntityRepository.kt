package com.bgtkv.quoteservice.adapter.percistance.postgres.repository

import com.bgtkv.quoteservice.adapter.percistance.postgres.entity.QuoteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostgresQuoteEntityRepository : JpaRepository<QuoteEntity, Long>