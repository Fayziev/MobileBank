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
import uz.gita.bank2.databinding.ScreenMainBinding
import uz.gita.bank2.enums.BottomPageEnum
import uz.gita.bank2.presentation.ui.adapter.MainPageAdapter
import uz.gita.bank2.presentation.viewmodel.MainViewModel
import uz.gita.bank2.presentation.viewmodel.impl.MainViewModelImpl
import uz.gita.bank2.utils.scope

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val binding by viewBinding(ScreenMainBinding::bind)
    private val adapter by lazy { MainPageAdapter(childFragmentManager, lifecycle) }
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openAddScreenFlow.onEach {
            findNavController().navigate(R.id.cardAddScreen)
        }.launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> viewModel.selectPagePos(BottomPageEnum.MAIN)
                R.id.transfer -> viewModel.selectPagePos(BottomPageEnum.TRANSFER)
                R.id.kirim -> viewModel.selectPagePos(BottomPageEnum.HISTORY)
                R.id.profile -> viewModel.selectPagePos(BottomPageEnum.PROFILE)
            }
            return@setOnItemSelectedListener true
        }
        viewModel.openSelectPosPageFlow.onEach {
            findNavController().navigate(it.pos)
        }.launchIn(lifecycleScope)
        add.setOnClickListener {
            viewModel.openAddScreen()
        }
    }
}