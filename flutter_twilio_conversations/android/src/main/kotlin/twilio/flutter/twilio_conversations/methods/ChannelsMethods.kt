package twilio.flutter.twilio_conversations.methods

import com.twilio.conversations.CallbackListener
import com.twilio.conversations.Conversation
import com.twilio.util.ErrorInfo
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import twilio.flutter.twilio_conversations.Mapper
import twilio.flutter.twilio_conversations.TwilioConversationsPlugin
import android.util.Log

object ChannelsMethods {
    fun getChannel(pluginInstance: TwilioConversationsPlugin, call: MethodCall, result: MethodChannel.Result) {
        val channelSidOrUniqueName = call.argument<String>("channelSidOrUniqueName")
                ?: return result.error("ERROR", "Missing 'channelSidOrUniqueName'", null)

        TwilioConversationsPlugin.chatClient?.getConversation(channelSidOrUniqueName, object : CallbackListener<Conversation> {
            override fun onSuccess(newChannel: Conversation) {
                Log.d("TwilioInfo", "ChannelsMethods.getChannel => onSuccess")
                result.success(Mapper.channelToMap(pluginInstance, newChannel))
            }

            override fun onError(errorInfo: ErrorInfo) {
                Log.d("TwilioInfo", "ChannelsMethods.getChannel => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getPublicChannelsList(call: MethodCall, result: MethodChannel.Result) {
        result.success(TwilioConversationsPlugin.chatClient?.myConversations)
    }

    fun getUserChannelsList(call: MethodCall, result: MethodChannel.Result) {
        result.success(TwilioConversationsPlugin.chatClient?.myConversations)
    }

    fun createChannel(pluginInstance: TwilioConversationsPlugin, call: MethodCall, result: MethodChannel.Result) {
        val friendlyName = call.argument<String>("friendlyName")
            ?: return result.error("ERROR", "Missing 'friendlyName'", null)
        TwilioConversationsPlugin.chatClient?.conversationBuilder()?.let { builder ->
            builder.withUniqueName(friendlyName)
            .build(object : CallbackListener<Conversation> {
                override fun onSuccess(newChannel: Conversation) {
                    Log.d("TwilioInfo", "ChannelsMethods.createChannel => onSuccess")
                    result.success(Mapper.channelToMap(pluginInstance, newChannel))
                }

                override fun onError(errorInfo: ErrorInfo) {
                    Log.d("TwilioInfo", "ChannelsMethods.createChannel => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        }
    }
}
