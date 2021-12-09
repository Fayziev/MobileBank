package uz.gita.bank2.domain.repository.impl

//class HistoryRepositoryImpl @Inject constructor(
//    private val pref: MyPref,
//    val api: MoneyTransferApi
//) : HistoryRepository {
//    private val config = PagingConfig(10)
//    override fun getHistoryPagingData(scope: CoroutineScope): Flow<PagingData<MoneyTransferResponse.HistoryData>> =
//        Pager(config) {
//            HistoryDataSource(api, pref)
//        }.flow.cachedIn(scope)
//
//}