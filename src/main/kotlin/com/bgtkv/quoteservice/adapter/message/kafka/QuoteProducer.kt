package com.bgtkv.quoteservice.adapter.message.kafka

import com.bgtkv.quoteservice.domain.entity.Quote
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.math.BigDecimal
import kotlin.random.Random
import kotlin.concurrent.thread

@Component
class QuoteProducer(private val kafkaTemplate: KafkaTemplate<String, Quote>): ApplicationRunner {

    /**
     * @param isinList - количество различных ISIN, которые будут сгенерированы.
     * @param messagesPerSecond - количество сообщений в секунду.
     * @param durationInSeconds - время теста в секундах
     * */
    fun startLoadTest(isinList: List<String>, messagesPerSecond: Int, durationInSeconds: Int) {
        val interval = 1000L / messagesPerSecond // Интервал между сообщениями в миллисекундах

        thread {
            val endTime = System.currentTimeMillis() + durationInSeconds * 1000
            while (System.currentTimeMillis() < endTime) {
                val isin = isinList.random()
                val bid = Random.nextDouble(90.0, 110.0)
                val ask = bid + Random.nextDouble(1.0, 5.0)
                val quote = Quote(isin, BigDecimal(bid), BigDecimal(ask))

                kafkaTemplate.send("quotes-topic", isin, quote)

                Thread.sleep(interval)
            }
            println("Load test completed")
        }
    }

    override fun run(args: ApplicationArguments?) {
        val isinCount = 10
        val isinList = List(isinCount) { "ISIN${it.toString().padStart(8, '0')}" }

        startLoadTest(isinList, 100, 60)
    }
}