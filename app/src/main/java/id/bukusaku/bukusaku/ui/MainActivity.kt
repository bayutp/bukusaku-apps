package id.bukusaku.bukusaku.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.R.id.*
import id.bukusaku.bukusaku.ui.articles.ArticlesFragment
import id.bukusaku.bukusaku.ui.bookmark.BookmarkFragment
import id.bukusaku.bukusaku.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        supportActionBar?.title = resources.getString(R.string.app_name)
        bottomNavigationState()
    }

    private fun bottomNavigationState() {
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                menu_home -> {
                    loadFragment(HomeFragment(), HomeFragment::class.java.simpleName, null)
                }
                menu_article -> {
                    loadFragment(ArticlesFragment(), ArticlesFragment::class.java.simpleName, null)
                }
                menu_bookmark -> {
                    loadFragment(BookmarkFragment(), BookmarkFragment::class.java.simpleName, null)
                }
            }
            true
        }
        navigation.selectedItemId = menu_home
    }

    private fun loadFragment(fragment: Fragment, TAG: String, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragment, TAG)
                .commit()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        navigation.selectedItemId = menu_home
        loadFragment(HomeFragment(), HomeFragment::class.java.simpleName, savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this@MainActivity.finish()
    }
}