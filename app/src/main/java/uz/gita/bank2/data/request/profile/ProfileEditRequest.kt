package uz.gita.bank2.data.request.profile

import com.google.gson.annotations.SerializedName

data class ProfileEditRequest(

    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("password")
    val password: String
)
