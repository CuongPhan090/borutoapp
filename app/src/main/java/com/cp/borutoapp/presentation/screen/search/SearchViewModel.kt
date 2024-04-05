package com.cp.borutoapp.presentation.screen.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {

    private var _searchQuery = mutableStateOf("")
    val searchQuery =  _searchQuery

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }
}