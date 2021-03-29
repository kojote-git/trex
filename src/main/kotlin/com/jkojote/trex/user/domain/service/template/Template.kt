package com.jkojote.trex.user.domain.service.template

interface Template<M> {

  fun render(model: M): String

}