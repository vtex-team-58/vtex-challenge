package com.twilio.utils

class AppConfig {

    companion object {
        val TWILIO_ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID")
        val TWILIO_API_KEY = System.getenv("TWILIO_API_KEY")
        val TWILIO_CHAT_SERVICE_SID = System.getenv("TWILIO_CHAT_SERVICE_SID")
        val TWILIO_API_SECRET = System.getenv("TWILIO_API_SECRET")
    }
}