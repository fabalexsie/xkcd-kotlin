package de.siebes.fabian.xkcd.helper

import de.siebes.fabian.xkcd.model.Comic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

// ComicLoader as Singleton
object ComicLoader {

    private const val BASE_URL = "https://xkcd.com"

    private val okHttpClient = OkHttpClient()

    // Run on IO dispatcher, to avoid network requests on main thread
    @Throws(ComicNotFoundException::class)
    suspend fun loadComic(comicNumber: Int? = null): Comic = withContext(Dispatchers.IO) {
        val url: String
        if (comicNumber == null) {
            url = "$BASE_URL/info.0.json";
        } else {
            url = "$BASE_URL/$comicNumber/info.0.json";
        }

        val request = Request.Builder()
            .url(url)
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw ComicNotFoundException(
                comicNumber,
                "Unexpected code $response"
            )
            val body =
                response.body ?: throw ComicNotFoundException(comicNumber, "Response body is null")
            return@withContext Comic.fromJson(body.string())
        }
    }
}