package de.reinhardy.tastytask.recipes.model.entity

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity

@MongoEntity(collection = "recipes")
class Recipe : PanacheMongoEntity() {
    lateinit var title: String
    lateinit var link: String

    var recipeIngredients: MutableList<RecipeIngredient> = mutableListOf()

    companion object: PanacheMongoCompanion<Recipe>
}
