package com.twilio.web.chat

import com.google.gson.JsonObject

class ChatVO {

    data class TokenChannel(
            var token: String = "",
            var channel: String = ""
    )

    data class PendingChannelsData(
        var channels: MutableList<ChannelData> = mutableListOf()
    )

    data class ChannelData(
        var friendly_name: String? = null,
        var date_updated: String = "",
        var date_created: String = "",
        var type: String = "",
        var created_by: String = "",
        var url: String = "",
        var sid: String = "",
        var service_sid: String = "",
        var messages_count: Int = 0,
        var unique_name: String? = null,
        var account_sid: String = "",
        var members_count: Int = 0
    )

}