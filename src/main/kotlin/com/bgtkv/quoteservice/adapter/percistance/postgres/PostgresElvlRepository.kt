package com.bgtkv.quoteservice.adapter.percistance.postgres

import com.bgtkv.quoteservice.adapter.percistance.postgres.entity.ElvlEntity
import com.bgtkv.quoteservice.adapter.percistance.postgres.entity.toDomain
import com.bgtkv.quoteservice.adapter.percistance.postgres.repository.PostgresElvlEntityRepository
import com.bgtkv.quoteservice.domain.entity.Elvl
import com.bgtkv.quoteservice.domain.repository.ElvlRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostgresElvlRepository(
    private val postgresElvlEntityRepository: PostgresElvlEntityRepository
): ElvlRepository {

    override fun getElvlByIsin(isin: String): Elvl? {
        return postgresElvlEntityRepository.findByIsin(isin)?.toDomain()
    }

    override fun getElvlByIsinWithLock(isin: String): Elvl? {
        return postgresElvlEntityRepository.findByIsinWithLock(isin)?.toDomain()
    }

    @Transactional
    override fun saveElvl(elvl: Elvl) {
        val existingElvl = postgresElvlEntityRepository.findByIsin(elvl.isin)

        if (existingElvl != null) {
            existingElvl.value = elvl.value
            postgresElvlEntityRepository.save(existingElvl)
        } else {
            val newElvl = ElvlEntity()
                .apply {
                    isin = elvl.isin
                    value = elvl.value
                }

            postgresElvlEntityRepository.save(newElvl)
        }
    }

    override fun getAllElvls(): List<Elvl> {
        return postgresElvlEntityRepository.findAll()
            .map { it.toDomain() }
    }
}