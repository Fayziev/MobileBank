package uz.gita.bank2.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.bank2.R
import uz.gita.bank2.data.request.auth.LoginRequest
import uz.gita.bank2.databinding.ScreenLoginBinding
import uz.gita.bank2.presentation.viewmodel.LoginViewModel
import uz.gita.bank2.presentation.viewmodel.impl.LoginViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast

@AndroidEntryPoint
class AuthLoginScreen : Fragment(R.layout.screen_login) {
    private val binding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openVerifyScreenFlow.onEach {
            findNavController().navigate(
                AuthLoginScreenDirections.actionLoginScreenToVerifyScreen
                    ("+998${binding.phoneEditText.rawText}", binding.inputPasswordEditText.text.toString())
            )
        }.launchIn(lifecycleScope)

    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        createAccountBtn.setOnClickListener {
            viewModel.openRegister()
        }
        backBtn.setOnClickListener {
            viewModel.backBtn()
        }
        loginBtn.setOnClickListener {
            viewModel.loginUser(
                LoginRequest("+998${binding.phoneEditText.rawText}", inputPasswordEditText.text.toString())
            )
            loginBtn.isEnabled = false
        }
        combine(
            phoneEditText.textChanges().map {
                it.length == 19
            },
            inputPasswordEditText.textChanges().map {
                it.length in 7..19
            },
            transform = { phone, password -> phone && password }
        ).onEach { loginBtn.isEnabled = it }
            .launchIn(lifecycleScope)

        viewModel.backBtnFlow.onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)

        viewModel.openRegisterFlow.onEach {
            findNavController().navigate(R.id.registerScreen)
        }.launchIn(lifecycleScope)

        viewModel.progressFlow.onEach {
            if (it) binding.progress.show()
            else binding.progress.hide()
        }.launchIn(lifecycleScope)

        viewModel.errorMessageFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)
    }

}