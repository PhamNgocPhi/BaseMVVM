package com.example.basemvvm.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.basemvvm.ui.list_category.ListCategoryFragment

class ScreenSlidePagerAdapter(fm: FragmentManager, private val numPage: Int) :
    FragmentStatePagerAdapter(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
//        return if (position == 0) {
//            SignupFragment()
//        } else {
//            LoginFragment()
//        }
        return ListCategoryFragment()
    }

    override fun getCount(): Int {
        return numPage
    }

}