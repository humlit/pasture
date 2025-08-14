package com.example.pastyra.data.usercase

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