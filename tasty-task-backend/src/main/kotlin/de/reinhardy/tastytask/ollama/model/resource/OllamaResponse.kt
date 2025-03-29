package de.reinhardy.tastytask.ollama.model.resource

data class OllamaResponse(
    val model: String,
    val response: String,
    val done: Boolean
)