import android.util.Log
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.safeRank
import com.example.pastyra.domain.model.safeSuit

/**
 * Проверяет, может ли игрок сделать завязку с выбранной картой в руке для конкретной карты, учитывая столл.
 * @param cardThatBundles - Карта, которая завязывает.
 * @param cardToBeBundled - Карта, которую хотим завязать.
 */
fun isPlayerCanBundleCard(
    deck: List<Card>, cardThatBundles: ArithmeticCard, cardToBeBundled: ArithmeticCard
): Boolean {
    return isPlayerCanGetValueFromDeck(
        deck + cardThatBundles, cardToBeBundled.arithmeticValue - cardThatBundles.arithmeticValue
    )
}

/**
 * Возвращает список карт в руке игрока, которые можно завязать с выбранной активной картой игрока.
 */
fun getPlayerBundlingCards(
    playerHand: List<Card>,
    tableDeck: List<Card>,
    activePlayerCard: Card?
): List<Card> {
    val cardsToBeBundled = mutableListOf<Card>()

    val filteredPlayerHand = playerHand.filter { it.id != activePlayerCard?.id }

    filteredPlayerHand.forEach { card ->
        if (card is ArithmeticCard && activePlayerCard is ArithmeticCard && isPlayerCanBundleCard(
                tableDeck, activePlayerCard, card
            )
        ) {
            cardsToBeBundled.add(card)
        }
    }
    Log.d("BUNDLE", cardsToBeBundled.joinToString("\n") { "${it.safeRank} ${it.safeSuit}" })
    return cardsToBeBundled
}