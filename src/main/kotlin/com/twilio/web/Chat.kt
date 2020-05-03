package com.twilio.web

import com.twilio.utils.Service
import com.twilio.utils.TwilioAuth

class Chat: Service() {

    private val twilioAuth = TwilioAuth()

    @Post
    fun generateToken(identity: String, device: String): String {
        return twilioAuth.generateToken(identity, "${identity}_$device")
    }
}