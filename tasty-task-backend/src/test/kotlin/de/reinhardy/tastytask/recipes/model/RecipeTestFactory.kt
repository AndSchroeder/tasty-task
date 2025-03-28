package de.reinhardy.tastytask.recipes.model

import de.reinhardy.tastytask.recipes.model.entity.Recipe
import de.reinhardy.tastytask.recipes.model.entity.RecipeIngredient

fun createSampleRecipe(
    title: String = "Blaubeeren",
    link: String = "http://blaubeeren.de",
    recipeIngredients: MutableList<RecipeIngredient> = mutableListOf( createSampleRecipeIngredient()),
) = Recipe().apply {
    this.title = title
    this.link = link
    this.recipeIngredients = recipeIngredients
}

fun createSampleRecipeIngredient(
    name: String = "Blaubeeren",
    amount: String = "200",
    unit: String = "g",
) = RecipeIngredient().apply {
    this.name = name
    this.amount = amount
    this.unit = unit
}