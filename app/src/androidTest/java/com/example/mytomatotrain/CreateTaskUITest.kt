package com.example.mytomatotrain

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.main_module.MainActivity
import com.example.mytomatotrain.TimePeriodSchedulerMatcher.Companion.hasCardInPosition
import com.example.mytomatotrain.TomatoContainerTomatoCountMatcher.Companion.hasChildCount
import com.example.schedule.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import com.example.create_task.R as create_Task_R
import com.example.time_period_schedule.R as time_Period_R
import org.junit.Rule
import org.junit.Test

class CreateTaskUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private val taskTitle = "New task"
    private val periodicRes = create_Task_R.string.everyday

    @Test
    fun createTaskFlowTest() {
        onView(withId(R.id.schedule_add_new_task))
            .check(matches(withText(R.string.add_new_task)))
            .perform(click())

        val createTaskDoneButton = onView(withId(create_Task_R.id.toolbar_done_button))

        createTaskDoneButton.check(matches(isNotEnabled()))

        testCreateTask()

        createTaskDoneButton
            .check(matches(isEnabled()))
            .perform(click())

        val scheduleCard = onView(withText(periodicRes))
            .check(matches(withId(R.id.schedule_periodic_title)))

        scheduleCard.perform(click())

        testCreatedTaskCardDisplaying()
    }

    private fun testCreateTask() {
        val createTaskInput = onView(withId(create_Task_R.id.enter_task_name))
        createTaskInput.perform(ViewActions.typeText(taskTitle))

        testTomatoCounter()

        onView(withId(create_Task_R.id.periodic_button_everyday))
            .check(matches(withText(periodicRes)))
            .perform(click())

        onView(withId(create_Task_R.id.color_picker_grid))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
    }

    private fun testTomatoCounter() {
        val tomatoCounterAddButton = onView(withId(create_Task_R.id.add_tomato_button))
        tomatoCounterAddButton.perform(click())
        tomatoCounterAddButton.perform(click())
        tomatoCounterAddButton.perform(click())

        val tomatoCounterTomatoContainer = onView(withId(create_Task_R.id.tomatoes_container))
        tomatoCounterTomatoContainer.check(matches(hasChildCount(3)))
    }

    private fun testCreatedTaskCardDisplaying() {
        val timePeriodRecyclerView = onView(withId(time_Period_R.id.periodic_recycler_view))

        timePeriodRecyclerView.check(matches(
            hasCardInPosition(0, hasDescendant(withText(taskTitle)))))
    }
}

class TomatoContainerTomatoCountMatcher(private val expectedTomatoCount: Int)
    : BoundedMatcher<View, LinearLayout>(LinearLayout::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendText("TomatoContainerTomatoCountMatcher matcher")
    }

    override fun matchesSafely(item: LinearLayout?): Boolean {
        if (item == null) {
            return false
        }

        val actualChildCount = item.childCount - 1
        return actualChildCount == expectedTomatoCount
    }

    companion object {
        fun hasChildCount(expectedChildCount: Int): Matcher<View> {
            return TomatoContainerTomatoCountMatcher(expectedChildCount)
        }
    }

}

class TimePeriodSchedulerMatcher(
    private val position: Int,
    private val matcher: Matcher<View>
) : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendText("TimePeriodSchedulerMatcher matcher")
    }

    override fun matchesSafely(item: RecyclerView): Boolean {
        val itemView = item.findViewHolderForAdapterPosition(position)?.itemView
        return matcher.matches(itemView)
    }

    companion object {
        fun hasCardInPosition(position: Int, matcher: Matcher<View>): Matcher<View> {
            return TimePeriodSchedulerMatcher(position, matcher)
        }
    }


}