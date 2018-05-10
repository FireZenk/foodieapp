package org.firezenk.foodieapp.config

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import com.schibsted.spain.barista.rule.flaky.FlakyTestRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class ProjectTestRule<T : Activity>(activityClass: Class<T>, val target: Any) : TestRule {

    companion object {
        private const val DEFAULT_FLAKY_ATTEMPTS = 3
        private const val LAUNCH_ACTIVITY_AUTOMATICALLY = false
        private const val INITIAL_TOUCH_MODE_ENABLED = true
    }

    private val daggerRule = getDaggerRule()
    private val clearPreferencesRule = ClearPreferencesRule()
    private val clearDatabaseRule = ClearDatabaseRule()
    private val clearFilesRule = ClearFilesRule()
    private val flakyTestRule = FlakyTestRule().apply {
        allowFlakyAttemptsByDefault(DEFAULT_FLAKY_ATTEMPTS)
    }
    private val activityTestRule: ActivityTestRule<T> = ActivityTestRule(activityClass,
            INITIAL_TOUCH_MODE_ENABLED,
            LAUNCH_ACTIVITY_AUTOMATICALLY)

    override fun apply(base: Statement?, description: Description?): Statement {
        return RuleChain.outerRule(flakyTestRule)
                // ↓ All rules below flakyTestRule will be repeated
                .around(activityTestRule)
                // ↓ All rules below activityTestRule will execute before launching the activity
                .around { newBase, _ -> daggerRule.apply(newBase, null, target) }
                .around(clearPreferencesRule)
                .around(clearDatabaseRule)
                .around(clearFilesRule)
                .apply(base, description)
    }

    fun launchActivity() {
        activityTestRule.launchActivity(null)
    }

    fun launchActivity(startIntent: Intent) {
        activityTestRule.launchActivity(startIntent)
    }
}