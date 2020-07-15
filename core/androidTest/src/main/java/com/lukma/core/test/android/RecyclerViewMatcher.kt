package com.lukma.core.test.android

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerViewId: Int) {
    fun atPosition(position: Int) = atPositionOnView(position, -1)

    fun atPositionOnView(
        position: Int,
        targetViewId: Int
    ): Matcher<View> = object : TypeSafeMatcher<View>() {
        private var resources: Resources? = null

        override fun describeTo(description: Description?) {
            val idDescription = if (resources != null) {
                try {
                    resources!!.getResourceName(recyclerViewId)
                } catch (ex: Resources.NotFoundException) {
                    String.format(
                        "%s (resource name not found)",
                        arrayOf<Any>(Integer.valueOf(recyclerViewId))
                    )
                }
            } else {
                recyclerViewId.toString()
            }

            description?.appendText("with id: $idDescription")
        }

        override fun matchesSafely(view: View?): Boolean {
            this.resources = view?.resources
            val recyclerView = view?.rootView?.findViewById<RecyclerView>(recyclerViewId)
            val childView = recyclerView?.findViewHolderForAdapterPosition(position)?.itemView
            return when {
                recyclerView == null || recyclerView.id != recyclerViewId -> false
                targetViewId == -1 -> view === childView
                else -> view == childView?.findViewById(targetViewId)
            }
        }
    }

    companion object {
        fun withId(id: Int) = RecyclerViewMatcher(id)
    }
}
