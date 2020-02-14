package com.patlejch.timetables.model.navigation

import com.patlejch.timetables.R
import com.skoumal.teanity.viewevents.NavigationEvent

object Navigation {

    fun setup() = NavigationEvent {
        navDirections {
            destination = R.id.setupActivity
            clearTask = true
        }
        navOptions { launchSingleTop = true }
    }

    fun main() = NavigationEvent {
        navDirections {
            destination = R.id.mainActivity
            clearTask = true
        }
        navOptions { launchSingleTop = true }
    }

    object Setup {
        private fun setupEvent(dest: Int) = NavigationEvent {
            navDirections { destination = dest }
            navOptions {
                launchSingleTop = true
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_left
                    popExit = R.anim.slide_out_right
                }
            }
        }

        fun last() = setupEvent(R.id.lastFragment)
    }

}
