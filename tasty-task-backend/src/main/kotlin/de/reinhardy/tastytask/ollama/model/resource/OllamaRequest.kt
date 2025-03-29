package de.reinhardy.tastytask.ollama.model.resource

data class OllamaRequest(
    val model: String,
    val prompt: String,
    val stream: Boolean = false,
    val options: Map<String, Any> = emptyMap()
)