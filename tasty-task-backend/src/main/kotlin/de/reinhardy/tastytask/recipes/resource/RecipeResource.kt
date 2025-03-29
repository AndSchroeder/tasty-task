package com.example.resource

import de.reinhardy.tastytask.recipes.model.entity.Recipe
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import org.bson.types.ObjectId

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class RecipeResource {

    @GET
    fun getAllRecipes(): List<Recipe> {
        return Recipe.listAll()
    }

    @GET
    @Path("/{id}")
    fun getRecipeById(@PathParam("id") id: String): Response {
        val recipe = Recipe.findById(ObjectId(id))
        return if (recipe != null) {
            Response.ok(recipe).build()
        } else {
            Response.status(Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{id}")
    fun deleteRecipeById(@PathParam("id") id: String): Response {
        val deleted = Recipe.deleteById(ObjectId(id))
        return if (deleted ) {
            Response.ok().build()
        } else {
            Response.status(Status.NOT_FOUND).build()
        }
    }

    @POST
    fun createRecipe(recipe: Recipe): Response {
        recipe.persist()
        return Response.status(Status.CREATED)
            .entity(recipe)
            .build()
    }
}