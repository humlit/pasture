package com.example.pastyra.domain.usercase

fun isPlayerCanTakeCardFromTheTable(deck: IntArray, findCard: Int): Boolean {
    val dp = BooleanArray(findCard + 1) { false }
    for (num in deck) {
        for (j in findCard downTo num) {
            if (dp[j - num]) {
                dp[j] = true
            }
        }
    }
    return dp[findCard]
}