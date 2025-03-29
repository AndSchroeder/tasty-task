package de.reinhardy.tastytask.recipes.service

import de.reinhardy.tastytask.ollama.adapter.OllamaAdapter
import de.reinhardy.tastytask.ollama.model.resource.OllamaRequest
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class RecipeAnalyzeService(
    @ConfigProperty(name = "de.reinhardy.tastytask.ollama.model")
    private val ollamaModel: String,
    private val ollamaAdapter: OllamaAdapter,
) {

    fun extractStructuredData(recipeText: String): String {
        val refinedRecipeText = recipeText.replaceCommasInNumbers().replaceRemainingCommas()

        val prompt = buildPrompt(refinedRecipeText)

        val ollamaResponse = ollamaAdapter.generate(
            OllamaRequest(
                model = ollamaModel,
                prompt = prompt,
                stream = false,
                options = mapOf(
                    "temperature" to 0.1,
                    "num_predict" to 2048
                )
            )
        )

        return ollamaResponse.response.trim()
    }

    private fun buildPrompt(recipeText: String): String {
        return """
                Delete your memory.
                You are a recipe analyzer. Extract the recipe ingredients from the following text.
                Output ONLY a CSV with these columns: name,amount,unit
                
                Follow these strict rules:
                1.  Use the german language only for the name and unit
                2.  Use the english language for amount and do not reformat numbers
                3.  First line must be the CSV header: name,amount,unit
                4.  Each ingredient on a new line
                5.  Include ONLY name, amount, and unit - nothing else
                6.  If amount or unit is unknown, leave it blank (e.g., "Zucker,,")
                7.  Clean up and standardize units (g, ml, Pck, etc.)
                8.  Use comma as separators
                9.  You must not add explanations
                
                Recipe text:
                $recipeText
                
                CSV Output:
            """.trimIndent()
    }

    fun String.replaceCommasInNumbers(): String = replace("""(\d+),(\d+)""".toRegex()) { matchResult ->
            val (before, after) = matchResult.destructured
            "$before.$after"
        }
    fun String.replaceRemainingCommas(): String = replace(",", "|")
}