package id.bukusaku.bukusaku.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.R.id.*
import id.bukusaku.bukusaku.ui.articles.ArticlesFragment
import id.bukusaku.bukusaku.ui.favorites.FavoritesFragment
import id.bukusaku.bukusaku.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        supportActionBar?.title = resources.getString(R.string.app_name)
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                menu_home -> {
                    supportActionBar?.title = resources.getString(R.string.app_name)
                    loadFragment(savedInstanceState,HomeFragment(),HomeFragment::class.java.simpleName)
                }
                menu_article -> {
                    supportActionBar?.title = resources.getString(R.string.articles)
                    loadFragment(savedInstanceState,ArticlesFragment(),ArticlesFragment::class.java.simpleName)
                }
                menu_bookmark -> {
                    supportActionBar?.title = resources.getString(R.string.bookmark)
                    loadFragment(savedInstanceState, FavoritesFragment(), FavoritesFragment::class.java.simpleName)
                }
            }
            true
        }
        navigation.selectedItemId = menu_home
    }

    private fun loadFragment(savedInstanceState: Bundle?, fragment: Fragment, fragmentClass:String){
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragment, fragmentClass)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}
