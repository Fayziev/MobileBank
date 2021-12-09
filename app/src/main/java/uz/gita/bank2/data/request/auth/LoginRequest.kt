package uz.gita.bank2.data.request.auth

data class LoginRequest(
    val phone:String,
    val password:String
)