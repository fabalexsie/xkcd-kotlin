package de.siebes.fabian.xkcd.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import de.siebes.fabian.xkcd.helper.ComicLoader
import de.siebes.fabian.xkcd.helper.FavoriteManager
import de.siebes.fabian.xkcd.helper.MyDateFormatter
import de.siebes.fabian.xkcd.model.Comic
import kotlinx.coroutines.launch
import java.util.Random

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

    private val _isFavorite = MutableLiveData<Boolean>().apply {
        value = false
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
                if(_comic.value != null)
                    _isFavorite.value = FavoriteManager.isFavorite(_comic.value!!)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadRandomComic() {
        viewModelScope.launch {
            val rnd = Random()
            val highestComicNumber = ComicLoader.getHighestComicNumber()
            // generate a random number between 1 and highestComicNumber (both inclusive)
            val comicNumber =
                rnd.nextInt(highestComicNumber) + 1
            loadComic(comicNumber)
        }
    }

    val comicNumber: LiveData<Int?> = _comic.map { it?.num }

    val title: LiveData<String> = _comic.map { it?.title ?: "" }

    val dateStr: LiveData<String> = _comic.map {
        if (it == null) {
            ""
        } else {
            MyDateFormatter.format(it.date)
        }
    }

    val imgUrl: LiveData<String> = _comic.map { it?.imgUrl ?: "" }

    val altText: LiveData<String> = _comic.map { it?.alt ?: "" }

    val isFavorite: LiveData<Boolean> = _isFavorite

    fun toggleFavorite() {
        val comic = _comic.value ?: return
        if (FavoriteManager.isFavorite(comic)) {
            FavoriteManager.removeFavorite(comic)
            _isFavorite.value = false
        } else {
            FavoriteManager.addFavorite(comic)
            _isFavorite.value = true
        }
    }
}