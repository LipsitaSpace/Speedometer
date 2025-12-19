package com.example.speedometer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.speedometer.screen.components.Speedometer
import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpeedometerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun speedometer_displaysSpeedAndUnit() {
        composeTestRule.setContent {
            Speedometer(
                speed = 80f,
                unit = "km/h",
                mode = true
            )
        }

        composeTestRule
            .onNodeWithTag("SpeedText")
            .assertIsDisplayed()
            .assertTextEquals("80.0")

        composeTestRule
            .onNodeWithTag("UnitText")
            .assertIsDisplayed()
            .assertTextEquals("km/h")
    }

    @Test
    fun speedometer_rendersInDarkMode() {
        composeTestRule.setContent {
            Speedometer(
                speed = 120f,
                unit = "km/h",
                mode = false
            )
        }

        composeTestRule
            .onNodeWithTag("SpeedometerRoot")
            .assertIsDisplayed()
    }

    @Test
    fun speedometer_formatsSpeedToOneDecimal() {
        composeTestRule.setContent {
            Speedometer(
                speed = 45.678f,
                unit = "km/h",
                mode = true
            )
        }

        composeTestRule
            .onNodeWithTag("SpeedText")
            .assertTextEquals("45.7")
    }
}