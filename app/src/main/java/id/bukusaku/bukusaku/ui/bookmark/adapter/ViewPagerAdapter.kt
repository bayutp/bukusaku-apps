package id.bukusaku.bukusaku.ui.bookmark.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private val fragmentList = ArrayList<Fragment>()
    private val fragmentListTitle = ArrayList<String>()

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = fragmentListTitle[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentListTitle.add(title)
    }

    fun addFragment(fragment: List<Fragment>){
        fragmentList.addAll(fragment)
    }
}