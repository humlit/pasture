import com.example.pastyra.domain.model.AnyArithmeticCard
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.BundleCard
import com.example.pastyra.domain.model.Card

fun isPlayerCanGetValueFromDeck(
    tableDeck: List<Card>,
    tableActiveCards: List<Card>,
    findCardValue: Int
): Boolean {
    val inspectedListOfCards = tableDeck + tableActiveCards

    if (findCardValue < 1) return false

    val filterDeckNums =
        inspectedListOfCards.filterIsInstance<AnyArithmeticCard>().map { card -> card.arithmeticValue }

    val dp = BooleanArray(findCardValue + 1) { false }
    dp[0] = true

    for (num in filterDeckNums) {
        for (j in findCardValue downTo num) {
            if (dp[j - num]) {
                dp[j] = true
            }
        }
    }

    return dp[findCardValue]
}

fun isPlayerCanGetValueFromDeckForTakeCard(tableDeck: List<Card>, tableActiveCards: List<Card>, findCardValue: Int): Boolean {
    val filteredTableDeck = tableDeck.filter { card -> card is ArithmeticCard || (card is BundleCard && card.arithmeticValue == findCardValue) }
    val filteredTableActiveCards = tableActiveCards.filter { card -> card is ArithmeticCard || (card is BundleCard && card.arithmeticValue == findCardValue) }

    return isPlayerCanGetValueFromDeck(
        tableDeck = filteredTableDeck,
        tableActiveCards = filteredTableActiveCards,
        findCardValue = findCardValue
    )
}
