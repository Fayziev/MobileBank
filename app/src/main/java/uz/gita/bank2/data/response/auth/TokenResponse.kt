package uz.gita.bank2.data.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    val data: TokenData
)

data class TokenData(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)