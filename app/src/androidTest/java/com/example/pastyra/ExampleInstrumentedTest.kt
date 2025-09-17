package com.example.pastyra

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pastyra.domain.fixtures.DECK_PLAYER_CAN_MAKE_PASTYRE
import com.example.pastyra.domain.model.Game
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.ui.components.PastyraGameStart
import com.example.pastyra.ui.viewmodel.GameViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // Фикстура пусть будет в самом тесте

    @Test
    fun player_must_make_pastyre_with_ten() {
        class FakeVM : GameViewModel(GameState(game = Game(gameDeck = DECK_PLAYER_CAN_MAKE_PASTYRE)))

        val fakeVm = FakeVM()

        composeTestRule.setContent {
            PastyraGameStart(fakeVm)
        }

        val nodes = composeTestRule.onAllNodesWithTag("CardView")

        val playerCards = listOf(nodes[0], nodes[1], nodes[2], nodes[3])
        val tableCards = listOf(nodes[4], nodes[5], nodes[6], nodes[7])

        // Нажал на 10
        playerCards[3].performClick()

        val takeBtn = composeTestRule.onNodeWithText("Взять")

        // Нажал на "Взять"
        takeBtn.performClick()

        // После нажатия, карта со стола уходит, так что снова нажимаем на 1 карту
        repeat(4) {
            tableCards[0].performClick()
        }

        val finishBtn = composeTestRule.onNodeWithText("Завершить ход")

        // Нажал на "Завершить ход"
        finishBtn.performClick()

        val player = fakeVm.state.value.game.playerList[0]

        Assert.assertEquals(
            "Тест со взяткой прошел успешно",
            1,
            player.pastyrs,
        )
    }

//    @Test
//    fun player_must_not_make_pastyre_with_ten() {
//        class FakeVM :
//            GameViewModel(
//                GameState(
//                    game =
//                        Game(
//                            gameDeck = emptyList(),
//                            playerList =
//                                listOf(
//                                    Player(cardsHand = listOf(DECK_PLAYER_CAN_NOT_MAKE_PASTYRE[0])),
//                                ),
//                        ),
//                ),
//            )
//
//        val fakeVm = FakeVM()
//
//        composeTestRule.setContent {
//            PastyraGameStart(fakeVm)
//        }
//
//        val nodes = composeTestRule.onAllNodesWithTag("CardView")
//
//        val playerCards = listOf(nodes[0], nodes[1], nodes[2], nodes[3])
//        val tableCards = listOf(nodes[4], nodes[5], nodes[6], nodes[7])
//
//        // Нажал на 10
//        playerCards[3].performClick()
//
//        val takeBtn = composeTestRule.onNodeWithText("Взять")
//
//        // Нажал на "Взять"
//        takeBtn.performClick()
//
//        // После нажатия, карта со стола уходит, так что снова нажимаем на 1 карту
//        repeat(4) {
//            tableCards[0].performClick()
//        }
//
//        val finishBtn = composeTestRule.onNodeWithText("Завершить ход")
//
//        // Нажал на "Завершить ход"
//        finishBtn.performClick()
//
//        val player = fakeVm.state.value.game.playerList[0]
//
//        Assert.assertEquals(
//            "Тест со взяткой прошел успешно",
//            1,
//            player.pastyrs,
//        )
//    }
}
