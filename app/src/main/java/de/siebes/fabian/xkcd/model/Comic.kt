package de.siebes.fabian.xkcd.model

import org.json.JSONObject
import java.util.Calendar
import java.util.Date

data class Comic(
    val num: Int,
    val date: Date,
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
            val cal = Calendar.getInstance()
            cal.set(
                Integer.parseInt(jsonObject.getString("year")),
                Integer.parseInt(jsonObject.getString("month")) -1,
                Integer.parseInt(jsonObject.getString("day"))
            )
            return Comic(
                jsonObject.getInt("num"),
                cal.time,
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