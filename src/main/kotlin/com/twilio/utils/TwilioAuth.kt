package com.twilio.utils

import com.twilio.jwt.accesstoken.AccessToken
import com.twilio.jwt.accesstoken.ChatGrant
import com.twilio.web.chat.ChatServices


class TwilioAuth {

    fun generateToken(identity: String, endpointId: String): String {
        val grant = ChatGrant()
        grant.endpointId = endpointId
        grant.serviceSid = AppConfig.TWILIO_CHAT_SERVICE_SID
        println("teste")
        val token = AccessToken.Builder(
            AppConfig.TWILIO_ACCOUNT_SID,
            AppConfig.TWILIO_API_KEY,
            AppConfig.TWILIO_API_SECRET
        ).identity(identity).grant(grant).build()
        return token.toJwt()
    }
}