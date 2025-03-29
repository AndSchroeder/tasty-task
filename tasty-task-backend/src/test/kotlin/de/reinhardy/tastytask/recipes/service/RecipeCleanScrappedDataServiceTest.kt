package de.reinhardy.tastytask.recipes.service

import de.reinhardy.tastytask.AbstractIntegrationTestIT
import de.reinhardy.tastytask.recipes.service.RecipeScrapperServiceTestIT.Companion.CHEFKOCH_SCRAPPING_RESULT
import de.reinhardy.tastytask.recipes.service.RecipeScrapperServiceTestIT.Companion.ESSEN_UND_TRINKEN_SCRAPPING_RESULT
import de.reinhardy.tastytask.util.shouldBe
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@QuarkusTest
class RecipeCleanScrappedDataServiceTest : AbstractIntegrationTestIT() {

    companion object {
        const val CHEFKOCH_CLEAN_RESULT = "450 g|Mehl|1 TL|Salz|3 EL|Olivenöl|250 ml|Wasser lauwarmes|1 Pck.|Trockenhefe 7 g"
        const val ESSEN_UND_TRINKEN_CLEAN_RESULT = "250|g|Mehl|0.5|Tl|Salz|0.5|Würfel|frische Hefe|(ca. 20 g)|0.5|Tl|Zucker|4|El|Olivenöl"
    }

    @Inject
    private lateinit var sut: RecipeCleanScrappedDataService

    @Test
    fun `cleanScrappedData should succeed with chefkoch scrapped data`() {

        val result = sut.cleanScrappedData(CHEFKOCH_SCRAPPING_RESULT)

        result shouldBe CHEFKOCH_CLEAN_RESULT


    }

    @Test
    fun `cleanScrappedData should succeed with essen und trinken scrapped data`() {

        val result = sut.cleanScrappedData(ESSEN_UND_TRINKEN_SCRAPPING_RESULT)

        result shouldBe ESSEN_UND_TRINKEN_CLEAN_RESULT


    }

}