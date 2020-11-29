package nl.soffware.madlevel6task2.model

import com.google.gson.GsonBuilder
import nl.soffware.madlevel6task2.deserializer.ConfigurationDeserializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConfigurationDeserializerTest {

    @Test
    fun `Deserializing the config response should work correctly`() {
        val json = """{
    "images": {
        "base_url": "http://image.tmdb.org/t/p/",
        "secure_base_url": "https://image.tmdb.org/t/p/",
        "backdrop_sizes": [
            "w300",
            "w780",
            "w1280",
            "original"
        ],
        "logo_sizes": [
            "w45",
            "w92",
            "w154",
            "w185",
            "w300",
            "w500",
            "original"
        ],
        "poster_sizes": [
            "w92",
            "w154",
            "w185",
            "w342",
            "w500",
            "w780",
            "original"
        ],
        "profile_sizes": [
            "w45",
            "w185",
            "h632",
            "original"
        ],
        "still_sizes": [
            "w92",
            "w185",
            "w300",
            "original"
        ]
    },
    "change_keys": [
        "video",
        "videos"
    ]
}"""
        val gson = GsonBuilder().registerTypeAdapter(Configuration::class.java,
            ConfigurationDeserializer()
        ).create()
        val config = gson.fromJson<Configuration>(json, Configuration::class.java)
        assertEquals("http://image.tmdb.org/t/p/", config.base_url)
        assertEquals("https://image.tmdb.org/t/p/", config.secure_base_url)
        assertEquals(listOf("w300", "w780", "w1280", "original"), config.backdrop_sizes)
        assertEquals(listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"), config.poster_sizes)
    }

}