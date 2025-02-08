package de.siebes.fabian.xkcd.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import de.siebes.fabian.xkcd.helper.ComicLoader
import de.siebes.fabian.xkcd.model.Comic
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val _error = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val _comic = MutableLiveData<Comic>().apply {
        value = null
    }

    val loading: LiveData<Boolean> = _loading

    val error: LiveData<Boolean> = _error

    fun loadComic(comicNumber: Int? = null) {
        _loading.value = true
        _error.value = false
        _comic.value = null
        viewModelScope.launch {
            try {
                _comic.value = ComicLoader.loadComic(comicNumber)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    val comicNumber: LiveData<Int?> = _comic.map { it?.num }

    val title: LiveData<String> = _comic.map { it?.title ?: "" }

    val dateStr: LiveData<String> = _comic.map {
        if (it == null) {
            ""
        } else {
            "${it.day.padStart(2, '0')}.${
                it.month.padStart(
                    2,
                    '0'
                )
            }.${it.year}" // TODO use SimpleDateFormatter
        }
    }

    val imgUrl: LiveData<String> = _comic.map { it?.imgUrl ?: "" }

    val altText: LiveData<String> = _comic.map { it?.alt ?: "" }
}