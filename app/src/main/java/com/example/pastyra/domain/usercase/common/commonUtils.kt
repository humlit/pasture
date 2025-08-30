import com.example.pastyra.domain.model.AnyArithmeticCard
import com.example.pastyra.domain.model.Card

fun isPlayerCanGetValueFromDeck(deck: List<Card>, findCardValue: Int): Boolean {
    if (findCardValue < 1) return false

    val filterDeckNums = deck.filterIsInstance<AnyArithmeticCard>().map {
        it.arithmeticValue
    }

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
