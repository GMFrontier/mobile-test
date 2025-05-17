package com.frommetoyou.superformulachallenge

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class AppAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)


    protected lateinit var context: Context

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
    }
}