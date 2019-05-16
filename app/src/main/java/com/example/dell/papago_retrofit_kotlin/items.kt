package com.example.dell.papago_retrofit_kotlin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseItem(
        @SerializedName("message")
        @Expose
        var message: Message
)

data class Message(
        @SerializedName("result")
        @Expose
        var result: Result
)

data class Result(
        @SerializedName("translatedText")
        @Expose
        var translatedText: String
)

data class LangCode(
        @SerializedName("langCode")
        @Expose
        var langCode: String

)

