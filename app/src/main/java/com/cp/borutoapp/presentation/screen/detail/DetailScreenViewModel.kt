package com.cp.borutoapp.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cp.borutoapp.data.local.entities.toHero
import com.cp.borutoapp.data.repository.BorutoRepository
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.util.Constant.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val borutoRepository: BorutoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedHero = MutableStateFlow<Hero?>(null)
    val selectedHero = _selectedHero.asStateFlow()

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            _selectedHero.value = heroId?.let {
                borutoRepository.getSelectedHeroes(heroId = heroId).toHero()
            }
        }
    }
}
