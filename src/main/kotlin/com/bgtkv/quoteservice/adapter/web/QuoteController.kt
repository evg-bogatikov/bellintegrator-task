package com.bgtkv.quoteservice.adapter.web

import com.bgtkv.quoteservice.adapter.web.request.QuoteRequest
import com.bgtkv.quoteservice.app.usecase.ProcessQuoteUseCase
import com.bgtkv.quoteservice.app.usecase.SelectionElvlUseCase
import com.bgtkv.quoteservice.domain.entity.Elvl
import com.bgtkv.quoteservice.domain.entity.Quote
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/quotes")
class QuoteController(
    private val processQuoteUseCase: ProcessQuoteUseCase,
    private val selectionElvlUseCase: SelectionElvlUseCase
) {

    @PostMapping("/process")
    fun processQuote(@RequestBody quoteRequest: QuoteRequest): ResponseEntity<Void> {
        val quote = Quote(
            quoteRequest.isin,
            quoteRequest.bid,
            quoteRequest.ask
        )

        processQuoteUseCase.processQuote(quote)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{isin}/elvl")
    fun getElvlByIsin(@PathVariable isin: String): ResponseEntity<Elvl> {
        return ResponseEntity.ok(selectionElvlUseCase.getElvlByIsin(isin))
    }

    @GetMapping("/elvls")
    fun getAllElvls(): ResponseEntity<List<Elvl>> {
        return ResponseEntity.ok(selectionElvlUseCase.getAllElvls())
    }
}