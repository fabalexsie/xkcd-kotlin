package de.siebes.fabian.xkcd.helper

import de.siebes.fabian.xkcd.model.Comic

object FavoriteManager {

    // TODO: Implement permanent storage for favorites
    private val favorites = mutableListOf<Comic>()

    fun addFavorite(comic: Comic) {
        favorites.add(comic)
    }

    fun removeFavorite(comic: Comic) {
        favorites.remove(comic)
    }

    fun isFavorite(comic: Comic): Boolean {
        favorites.forEach {
            if (it.num == comic.num) {
                return true
            }
        }
        return false
    }
}