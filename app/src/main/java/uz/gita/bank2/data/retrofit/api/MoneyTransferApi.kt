package uz.gita.bank2.data.retrofit.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.gita.bank2.data.request.transfer.MoneyTransferRequest
import uz.gita.bank2.data.request.transfer.TransferFeeRequest
import uz.gita.bank2.data.response.IncomesResponse
import uz.gita.bank2.data.response.OutcomesResponse
import uz.gita.bank2.data.response.transfer.MoneyTransferResponse
import uz.gita.bank2.data.response.transfer.SendMoneyResponse
import uz.gita.bank2.data.response.transfer.TransferFeeResponse

interface MoneyTransferApi {

    @POST("/api/v1/money-transfer/send-money")//
    suspend fun sendMoney(@Body data: MoneyTransferRequest): Response<SendMoneyResponse>

    @POST("/api/v1/money-transfer/fee")
    suspend fun transferFee(@Body data: TransferFeeRequest): Response<TransferFeeResponse>

    @GET("/api/v1/money-transfer/history")
    suspend fun history(
        @Query("page_number") pageNumber: Int,
        @Query("page_size") pageSize: Int
    ): Response<List<MoneyTransferResponse.HistoryData>>

    @GET("/api/v1/money-transfer/incomes")
    suspend fun incomes(): Response<IncomesResponse>

    @GET("/api/v1/money-transfer/outcomes")
    suspend fun outcomes(): Response<OutcomesResponse>
}