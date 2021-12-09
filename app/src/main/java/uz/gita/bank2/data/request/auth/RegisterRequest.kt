package uz.gita.bank2.data.request.auth

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String,
    val status: Int = 0
)