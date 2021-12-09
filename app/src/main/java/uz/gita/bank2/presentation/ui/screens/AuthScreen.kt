package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bank2.R
import uz.gita.bank2.databinding.ScreenAuthBinding
import uz.gita.bank2.presentation.viewmodel.AuthViewModel
import uz.gita.bank2.presentation.viewmodel.impl.AuthViewModelImpl
import uz.gita.bank2.utils.scope
@AndroidEntryPoint

class AuthScreen : Fragment(R.layout.screen_auth) {
    private val binding by viewBinding(ScreenAuthBinding::bind)
    private val viewModel: AuthViewModel by viewModels<AuthViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.openLoginFlow.onEach {
            findNavController().navigate(R.id.action_authScreen_to_loginScreen)
        }.launchIn(lifecycleScope)

        viewModel.openRegisterFlow.onEach {
            findNavController().navigate(R.id.action_authScreen_to_registerScreen)
        }.launchIn(lifecycleScope)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {

        login.setOnClickListener {
            viewModel.openLogin()
        }
        register.setOnClickListener {
            viewModel.openRegister()
        }
    }
}