package com.jkojote.trex.util

fun <R: Any, T> R.mapTo(receiver: R.() -> T) : T {
  return receiver(this)
}