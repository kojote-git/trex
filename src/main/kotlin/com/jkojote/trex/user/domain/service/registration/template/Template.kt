package com.jkojote.trex.user.domain.service.registration.template

interface Template<M> {

  fun render(model: M): String

}