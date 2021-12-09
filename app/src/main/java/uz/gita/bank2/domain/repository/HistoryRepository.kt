package uz.gita.bank2.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.response.transfer.MoneyTransferResponse

interface HistoryRepository {

    fun getHistoryPagingData(scope: CoroutineScope): Flow<PagingData<MoneyTransferResponse.HistoryData>>
}