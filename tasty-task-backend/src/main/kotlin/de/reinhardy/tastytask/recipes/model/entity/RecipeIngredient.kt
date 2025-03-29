package de.reinhardy.tastytask.recipes.model.entity

import io.quarkus.mongodb.panache.common.MongoEntity

@MongoEntity(collection = "ingredients")
class RecipeIngredient {
    lateinit var name: String
    var amount: String? = null
    var unit: String? = null
}