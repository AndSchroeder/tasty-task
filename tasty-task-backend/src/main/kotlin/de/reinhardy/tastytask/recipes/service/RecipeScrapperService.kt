package de.reinhardy.tastytask.recipes.service

import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Playwright
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RecipeScrapperService {
    private val playwright = Playwright.create()
    private val browser = playwright.chromium().launch()

    private companion object{
        val INGREDIENT_SELECTORS = listOf(
            ".ingredients",
            ".recipe-ingredients__list",
            ".recipe-ingredients",
            ".ingredients-list",
            "[itemprop='recipeIngredient']",
            ".wprm-recipe-ingredient-group",
        )
    }

    fun scrapeRecipeFromUrl(url: String): String {
        val page = browser.newPage()
        page.navigate(url)

        var ingredientsContent = ""
        for (selector in INGREDIENT_SELECTORS) {
            val elements = page.querySelectorAll(selector).toList()
            if (elements.isNotEmpty()) {
                ingredientsContent = parseToString(elements)
                break
            }
        }

        if (ingredientsContent.isBlank()) {
            val recipeSection = page.querySelector("main") ?: page.querySelector("article")
            ?: page.querySelector("body")
            ingredientsContent = recipeSection?.textContent() ?: ""
        }

        page.close()
        return ingredientsContent
    }

    private fun parseToString(elements: List<ElementHandle>): String = elements.joinToString("") {
        it.textContent().trim()
    }


}