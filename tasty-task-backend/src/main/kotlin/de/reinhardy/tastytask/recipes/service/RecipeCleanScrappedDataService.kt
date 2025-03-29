package de.reinhardy.tastytask.recipes.service

import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class RecipeCleanScrappedDataService {

    fun cleanScrappedData(recipeText: String) =
        recipeText.reduceWhitespaces()
            .cleanRepeatedData()
            .replaceCommasInNumbers()
            .replaceRemainingCommas()
            .replaceFractions()


    private fun String.reduceWhitespaces() = lines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }.joinToString("|") { line ->
            line.replace(Regex("\\s+"), " ")
        }

    private fun String.replaceCommasInNumbers(): String = replace("""(\d+),(\d+)""".toRegex()) { matchResult ->
        val (before, after) = matchResult.destructured
        "$before.$after"
    }

    private fun String.replaceRemainingCommas(): String = replace(",", "")

    private fun String.cleanRepeatedData(): String = replace("""([\p{L}\p{N}_]+)\|\1\|""".toRegex(), "$1|")

    private fun String.replaceFractions() =
        replace("½", "0.5")
            .replace("⅓", "0.33")
            .replace("⅔", "0.67")
            .replace("¼", "0.25")
            .replace("¾", "0.75")
            .replace("⅕", "0.2")
            .replace("⅖", "0.4")
            .replace("⅗", "0.6")
            .replace("⅘", "0.8")
            .replace("⅙", "0.17")
            .replace("⅚", "0.83")
            .replace("⅛", "0.125")
            .replace("⅜", "0.375")
            .replace("⅝", "0.625")
            .replace("⅞", "0.875")
}