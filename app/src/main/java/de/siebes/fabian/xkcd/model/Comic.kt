package de.siebes.fabian.xkcd.model

import org.json.JSONObject

data class Comic(
    val num: Int,
    val day: String,
    val month: String,
    val year: String,
    val title: String,
    val safeTitle: String,
    val imgUrl: String,
    val alt: String,
    val link: String,
    val transcript: String,
    val news: String
) {

    companion object {
        fun fromJson(json: String): Comic {
            val jsonObject = JSONObject(json)
            return Comic(
                jsonObject.getInt("num"),
                jsonObject.getString("day"),
                jsonObject.getString("month"),
                jsonObject.getString("year"),
                jsonObject.getString("title"),
                jsonObject.getString("safe_title"),
                jsonObject.getString("img"),
                jsonObject.getString("alt"),
                jsonObject.getString("link"),
                jsonObject.getString("transcript"),
                jsonObject.getString("news")
            )
        }
    }
}