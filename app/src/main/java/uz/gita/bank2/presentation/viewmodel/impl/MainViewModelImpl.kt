package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.bank2.enums.BottomPageEnum
import uz.gita.bank2.presentation.viewmodel.MainViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor() : ViewModel(), MainViewModel {

    override val openSelectPosPageFlow = eventValueFlow<BottomPageEnum>()
    override val openNotificationsFlow = eventValueFlow<Unit>()
    override val manageDrawerFlow = eventValueFlow<Boolean>()
    override val openAddScreenFlow = eventFlow()
    private var selectPos = 0
    override fun openNotificationScreen() {

    }

    override fun selectPagePos(page: BottomPageEnum) {

        if (selectPos != page.pos) {
            selectPos = page.pos
            viewModelScope.launch {
                openSelectPosPageFlow.emit(page)
            }
        }
    }

    override fun openDrawerLayout() {
        viewModelScope.launch {
            manageDrawerFlow.emit(true)
        }
    }

    override fun closeDrawerLayout() {
        viewModelScope.launch {
            manageDrawerFlow.emit(false)
        }
    }

    override fun openAddScreen() {
        viewModelScope.launch {
            openAddScreenFlow.emit(Unit)
        }
    }
}