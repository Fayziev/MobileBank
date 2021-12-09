package uz.gita.bank2.data.response.auth

import com.google.gson.annotations.SerializedName


data class VerifyResponse(
    @SerializedName("owner")
    val owner: String,
    @SerializedName("cardName")
    val cardName: String,
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("color")
    val color: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pan")
    val pan: String,
    @SerializedName("exp")
    val exp: String,
    @SerializedName("ignoreBalance")
    val ignoreBalance: Boolean,
    @SerializedName("status")
    val status: Int
)
