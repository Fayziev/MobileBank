package uz.gita.bank2.data.response

data class ProfileInfoResponse(
    val data: DataUser
)

data class DataUser(

    val firstName: String,
    val lastName: String,
    val password: String,
    val phone: String
)
