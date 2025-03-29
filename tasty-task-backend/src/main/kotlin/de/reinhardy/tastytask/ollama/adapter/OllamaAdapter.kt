package de.reinhardy.tastytask.ollama.adapter

import com.google.gson.Gson
import de.reinhardy.tastytask.ollama.model.resource.OllamaRequest
import de.reinhardy.tastytask.ollama.model.resource.OllamaResponse
import jakarta.enterprise.context.ApplicationScoped
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.IOException

@ApplicationScoped
class OllamaAdapter(
    @ConfigProperty(name = "de.reinhardy.tastytask.ollama.url")
    private val ollamaBaseUrl: String
) {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun generate(request: OllamaRequest): OllamaResponse {
        val requestBody = gson.toJson(request)
        val httpRequest = Request.Builder()
            .url("$ollamaBaseUrl/api/generate")
            .post(requestBody.toRequestBody("application/json".toMediaType()))
            .build()

        val response = client.newCall(httpRequest).execute()
        val responseBody = response.body?.string() ?: throw IOException("Empty response")

        return gson.fromJson(responseBody, OllamaResponse::class.java)
    }
}