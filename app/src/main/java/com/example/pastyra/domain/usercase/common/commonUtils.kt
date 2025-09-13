import com.example.pastyra.domain.model.Card

fun isPlayerCanGetValueFromDeck(deck: List<Card>, findCardValue: Int): Boolean {
    if (findCardValue < 1) return false

    val filterDeckNums =
        deck.filterIsInstance<Card.AnyArithmeticCard>().map { card -> card.arithmeticValue }

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

fun isPlayerCanGetValueFromDeckForTakeCard(deck: List<Card>, findCardValue: Int): Boolean {
    return isPlayerCanGetValueFromDeck(
        deck.filter { card -> card is Card.AnyArithmeticCard.ArithmeticCard || (card is Card.AnyArithmeticCard.BundleCard && card.arithmeticValue == findCardValue) },
        findCardValue = findCardValue
    )
}
