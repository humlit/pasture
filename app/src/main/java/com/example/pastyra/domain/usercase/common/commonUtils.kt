import android.util.Log
import com.example.pastyra.domain.model.AnyArithmeticCard
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.safeRank
import com.example.pastyra.domain.model.safeSuit
import com.example.pastyra.ui.viewmodel.GameViewModel

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

fun canPlayerBundle(viewModel: GameViewModel): List<Card> {
    val game = viewModel.state.value.game
    val activePlayerCard = viewModel.requiredActivePlayerCard

    val cardsForBundle = mutableListOf<Card>()

    viewModel.activePlayer?.cardsHand?.forEach { card ->
        if (card is ArithmeticCard && activePlayerCard is ArithmeticCard && isPlayerCanBundleCard(
                game.tableDeck, activePlayerCard, card
            )
        ) {
            cardsForBundle.add(card)
        }
    }
    Log.d("BUNDLE", cardsForBundle.joinToString("\n") { "${it.safeRank} ${it.safeSuit}" })
    return cardsForBundle
}