package com.l.wikipediasearch.views

import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.l.wikipediasearch.R
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {
    protected val subscriptions = CompositeDisposable()
    protected val handler = Handler()

    enum class TRANSITION {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT, NONE, VIEW_TRANSITION
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }

    /**
     * Adds fragment on current stack
     * There are bunch of options which are self explanatory
     */
    fun addFragment(fragment: Fragment, transition: TRANSITION = TRANSITION.RIGHT_TO_LEFT,
                    containerId: Int = R.id.container, replace: Boolean = false, addToBackStack: Boolean = true,
                    sharedElement: View? = null, tag: String = fragment.javaClass.simpleName, fragmentManager: FragmentManager = supportFragmentManager) {

        val transaction = fragmentManager.beginTransaction()

        when (transition) {
            TRANSITION.RIGHT_TO_LEFT -> {
                transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
            }

            TRANSITION.LEFT_TO_RIGHT -> {
                transaction.setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right,
                    R.anim.enter_from_right,
                    R.anim.exit_to_left
                )
            }

            TRANSITION.VIEW_TRANSITION -> {
                sharedElement?.let {
                    transaction.setReorderingAllowed(true)
                    transaction.addSharedElement(sharedElement, sharedElement.transitionName)
                }
            }

            else -> {
            }
        }


        if (replace) {
            transaction.replace(containerId, fragment, tag)
        } else {
            val oldFragment = getCurrentTopFragment(fragmentManager)
            oldFragment?.let { transaction.setMaxLifecycle(it, Lifecycle.State.STARTED) }
            /*
            set maxLifecycle of CurrentTopFragment to STARTED, when fragment is being added only,
            No need to setMaxLifecycle when fragment is being replaced
            */
            transaction.add(containerId, fragment, tag)
        }

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }

        //this does not crash in case activity was not in correct state
        transaction.commitAllowingStateLoss()
    }



    private fun getCurrentTopFragment(fragmentManager: FragmentManager): Fragment? {
        try {
            val stackCount = fragmentManager.backStackEntryCount
            if (stackCount > 0) {
                val backEntry = fragmentManager.getBackStackEntryAt(stackCount - 1)
                return fragmentManager.findFragmentByTag(backEntry.name)
            } else {
                val fragments = fragmentManager.fragments
                if (fragments.size > 0) {
                    for (f in fragments) {
                        if (f != null && !f.isHidden) {
                            return f
                        }
                    }
                }
            }
            return null
        } catch (e: KotlinNullPointerException) {
            //was reported in crashlytics
            //this is the kind of error which can be safely ignored as there are no side effects if top fragment is not found
            return null
        }
    }

}