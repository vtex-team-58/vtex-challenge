package com.twilio.web

import com.twilio.utils.Service
import com.twilio.utils.TwilioAuth
import com.twilio.web.chat.ChatServices
import com.twilio.web.chat.ChatVO

class Chat: Service() {

    private val twilioAuth = TwilioAuth()
    private val chatService = ChatServices()

    @Post @RequestIp
    fun generateToken(ip: String, identity: String, device: String): ChatVO.TokenChannel {
        val tokenChannel = ChatVO.TokenChannel()
        tokenChannel.token = twilioAuth.generateToken(identity, "${ip}_${identity}_$device")
        tokenChannel.channel = chatService.createNewChannel()
        chatService.putUserOnChannel(identity, tokenChannel.channel)
        return tokenChannel
    }

    @Post
    fun generateJustToken(ip: String, identity: String, device: String): ChatVO.TokenChannel {
        val tokenChannel = ChatVO.TokenChannel()
        tokenChannel.token = twilioAuth.generateToken(identity, "${ip}_${identity}_$device")
        return tokenChannel
    }

    @Post
    fun deleteChannel(sid: String) {
        chatService.deleteChannel(sid)
    }

    @Get
    fun getPendingChannels(): List<ChatVO.ChannelData> {
        return chatService.getPendingChannels()
    }
}