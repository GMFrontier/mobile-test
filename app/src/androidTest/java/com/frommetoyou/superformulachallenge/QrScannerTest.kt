package com.frommetoyou.superformulachallenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import com.frommetoyou.superformulachallenge.common.R

@HiltAndroidTest
class QrScannerTest: AppAndroidTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCameraPermissionDenial_showsErrorDialog() {
        composeRule.onNodeWithTag("Floating action button").performClick()
        composeRule.onNodeWithTag("Scan").performClick()
        composeRule.waitForIdle()
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val dontAllowButton = device.findObject(UiSelector().textStartsWith("Don"))
        dontAllowButton.click()

        composeRule.onNodeWithText("Ok").performClick()
        dontAllowButton.click()

        composeRule.onNodeWithText("It seems that you have permanently declined camera permission. You can go to the app settings to grant it").assertIsDisplayed()
    }
}