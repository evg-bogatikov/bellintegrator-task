package com.bgtkv.quoteservice.app.service

import com.bgtkv.quoteservice.domain.entity.Elvl
import com.bgtkv.quoteservice.domain.entity.Quote
import com.bgtkv.quoteservice.domain.repository.ElvlRepository
import com.bgtkv.quoteservice.domain.repository.QuoteRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class QuoteServiceTest {

 @Mock
 private lateinit var quoteRepository: QuoteRepository

 @Mock
 private lateinit var elvlRepository: ElvlRepository

 @InjectMocks
 private lateinit var quoteService: DefaultQuoteService

 companion object{
  const val UNKNOWN_ISIN = "UNKNOWN_ISIN"
  const val INVALID_ISIN_BY_LENGTH = "INVALID_ISIN_LENGTH"
  const val ISIN_TEST_NAME_FIRST = "RU000A0JX0J2"
  const val ISIN_TEST_NAME_SECOND = "RU000A0JX1J3"
 }

 @Nested
 inner class ProcessQuoteUseCase {

  @Test
  fun `should new quote and save as new elvl`() {
   val quote = Quote(
    isin = ISIN_TEST_NAME_FIRST,
    bid = BigDecimal("100.2"),
    ask = BigDecimal("101.9")
   )
   whenever(elvlRepository.getElvlByIsin(quote.isin)).thenReturn(null)

   quoteService.processQuote(quote)

   val elvlCaptor = argumentCaptor<Elvl>()
   verify(quoteRepository).saveQuote(quote)
   verify(elvlRepository).saveElvl(elvlCaptor.capture())

   val savedElvl = elvlCaptor.firstValue
   assertEquals(ISIN_TEST_NAME_FIRST, savedElvl.isin)
   assertEquals(BigDecimal("100.2"), savedElvl.value)
  }

  @Test
  fun `should update elvl if bid is greater than current elvl`() {
   val quote = Quote(
    isin = ISIN_TEST_NAME_FIRST,
    bid = BigDecimal("101.5"),
    ask = BigDecimal("102.0")
   )
   whenever(elvlRepository.getElvlByIsin(quote.isin)).thenReturn(
    Elvl(ISIN_TEST_NAME_FIRST, BigDecimal("100.5"))
   )

   quoteService.processQuote(quote)

   val elvlCaptor = argumentCaptor<Elvl>()
   verify(elvlRepository).saveElvl(elvlCaptor.capture())

   val updatedElvl = elvlCaptor.firstValue
   assertEquals(ISIN_TEST_NAME_FIRST, updatedElvl.isin)
   assertEquals(BigDecimal("101.5"), updatedElvl.value)
  }

  @Test
  fun `should update elvl if ask is less than current elvl`() {
   val quote = Quote(
    isin = ISIN_TEST_NAME_FIRST,
    bid = null,
    ask = BigDecimal("99.0")
   )
   whenever(elvlRepository.getElvlByIsin(quote.isin)).thenReturn(
    Elvl(ISIN_TEST_NAME_FIRST, BigDecimal("100.5"))
   )

   quoteService.processQuote(quote)

   val elvlCaptor = argumentCaptor<Elvl>()
   verify(elvlRepository).saveElvl(elvlCaptor.capture())

   val updatedElvl = elvlCaptor.firstValue
   assertEquals(ISIN_TEST_NAME_FIRST, updatedElvl.isin)
   assertEquals(BigDecimal("99.0"), updatedElvl.value)
  }

  @Test
  fun `should validate ISIN length`() {
   val quote = Quote(
    isin = INVALID_ISIN_BY_LENGTH,
    bid = BigDecimal("100.0"),
    ask = BigDecimal("101.0")
   )

   val exception = assertThrows<IllegalArgumentException> {
    quoteService.processQuote(quote)
   }

   assertEquals("ISIN must have 12 characters", exception.message)
  }

  @Test
  fun `should validate bid less than ask`() {
   val quote = Quote(
    isin = ISIN_TEST_NAME_FIRST,
    bid = BigDecimal("102.0"),
    ask = BigDecimal("101.0")
   )

   val exception = assertThrows<IllegalArgumentException> {
    quoteService.processQuote(quote)
   }

   assertEquals("Bid must be less than ask", exception.message)
  }
 }

 @Nested
 inner class GetElvlByIsin {

  @Test
  fun `should return elvl by ISIN`() {
   val elvl = Elvl(ISIN_TEST_NAME_FIRST, BigDecimal("100.5"))
   whenever(elvlRepository.getElvlByIsin(ISIN_TEST_NAME_FIRST)).thenReturn(elvl)

   val result = quoteService.getElvlByIsin(ISIN_TEST_NAME_FIRST)

   assertEquals(elvl, result)
  }

  @Test
  fun `should return null if elvl is not found`() {
   whenever(elvlRepository.getElvlByIsin(UNKNOWN_ISIN)).thenReturn(null)

   val result = quoteService.getElvlByIsin(UNKNOWN_ISIN)

   assertEquals(null, result)
  }
 }

 @Nested
 inner class GetAllElvls {

  @Test
  fun `should return all elvls`() {
   val elvlList = listOf(
    Elvl(ISIN_TEST_NAME_FIRST, BigDecimal("100.5")),
    Elvl(ISIN_TEST_NAME_SECOND, BigDecimal("200.0"))
   )
   whenever(elvlRepository.getAllElvls()).thenReturn(elvlList)

   val result = quoteService.getAllElvls()

   assertEquals(elvlList, result)
  }
 }
}