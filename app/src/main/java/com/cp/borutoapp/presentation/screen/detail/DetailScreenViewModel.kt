package com.cp.borutoapp.presentation.screen.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cp.borutoapp.data.local.entities.toHero
import com.cp.borutoapp.data.repository.BorutoRepository
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.util.Constant.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    // Trigger one-time event, even after config changes
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            _selectedHero.value = heroId?.let {
                borutoRepository.getSelectedHeroes(heroId = heroId).toHero()
            }
        }
    }

    fun generateColorPalette() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GenerateColorPalette)
        }
    }

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }
}

sealed class UiEvent {
    data object GenerateColorPalette : UiEvent()
}
