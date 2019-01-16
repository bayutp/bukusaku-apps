package id.bukusaku.bukusaku.ui.onboarding

import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.ui.MainActivity
import id.bukusaku.bukusaku.ui.onboarding.adapter.FragmentAdapter
import id.bukusaku.bukusaku.ui.splashscreen.SplashScreenActivity.Companion.STATUS_ON_BOARDING
import id.bukusaku.bukusaku.utils.SharePreference
import id.bukusaku.bukusaku.utils.gone
import id.bukusaku.bukusaku.utils.visible
import kotlinx.android.synthetic.main.activity_on_boarding.*
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject

class OnBoardingActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private val sp: SharePreference by inject()
    private val fragmentList: MutableList<OnBoardingFragment> = mutableListOf()

    companion object {
        const val NUM_PAGE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        initView()
        setupFragmentAdapter()
        setupListener()
    }

    private fun initView() {
        val title1 = getString(R.string.onboarding_title_1)
        val title2 = getString(R.string.onboarding_title_2)
        val title3 = getString(R.string.onboarding_title_3)

        val desc1 = getString(R.string.onboarding_desc_1)
        val desc2 = getString(R.string.onboarding_desc_2)
        val desc3 = getString(R.string.onboarding_desc_3)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        fragmentList.add(OnBoardingFragment.newInstance(title1, desc1, R.drawable.img_slide_1))
        fragmentList.add(OnBoardingFragment.newInstance(title2, desc2, R.drawable.img_slide_2))
        fragmentList.add(OnBoardingFragment.newInstance(title3, desc3, R.drawable.img_slide_3))

        btn_finish.gone()
    }

    private fun setupListener() {
        btn_finish.setOnClickListener {
            showMainActivity()
        }
    }

    private fun showMainActivity() {
        sp.writeBoolean(STATUS_ON_BOARDING, true)
        startActivity<MainActivity>()
        this.finish()
    }

    private fun setupFragmentAdapter() {
        val fragmentAdapter = FragmentAdapter(supportFragmentManager, fragmentList)
        view_pager_onboarding.adapter = fragmentAdapter
        view_pager_onboarding.addOnPageChangeListener(this)

    }

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(p0: Int) {
        layout_indicator.setSelected(p0)
        if (p0 == NUM_PAGE - 1) {
            btn_finish.visible()
        } else {
            btn_finish.gone()
        }
    }

}
