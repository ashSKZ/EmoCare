package com.example.emocare.ui.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object History : NavRoutes("history")
    object Profile : NavRoutes("profile")
    object Relaxation : NavRoutes("relaxation")
    object Result : NavRoutes("result/{emotion}/{confidence}/{recommendation}") {
        fun createRoute(emotion: String, confidence: Float, recommendation: String): String {
            val encodedRecommendation = java.net.URLEncoder.encode(recommendation, "UTF-8")
            return "result/$emotion/$confidence/$encodedRecommendation"
        }
    }
    object ExploreExercises : NavRoutes("explore_exercises")

}
