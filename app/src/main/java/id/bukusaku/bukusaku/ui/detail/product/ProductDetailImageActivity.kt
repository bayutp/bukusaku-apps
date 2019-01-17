package id.bukusaku.bukusaku.ui.detail.product

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.ImageProduct
import id.bukusaku.bukusaku.ui.bookmark.adapter.ViewPagerAdapter
import id.bukusaku.bukusaku.ui.detail.product.ProductDetailActivity.Companion.ID_IMG_PRODUCTS
import id.bukusaku.bukusaku.ui.detail.product.ProductDetailActivity.Companion.IMG_PRODUCT_LIST
import kotlinx.android.synthetic.main.activity_detail_image.*

class ProductDetailImageActivity : AppCompatActivity() {

    private val fragmentList: MutableList<ProductDetailImageFragment> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_image)

        setSupportActionBar(toolbar_detail_image)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setToolbarTransparent()
        initView()
    }

    private fun initView() {
        val url = intent.getParcelableArrayListExtra<ImageProduct>(IMG_PRODUCT_LIST)
        val position = intent.getIntExtra(ID_IMG_PRODUCTS,0)
        for (i in url.indices){
            fragmentList.add(ProductDetailImageFragment.instance(url[i].imgUrl))
        }
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(fragmentList)
        view_pager_detail_image.adapter = adapter
        view_pager_detail_image.currentItem = position
    }

    private fun setToolbarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
