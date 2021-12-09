package uz.gita.bank2.data.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.bank2.data.request.auth.*
import uz.gita.bank2.data.response.auth.BasicResponse
import uz.gita.bank2.data.response.TokenResponse

interface AuthApi {

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<BasicResponse>

    @POST("/api/v1/auth/logout")
    suspend fun logout(): Response<BasicResponse>

    @POST("/api/v1/auth/register")
    suspend fun register(@Body data: RegisterRequest): Response<BasicResponse>

    @POST("/api/v1/auth/verify")
    suspend fun verify(@Body data: VerifyRequest): Response<TokenResponse>

    @POST("/api/v1/auth/resend")
    suspend fun resend(@Body request: ResendRequest): Response<BasicResponse>

    @POST("/api/v1/auth/reset")
    suspend fun reset(@Body request: ResetRequest): Response<BasicResponse>

    @POST("/api/v1/auth/newpassword")
    suspend fun newPassword(@Body request: NewPasswordRequest): Response<BasicResponse>
}