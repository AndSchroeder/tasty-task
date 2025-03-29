package de.reinhardy.tastytask.recipes.service

import de.reinhardy.tastytask.AbstractIntegrationTestIT
import de.reinhardy.tastytask.util.shouldBe
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@QuarkusTest
class RecipeScrapperServiceTestIT : AbstractIntegrationTestIT() {

    companion object {
        const val CHEFKOCH_SCRAPPING_RESULT = "450                                g                            \n                    \n                                                    Mehl \n                                                                    \n                \n                                            \n                                        \n                                1                                TL                            \n                    \n                                                    Salz \n                                                                    \n                \n                                            \n                                        \n                                3                                EL                            \n                    \n                                                    Olivenöl \n                                                                    \n                \n                                            \n                                        \n                                250                                ml                            \n                    \n                                                    Wasser, lauwarmes\n                                                                    \n                \n                                            \n                                        \n                                1                                Pck.                            \n                    \n                                                    Trockenhefe, 7 g"
        const val ESSEN_UND_TRINKEN_SCRAPPING_RESULT = "250  \ng\ng\nMehl\n ½  \nTl\nTl\nSalz\n ½  \nWürfel\nWürfel\nfrische Hefe\n(ca. 20 g)\n ½  \nTl\nTl\nZucker\n 4  \nEl\nEl\nOlivenöl"
    }

    @Inject
    private lateinit var sut: RecipeScrapperService

    @Test
    fun `scrapeRecipeFromUrl should get ingredients from chefkoch`() {
        val url = "https://www.chefkoch.de/rezepte/611671161092488/Pizzateig.html"

        val result = sut.scrapeRecipeFromUrl(url)

        result shouldBe CHEFKOCH_SCRAPPING_RESULT
    }

    @Test
    fun `scrapeRecipeFromUrl should get ingredients from essen und trinken`() {
        val url = "https://www.essen-und-trinken.de/rezepte/31939-rzpt-pizzateig"

        val result = sut.scrapeRecipeFromUrl(url)

        result shouldBe ESSEN_UND_TRINKEN_SCRAPPING_RESULT
    }
}