package de.reinhardy.tastytask.recipes.service

import de.reinhardy.tastytask.recipes.model.entity.Recipe
import de.reinhardy.tastytask.recipes.model.entity.RecipeIngredient
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class RecipeIngredientService(
    private val recipeScrapperService: RecipeScrapperService,
    private val recipeAnalyzeService: RecipeAnalyzeService,
) {

    fun processRecipeUrl(recipe: Recipe) {
        val recipeText = recipeScrapperService.scrapeRecipeFromUrl(recipe.link)
        val csvData = recipeAnalyzeService.extractStructuredData(recipeText)
        val ingredients = parseCsvIntoIngredients(csvData)
        recipe.recipeIngredients = ingredients.toMutableList()
        recipe.update()
    }

    private fun parseCsvIntoIngredients(csvData: String): List<RecipeIngredient> {
        val lines = csvData.trim().lines()
        if (lines.isEmpty() || !lines[0].startsWith("name")) {
            return emptyList()
        }

        return lines.drop(1).map { line ->
            val parts = line.split(",", limit = 3)
            RecipeIngredient().apply {
                name = parts[0].trim()
                amount = if (parts.size > 1) parts[1].trim() else ""
                unit = if (parts.size > 2) parts[2].trim() else ""
            }
        }
    }
}