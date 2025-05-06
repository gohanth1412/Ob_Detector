package co.spirbase.framework.network

import com.google.gson.annotations.SerializedName

data class ApiObjectResponse<T>(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val dataResponse: T,

    @SerializedName("status")
    val status: Int
)