package com.jkojote.trex

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TrexBackend

fun main(args: Array<String>) {
  runApplication<TrexBackend>(*args)
}