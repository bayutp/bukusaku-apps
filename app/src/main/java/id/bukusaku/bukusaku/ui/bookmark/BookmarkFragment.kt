package id.bukusaku.bukusaku.ui.bookmark


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.ui.bookmark.adapter.ViewPagerAdapter
import id.bukusaku.bukusaku.ui.bookmark.articles.BookmarkArticleFragment
import id.bukusaku.bukusaku.ui.bookmark.products.BookmarkProductFragment
import org.jetbrains.anko.find

class BookmarkFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabBookmark: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_bookmark, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.find(R.id.view_pager)
        tabBookmark = view.find(R.id.bookmark_tab)

        tabBookmark.setupWithViewPager(viewPager)
        initTab(viewPager)
    }

    private fun initTab(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(BookmarkProductFragment(), getString(R.string.products).capitalize())
        adapter.addFragment(BookmarkArticleFragment(), getString(R.string.articles))
        viewPager.adapter = adapter
    }
}
