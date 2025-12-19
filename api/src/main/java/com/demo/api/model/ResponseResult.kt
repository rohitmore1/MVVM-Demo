package com.demo.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseResult(
    @SerializedName(value = "response") val response: String? = null,
    @SerializedName(value = "message") val message: String? = null,

)