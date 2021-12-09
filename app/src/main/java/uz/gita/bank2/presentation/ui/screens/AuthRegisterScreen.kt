package uz.gita.bank2.presentation.ui.screens

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
import uz.gita.bank2.data.request.auth.RegisterRequest
import uz.gita.bank2.databinding.ScreenRegisterBinding
import uz.gita.bank2.presentation.viewmodel.RegisterViewModel
import uz.gita.bank2.presentation.viewmodel.impl.RegisterViewModelImpl
import uz.gita.bank2.utils.myLog
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast
import uz.gita.bank2.utils.timber

@AndroidEntryPoint
class AuthRegisterScreen : Fragment(R.layout.screen_register) {

    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels<RegisterViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.openVerifyScreenFlow.onEach {
            findNavController().navigate(
                AuthRegisterScreenDirections.actionRegisterScreenToVerifyScreen(
                    "+998${binding.phoneEditText.rawText}",
                    binding.inputPasswordEditText.text.toString()
                )
            )
        }.launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        combine(
            inputFirstEditText.textChanges().map {
                it.length > 4
            },
            inputLastEditText.textChanges().map {
                it.length > 4
            },
            phoneEditText.textChanges().map {
                myLog("Phone=${it}", "RRR")
                it.length == 19
            },
            inputPasswordEditText.textChanges().map {
                it.length in 7..19
            },
            inputConfirmEditText.textChanges().map {
                it.length in 7..19 && inputPasswordEditText.text.toString() == it.toString()
            },
            transform = { firstName, lastName, phone, password, confirmPassword ->
                firstName && lastName && phone
                        && password && confirmPassword
                        && confirmPassword == password
            }
        ).onEach { loginBtn.isEnabled = it }
            .launchIn(lifecycleScope)

        backBtn.setOnClickListener {
            viewModel.backBtn()
        }

        loginBtn.setOnClickListener {
            viewModel.registerUser(
                RegisterRequest(
                    inputFirstEditText.text.toString(),
                    inputLastEditText.text.toString(),
                    "+998${binding.phoneEditText.rawText}",
                    inputPasswordEditText.text.toString()
                )
            )
        }
        viewModel.errorMessageFlow.onEach {
            binding.inputConfirmEditText.setText("")
            showToast(it)
            timber(it)
        }.launchIn(lifecycleScope)
        viewModel.progressFlow.onEach {
            if (it) binding.progress.show()
            else binding.progress.hide()
        }.launchIn(lifecycleScope)
        viewModel.backBtnFlow.onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)
    }
}