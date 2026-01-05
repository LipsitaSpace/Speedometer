package com.example.speedometer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.speedometer.screen.components.TimeAndDistance
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TImeAndDistanceTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun timeAndDistance_displaysCorrectValues() {

        val distance = 200f
        val avgSpeed = 40f
        val time = "10:20:43"

        composeTestRule.setContent {
            TimeAndDistance(
                distance = distance,
                time = time,
                avgSpeed = avgSpeed,
                mode = true
            )
        }

        composeTestRule.onNodeWithText("DISTANCE").assertIsDisplayed()
        composeTestRule.onNodeWithText("AVG SPEED").assertIsDisplayed()

        composeTestRule.onNodeWithTag("distance_value")
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("avg_speed_value")
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("time_value")
            .assertIsDisplayed()
    }
}