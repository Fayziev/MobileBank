package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bank2.R
import uz.gita.bank2.presentation.viewmodel.PinLockViewModel
import uz.gita.bank2.presentation.viewmodel.impl.PinLockViewModelImpl

@AndroidEntryPoint
class AuthStartScreen : Fragment(R.layout.screen_splash) {
    private val viewModelPin: PinLockViewModel by viewModels<PinLockViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelPin.getPin()
        viewModelPin.pinStatusFlow.onEach {
            delay(2000)
            when (it) {
                0 -> {
                    findNavController().navigate(R.id.action_splashScreen_to_loginScreen)
                }
                1 -> {
                    findNavController().navigate(R.id.pinEnterScreen)
                }
                else -> {
                    findNavController().navigate(R.id.mainScreen)
                }
            }
        }.launchIn(lifecycleScope)
    }
}