package uz.gita.bank2.data.retrofit.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import uz.gita.bank2.data.request.profile.ProfileEditRequest
import uz.gita.bank2.data.response.ProfileEditResponse
import uz.gita.bank2.data.response.ProfileInfoResponse
import uz.gita.bank2.data.response.profile.AvatarResponse

interface ProfileApi {


    @Multipart
    @POST("/api/v1/profile/avatar")
    suspend fun setAvatar(@Part part: MultipartBody.Part): Response<AvatarResponse>

    @GET("/api/v1/profile/avatar")
    suspend fun getAvatar(): Response<ResponseBody>

    @PUT("/api/v1/profile")
    suspend fun editProfile(@Body data: ProfileEditRequest): Response<ProfileEditResponse>

    @GET("/api/v1/profile/info")
    suspend fun profileInfo(): Response<ProfileInfoResponse>

}