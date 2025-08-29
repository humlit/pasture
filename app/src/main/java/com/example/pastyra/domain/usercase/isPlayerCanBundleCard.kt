import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card

// TODO:

fun isPlayerCanBundleCard(
    deck: List<Card>, cardForBundle: ArithmeticCard, cardToBundle: ArithmeticCard
): Boolean {
    return isPlayerCanGetValueFromDeck(
        deck + cardForBundle, cardToBundle.arithmeticValue - cardForBundle.arithmeticValue
    )
}
