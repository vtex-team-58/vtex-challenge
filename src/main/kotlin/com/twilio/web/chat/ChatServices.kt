package com.twilio.web.chat

import com.google.gson.Gson
import com.twilio.utils.AppConfig.Companion.TWILIO_API_KEY
import com.twilio.utils.AppConfig.Companion.TWILIO_API_SECRET
import com.twilio.utils.AppConfig.Companion.TWILIO_CHAT_SERVICE_SID
import khttp.responses.Response
import khttp.structures.authorization.BasicAuthorization

class ChatServices {

    fun createNewChannel(): String {
        val res = khttp.post("""
            https://chat.twilio.com/v2/Services/${TWILIO_CHAT_SERVICE_SID}/Channels
        """.trimIndent(), auth = BasicAuthorization(TWILIO_API_KEY, TWILIO_API_SECRET))
        if(res.statusCode !in 200..299){
            throw Exception()
        }
        return res.jsonObject["sid"] as String
    }

    fun deleteChannel(sid: String): Response {
        val res = khttp.delete("""
            https://chat.twilio.com/v2/Services/${TWILIO_CHAT_SERVICE_SID}/Channels/$sid
        """.trimIndent(), auth = BasicAuthorization(TWILIO_API_KEY, TWILIO_API_SECRET))
        if(res.statusCode !in 200..299){
            throw Exception()
        }
        return res
    }

    fun getPendingChannels(): List<ChatVO.ChannelData> {
        val res = khttp.get("""
            https://chat.twilio.com/v2/Services/${TWILIO_CHAT_SERVICE_SID}/Channels
        """.trimIndent(), auth = BasicAuthorization(TWILIO_API_KEY, TWILIO_API_SECRET))
        if(res.statusCode !in 200..299){
            throw Exception()
        }
        val result = Gson().fromJson(res.jsonObject.toString(), ChatVO.PendingChannelsData::class.java)
        return result.channels.filter { it.members_count == 1 && it.friendly_name != "General Channel" }
    }

    fun putUserOnChannel(identity: String, channelSid: String){
        val res = khttp.post("""
            https://chat.twilio.com/v2/Services/${TWILIO_CHAT_SERVICE_SID}/Channels/$channelSid/Members
        """.trimIndent(), auth = BasicAuthorization(TWILIO_API_KEY, TWILIO_API_SECRET),
            data = mapOf("Identity" to identity),
            params = mapOf("Identity" to identity))
        if(res.statusCode !in 200..299){
            throw Exception()
        }
    }
}