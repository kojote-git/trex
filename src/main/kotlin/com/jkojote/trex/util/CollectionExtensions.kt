package com.jkojote.trex.util

import java.util.function.Predicate

fun <T> Collection<T>.has(predicate: Predicate<T>) : Boolean {
  return find { predicate.test(it) } != null
}