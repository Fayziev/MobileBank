package uz.gita.bank2.data.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.gita.bank2.data.request.card.AddCardRequest
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.data.request.card.VerifyCardRequest
import uz.gita.bank2.data.response.card.AllCardResponse
import uz.gita.bank2.data.response.auth.BasicResponse
import uz.gita.bank2.data.response.auth.VerifyResponse

interface AuthCardApi {
    @POST("/api/v1/card/add-card")
    suspend fun addCard(@Body request: AddCardRequest): Response<BasicResponse>

    @POST("/api/v1/card/verify")
    suspend fun verifyCard(@Body request: VerifyCardRequest): Response<VerifyResponse>

    @POST("/api/v1/card/edit-card")
    suspend fun editCard(@Body request: EditCardRequest): Response<BasicResponse>

    @POST("/api/v1/card/delete-card")
    suspend fun deleteCard(@Body request: DeleteCardRequest): Response<BasicResponse>

    @GET("/api/v1/card/all")
    suspend fun getAllCard(): Response<AllCardResponse>
}