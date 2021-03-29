package com.jkojote.trex.template

interface Template<M> {

  fun render(model: M): String

}