package de.reinhardy.tastytask.recipes.service

import de.reinhardy.tastytask.AbstractIntegrationTestIT
import de.reinhardy.tastytask.recipes.service.RecipeCleanScrappedDataServiceTest.Companion.CHEFKOCH_CLEAN_RESULT
import de.reinhardy.tastytask.recipes.service.RecipeCleanScrappedDataServiceTest.Companion.ESSEN_UND_TRINKEN_CLEAN_RESULT
import de.reinhardy.tastytask.util.shouldBe
import de.reinhardy.tastytask.util.shouldContain
import de.reinhardy.tastytask.util.shouldHaveMinSize
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.RepeatedTest

@QuarkusTest
class RecipeAnalyzeServiceTestIT : AbstractIntegrationTestIT() {

    @Inject
    private lateinit var sut: RecipeAnalyzeService

    @RepeatedTest(value = 15)
    fun `extractStructuredData should succeed for normal recipe`() {

        val result = sut.extractStructuredData(CHEFKOCH_CLEAN_RESULT)

        result.lines() shouldHaveMinSize 6
        result.lines()[0] shouldContain listOf("name", "amount", "unit")
        result.lines().first { it.contains("Mehl") } shouldContain listOf("450", "g")
        result.lines().first { it.contains("Salz") } shouldContain listOf("1", "TL")
        result.lines().first { it.contains("Olivenöl") } shouldContain listOf("3", "EL")
        result.lines().first { it.contains("Wasser") } shouldContain listOf("250", "ml")
        result.lines().first { it.contains("Trockenhefe") } shouldContain listOf("1", "Pck")
    }
    @RepeatedTest(value = 15)
    fun `extractStructuredData should not well formated recipe`() {

        val result = sut.extractStructuredData(ESSEN_UND_TRINKEN_CLEAN_RESULT)

        result.lines() shouldHaveMinSize 6
        result.lines()[0] shouldContain listOf("name", "amount", "unit")
        result.lines().first { it.contains("Mehl") } shouldContain listOf( "250", "g")
        result.lines().first { it.contains("Salz") } shouldContain listOf( "0.5", "TL")
        result.lines().first { it.contains("frische Hefe") } shouldContain listOf( "20", "g")
        result.lines().first { it.contains("Olivenöl") } shouldContain listOf( "Olivenöl")
        result.lines().first { it.contains("Zucker") } shouldContain listOf( "Zucker")
    }
}

