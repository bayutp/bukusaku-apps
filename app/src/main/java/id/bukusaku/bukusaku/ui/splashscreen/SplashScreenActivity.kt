package id.bukusaku.bukusaku.ui.splashscreen

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.ui.MainActivity
import id.bukusaku.bukusaku.ui.onboarding.OnBoardingActivity
import id.bukusaku.bukusaku.utils.SharePreference
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import kotlin.concurrent.thread


class SplashScreenActivity : AppCompatActivity() {
    companion object {
        const val STATUS_ON_BOARDING = "STATUS_ON_BOARDING"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        launchActivity()
    }

    private fun launchActivity() {
        val sp: SharePreference by inject()
        sp.init(applicationContext)
        val status = sp.readBoolean(STATUS_ON_BOARDING, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        thread(start = true) {
            try {
                Thread.sleep(5000L)
            } catch (e: Throwable) {
                e.printStackTrace()
            } finally {
                status.let {
                    when (it) {
                        true -> startActivity<MainActivity>()
                        else -> startActivity<OnBoardingActivity>()
                    }
                }
                this@SplashScreenActivity.finish()
            }
        }
    }
}