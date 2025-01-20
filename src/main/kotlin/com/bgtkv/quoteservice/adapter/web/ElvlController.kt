package com.bgtkv.quoteservice.adapter.web

import com.bgtkv.quoteservice.app.usecase.SelectionElvlUseCase
import com.bgtkv.quoteservice.domain.entity.Elvl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/elvl")
class ElvlController(
    private val selectionElvlUseCase: SelectionElvlUseCase
) {

    @GetMapping("/{isin}")
    fun getElvlByIsin(@PathVariable isin: String): ResponseEntity<Elvl> {
        return ResponseEntity.ok(selectionElvlUseCase.getElvlByIsin(isin))
    }

    @GetMapping("/list")
    fun getAllElvls(): ResponseEntity<List<Elvl>> {
        return ResponseEntity.ok(selectionElvlUseCase.getAllElvls())
    }
}