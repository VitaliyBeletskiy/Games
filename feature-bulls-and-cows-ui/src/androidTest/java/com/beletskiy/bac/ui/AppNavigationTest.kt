/*
package com.beletskiy.games

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppNavigationTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun init() {
        hiltRule.inject() // Inject dependencies before running tests

        composeTestRule.activity.setContent {
            // Always create a new TestNavHostController for each test
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            AppNavigation(navController = navController)
        }

        composeTestRule.runOnIdle {
            assertNotNull(navController) // Ensure navController is initialized
        }

        composeTestRule.waitForIdle() // Wait for Compose to stabilize
    }

    // Not needed right now
//    @After
//    fun tearDown() {
//        if (::navController.isInitialized) {
//            composeTestRule.runOnIdle {
//                // Clear the navigation stack to reset the state
//                navController.popBackStack(navController.graph.startDestinationId, inclusive = false)
//            }
//        }
//    }

    @Test
    fun appNavigation_verifyStartDestination() {
        navController.assertCurrentRouteName(AppScreens.GameScreen.name)
    }

    @Test
    fun appNavigation_verifyBackNavigationNotShownOnGameScreen() {
        val labelBack = composeTestRule.activity.getString(R.string.description_back)
        composeTestRule.onNodeWithContentDescription(labelBack).assertDoesNotExist()
    }

    @Test
    fun appNavigation_clickRules_navigatesToRulesScreen() {
        val labelRules = composeTestRule.activity.getString(R.string.description_rules)

        composeTestRule.onNodeWithContentDescription(labelRules).performClick()
        composeTestRule.waitForIdle() // Wait for UI stabilization
        composeTestRule.runOnIdle {
            navController.assertCurrentRouteName(AppScreens.RulesScreen.name)
        }
    }

    // Don't want to refactor this having just two screens
    @Test
    fun appNavigation_clickRulesThenClickBackButton_navigatesToRulesScreenThenBackToGameScreen() {
        val labelRules = composeTestRule.activity.getString(R.string.description_rules)

        composeTestRule.onNodeWithContentDescription(labelRules).performClick()
        composeTestRule.waitForIdle() // Wait for UI stabilization
        composeTestRule.runOnIdle {
            navController.assertCurrentRouteName(AppScreens.RulesScreen.name)
        }

        val labelBack = composeTestRule.activity.getString(R.string.description_back)
        composeTestRule.onNodeWithContentDescription(labelBack).performClick()
        composeTestRule.waitForIdle() // Wait for UI stabilization
        composeTestRule.runOnIdle {
            navController.assertCurrentRouteName(AppScreens.GameScreen.name)
        }
    }
}
*/
