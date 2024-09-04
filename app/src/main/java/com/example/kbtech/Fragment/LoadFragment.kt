package com.example.kbtech.Fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class LoadFragment(private val activity: FragmentActivity) {

    private val fragmentManager: FragmentManager = activity.supportFragmentManager

    /**
     * Load a fragment into the specified container.
     * @param fragment The fragment to load.
     * @param containerId The ID of the container view to load the fragment into.
     * @param addToBackStack Whether to add this transaction to the back stack.
     */
    fun loadFragment(fragment: Fragment, containerId: Int, addToBackStack: Boolean = false) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(containerId, fragment)

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }

        fragmentTransaction.commit()
    }
}
