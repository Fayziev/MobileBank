package uz.gita.bank2.data.request.auth

data class NewPasswordRequest(
    val phone:String,
    val newPassword:String,
    val code:String
)