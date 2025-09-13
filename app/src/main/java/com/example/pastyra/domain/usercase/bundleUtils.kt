import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.BundleCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.Player

/**
 * Проверяет, может ли игрок сделать завязку с выбранной картой в руке для конкретной карты, учитывая столл.
 * @param cardThatBundles - Карта, которая завязывает.
 * @param cardToBeBundled - Карта, которую хотим завязать.
 */
fun isPlayerCanBundleCard(
    deck: List<Card>,
    tableActiveCards: List<Card>,
    cardThatBundles: ArithmeticCard,
    cardToBeBundled: ArithmeticCard
): Boolean {
    if (cardThatBundles.arithmeticValue == cardToBeBundled.arithmeticValue) {
        return isPlayerCanGetValueFromDeck(
            deck, tableActiveCards, cardToBeBundled.arithmeticValue
        )
    }

    val filterCardToBundle = deck.filter {
        if (it is BundleCard) {
            it.bundleStage == 1
        } else {
            true
        }
    }

    return isPlayerCanGetValueFromDeck(
        filterCardToBundle,
        tableActiveCards,
        cardToBeBundled.arithmeticValue - cardThatBundles.arithmeticValue
    )
}

/**
 * Возвращает список карт в руке игрока, которые можно завязать с выбранной активной картой игрока.
 */
fun getPlayerBundlingCards(
    player: Player,
    tableDeck: List<Card>,
    tableActiveCards: List<Card>,
): List<Card> {
    if (player.activeCard == null) return listOf()

    val playerActiveCard = player.activeCard

    val cardsToBeBundled = mutableListOf<Card>()

    val filteredPlayerHand = player.cardsHand.filter { it.id != playerActiveCard.id }

    filteredPlayerHand.forEach { card ->
        if (card is ArithmeticCard && playerActiveCard is ArithmeticCard && isPlayerCanBundleCard(
                tableDeck, tableActiveCards, playerActiveCard, card
            )
        ) {
            cardsToBeBundled.add(card)
        }
    }

    return cardsToBeBundled
}

fun canPlayerFinishBundlingCards(
    cardToBeBundled: ArithmeticCard,
    tableDeck: List<Card>,
    tableActiveCards: List<Card>,
): Boolean {

    return !isPlayerCanGetValueFromDeck(
        tableDeck = tableDeck,
        tableActiveCards = tableActiveCards,
        findCardValue = cardToBeBundled.arithmeticValue
    )
}