package com.example.speedometer

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TripDataScreenComposeTest {

    @get:Rule
    //val composeRule = createAndroidComposeRule<MainActivity>()
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun startButtonIsDisplayed() {
        composeRule.onNodeWithTag("start_text")
            .assertIsDisplayed()
    }

    @Test
    fun startButtonChangesToStop() {
        composeRule.onNodeWithTag("start_text")
            .performClick()
        composeRule.onNodeWithTag("start_text")
            .assertTextEquals("STOP")

        composeRule.onNodeWithTag("start_text")
            .performClick()
        composeRule.onNodeWithTag("start_text")
            .assertTextEquals("START")
    }

    @Test
    fun stopButtonChangesToStartOnLongClick() {
        composeRule.onNodeWithTag("start_text")
            .performTouchInput { longClick() }
        composeRule.onNodeWithTag("start_text")
            .assertTextEquals("START")
    }

    @Test
    fun textBoxDisplay(){
        composeRule.onAllNodesWithTag("text_box")
            .assertCountEquals(4)
    }

    @Test
    fun testTextBoxValues(){
        composeRule.onAllNodesWithTag("text_box")[0]
            .assertTextEquals("start location")

        composeRule.onAllNodesWithTag("text_box")[1]
            .assertTextEquals("00:00:00")

        composeRule.onAllNodesWithTag("text_box")[2]
            .assertTextEquals("destination")

        composeRule.onAllNodesWithTag("text_box")[3]
            .assertTextEquals("00:00:00")
    }

    @Test
    fun textIsDisplay(){
        composeRule.onNodeWithTag("start_text")
            .performClick()
        composeRule.onNodeWithTag("start_text")
            .assertTextEquals("STOP")
        composeRule.onAllNodesWithTag("text_box")[0]
            .assertIsDisplayed()
        composeRule.onAllNodesWithTag("text_box")[1]
            .assertIsDisplayed()

        composeRule.onNodeWithTag("start_text")
            .performClick()
        composeRule.onNodeWithTag("start_text")
            .assertTextEquals("START")
        composeRule.onAllNodesWithTag("text_box")[2]
            .assertIsDisplayed()
        composeRule.onAllNodesWithTag("text_box")[3]
            .assertIsDisplayed()

    }
    @Test
    fun testValuesAfterReset(){
        composeRule.onNodeWithTag("start_text")
            .performTouchInput { longClick() }
        composeRule.onNodeWithTag("start_text")
            .assertTextEquals("START")
        composeRule.onAllNodesWithTag("text_box")[0]
            .assertTextEquals("start location")

        composeRule.onAllNodesWithTag("text_box")[1]
            .assertTextEquals("00:00:00")

        composeRule.onAllNodesWithTag("text_box")[2]
            .assertTextEquals("destination")

        composeRule.onAllNodesWithTag("text_box")[3]
            .assertTextEquals("00:00:00")
    }

    @Test
    fun testButtonClick(){
        composeRule.onNodeWithTag("start_text")
            .assertIsDisplayed()
        repeat(15){
            composeRule.onNodeWithTag("start_text").performClick()
        }

        composeRule.onNodeWithTag("start_text").assertExists()
    }
}
