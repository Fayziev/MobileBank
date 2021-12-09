package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.enums.BottomPageEnum

interface MainViewModel {

        val openSelectPosPageFlow: Flow<BottomPageEnum>
        val openNotificationsFlow: Flow<Unit>
        val manageDrawerFlow: Flow<Boolean>
        val openAddScreenFlow:Flow<Unit>

        fun openNotificationScreen()
        fun selectPagePos(page : BottomPageEnum)
        fun openDrawerLayout()
        fun closeDrawerLayout()
        fun openAddScreen()
}