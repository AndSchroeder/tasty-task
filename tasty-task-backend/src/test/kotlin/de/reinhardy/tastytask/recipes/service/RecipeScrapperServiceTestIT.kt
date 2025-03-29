package de.reinhardy.tastytask.recipes.service

import de.reinhardy.tastytask.AbstractIntegrationTestIT
import de.reinhardy.tastytask.util.shouldBe
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@QuarkusTest
class RecipeScrapperServiceTestIT : AbstractIntegrationTestIT() {

    @Inject
    private lateinit var sut: RecipeScrapperService

    @Test
    fun `scrapeRecipeFromUrl should get ingredients from chefkoch`() {
        val url = "https://www.chefkoch.de/rezepte/611671161092488/Pizzateig.html"

        val result = sut.scrapeRecipeFromUrl(url)

        result shouldBe """450 g|Mehl|1 TL|Salz|3 EL|Olivenöl|250 ml|Wasser, lauwarmes|1 Pck.|Trockenhefe, 7 g"""
    }

    @Test
    fun `scrapeRecipeFromUrl should get ingredients from essen und trinken`() {
        val url = "https://www.essen-und-trinken.de/rezepte/31939-rzpt-pizzateig"

        val result = sut.scrapeRecipeFromUrl(url)

        result shouldBe """Zutaten|Für|2|Portionen|250|g|g|Mehl|½|Tl|Tl|Salz|½|Würfel|Würfel|frische Hefe|(ca. 20 g)|½|Tl|Tl|Zucker|4|El|El|Olivenöl"""
    }
}