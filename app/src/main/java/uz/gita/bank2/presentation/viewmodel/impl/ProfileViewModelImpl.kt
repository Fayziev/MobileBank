package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import uz.gita.bank2.data.request.profile.ProfileEditRequest
import uz.gita.bank2.data.response.DataUser
import uz.gita.bank2.domain.usecase.ProfileUseCase
import uz.gita.bank2.presentation.viewmodel.ProfileViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val useCase: ProfileUseCase
) : ViewModel(), ProfileViewModel {

    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val getAvatarFlow = eventValueFlow<ResponseBody>()
    override val editProfileFlow = eventValueFlow<Unit>()
    override val getInfoFlow = eventValueFlow<DataUser>()
    override val setAvatarFlow = eventFlow()
    override fun setAvatar(file: File) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.setAvatar(file).onEach {
            it.onSuccess {
                viewModelScope.launch {
                    progressFlow.emit(false)
                    successFlow.emit("Success")
                    setAvatarFlow.emit(Unit)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorFlow.emit(throwable.message.toString())
                    progressFlow.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun getAvatar() {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.getAvatar().onEach {
            it.onSuccess { response ->
                viewModelScope.launch {
                    progressFlow.emit(false)
                    successFlow.emit("Success")
                    getAvatarFlow.emit(response)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorFlow.emit(throwable.message.toString())
                    progressFlow.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun editProfile(request: ProfileEditRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.editProfile(request).onEach {
            it.onSuccess {
                viewModelScope.launch {
                    progressFlow.emit(false)
                    successFlow.emit("Success")
                    editProfileFlow.emit(Unit)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorFlow.emit(throwable.message.toString())
                    progressFlow.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun getInfo() {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.getInfo().onEach {
            it.onSuccess { dataUser ->
                viewModelScope.launch {
                    progressFlow.emit(false)
                    successFlow.emit("Success")
                    getInfoFlow.emit(dataUser)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorFlow.emit(throwable.message.toString())
                    progressFlow.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }
}