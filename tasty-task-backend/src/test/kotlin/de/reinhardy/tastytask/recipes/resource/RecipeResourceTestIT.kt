package de.reinhardy.tastytask.recipes.resource

import de.reinhardy.tastytask.AbstractIntegrationTestIT
import de.reinhardy.tastytask.recipes.model.createSampleRecipe
import de.reinhardy.tastytask.recipes.model.entity.Recipe
import de.reinhardy.tastytask.util.shouldBe
import io.quarkus.test.junit.QuarkusTest
import org.bson.types.ObjectId
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

import org.junit.jupiter.api.Test
import kotlin.collections.first


@QuarkusTest
class RecipeResourceTestIT: AbstractIntegrationTestIT() {

    @Test
    fun `getAllRecipes should return recipes`() {
        val recipe1 = createSampleRecipe()
        val recipe2 = createSampleRecipe()
        recipe1.persist()
        recipe2.persist()

        val result = getRequest("/recipes/").shouldSucceedAndReturn<List<Recipe>>()

        shouldMatchValues(result.first { it.id == recipe1.id }, recipe1.id!!)
        shouldMatchValues(result.first { it.id == recipe2.id }, recipe2.id!!)
    }

    @Test
    fun `getRecipeById should return a recipe`() {
        val recipe = createSampleRecipe()
        recipe.persist()

        val result = getRequest("/recipes/${recipe.id}").shouldSucceedAndReturn<Recipe>()

        shouldMatchValues(result, recipe.id!!)
    }

    @Test
    fun `deleteRecipeById should delete a recipe`() {
        val recipe = createSampleRecipe()
        recipe.persist()

        deleteRequest("/recipes/${recipe.id}").shouldSucceed()

        assertThat(Recipe.findAll().count(), `is`(0L))
    }

    @Test
    fun `createRecipe should create a recipe`() {
        val recipe = createSampleRecipe()

        val result = postRequest("/recipes/", recipe).shouldBeCreatedAndReturn<Recipe>()
        assert(Recipe.count() == 1L)
        shouldMatchValues(Recipe.findById(result.id!!)!!, result.id!!)
    }

    private fun shouldMatchValues(
        recipe: Recipe,
        id: ObjectId
    ) {
        with(recipe) {
            this.id shouldBe id
            title shouldBe "Blaubeeren"
            link shouldBe "http://blaubeeren.de"
            title shouldBe "Blaubeeren"
            with(recipeIngredients.single()) {
                name shouldBe "Blaubeeren"
                amount shouldBe "200"
                unit shouldBe "g"
            }
        }
    }
}

