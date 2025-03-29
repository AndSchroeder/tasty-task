package de.reinhardy.tastytask.recipes.service

import de.reinhardy.tastytask.AbstractIntegrationTestIT
import de.reinhardy.tastytask.recipes.model.createSampleRecipe
import de.reinhardy.tastytask.recipes.model.entity.Recipe
import de.reinhardy.tastytask.util.shouldBe
import de.reinhardy.tastytask.util.shouldContain
import de.reinhardy.tastytask.util.shouldContainInAnyOrder
import de.reinhardy.tastytask.util.shouldHaveMinSize
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@QuarkusTest
class RecipeIngredientServiceTest : AbstractIntegrationTestIT() {

    @Inject
    private lateinit var sut: RecipeIngredientService

    @Test
    fun `processRecipeUrl should succeed for chefkoch link`() {
        val recipe = createSampleRecipe(
            link = "https://www.chefkoch.de/rezepte/611671161092488/Pizzateig.html",
            title = "Pizzateig",
            recipeIngredients = mutableListOf()
        ).apply { persist() }

        sut.processRecipeUrl(recipe)

        val savedRecipe = Recipe.findById(recipe.id!!)

        with(savedRecipe!!) {
            title shouldBe recipe.title
            link shouldBe recipe.link
            recipeIngredients shouldHaveMinSize 5
            recipeIngredients.joinToString { it.name }.let { ingredients ->
                ingredients shouldContain "Mehl"
                ingredients shouldContain "Salz"
                ingredients shouldContain "Olivenöl"
                ingredients shouldContain "Wasser"
                ingredients shouldContain "Trockenhefe"
            }
        }
    }

    @Test
    fun `processRecipeUrl should succeed for essen und trinken link`() {
        val recipe = createSampleRecipe(
            link = "https://www.essen-und-trinken.de/rezepte/31939-rzpt-pizzateig",
            title = "Pizzateig",
            recipeIngredients = mutableListOf()
        ).apply { persist() }

        sut.processRecipeUrl(recipe)

        val savedRecipe = Recipe.findById(recipe.id!!)

        with(savedRecipe!!) {
            title shouldBe recipe.title
            link shouldBe recipe.link
            recipeIngredients shouldHaveMinSize 5
            recipeIngredients.joinToString { it.name }.let { ingredients ->
                ingredients shouldContain "Mehl"
                ingredients shouldContain "Salz"
                ingredients shouldContain "Olivenöl"
                ingredients shouldContain "Zucker"
                ingredients shouldContain "Hefe"
            }
        }
    }
}