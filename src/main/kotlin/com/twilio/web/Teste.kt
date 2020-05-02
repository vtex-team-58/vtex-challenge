package com.twilio.web

import com.twilio.utils.Service

class Teste: Service() {

    @Service.Get
    fun teste(): String {
        return "Hello World!"
    }
}