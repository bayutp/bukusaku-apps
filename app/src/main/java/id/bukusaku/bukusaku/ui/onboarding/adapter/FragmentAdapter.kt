package id.bukusaku.bukusaku.ui.onboarding.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.bukusaku.bukusaku.ui.onboarding.OnBoardingFragment

class FragmentAdapter(fm: FragmentManager, private var fragmentList: MutableList<OnBoardingFragment>) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment {
        return fragmentList[p0]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}