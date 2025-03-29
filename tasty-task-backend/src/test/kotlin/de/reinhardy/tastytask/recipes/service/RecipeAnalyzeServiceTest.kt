package de.reinhardy.tastytask.recipes.service

import de.reinhardy.tastytask.AbstractIntegrationTestIT
import de.reinhardy.tastytask.util.shouldBe
import de.reinhardy.tastytask.util.shouldContain
import de.reinhardy.tastytask.util.shouldHaveMinSize
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

@QuarkusTest
class RecipeAnalyzeServiceTest : AbstractIntegrationTestIT() {

    @Inject
    private lateinit var sut: RecipeAnalyzeService

    @RepeatedTest(value = 100)
    fun `extractStructuredData should succeed`() {
        val recipeText = """450 g|Mehl|1 TL|Salz|3,5 EL|Olivenöl|250 ml|Wasser, lauwarmes|1 Pck.|Trockenhefe"""

        val result = sut.extractStructuredData(recipeText)

        result.lines() shouldHaveMinSize 6
        result.lines()[0] shouldContain listOf("name", "amount", "unit")
        result.lines()[1] shouldContain listOf("Mehl", "450", "g")
        result.lines()[2] shouldContain listOf("Salz", "1", "TL")
        result.lines()[3] shouldContain listOf("Olivenöl", "3.5", "EL")
        result.lines()[4] shouldContain listOf("Wasser", "250", "ml")
        result.lines()[5] shouldContain listOf("Trockenhefe", "1", "Pck")
    }
}

