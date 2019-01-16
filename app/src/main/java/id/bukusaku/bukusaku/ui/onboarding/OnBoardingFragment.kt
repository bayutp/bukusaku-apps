package id.bukusaku.bukusaku.ui.onboarding


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.bukusaku.bukusaku.R
import org.jetbrains.anko.find

class OnBoardingFragment : Fragment() {

    private lateinit var titleSlide: TextView
    private lateinit var descSlide: TextView
    private lateinit var imgSlide: ImageView

    companion object {
        const val TITLE_ON_BOARDING = "TITLE_ON_BOARDING"

        const val DESC_ON_BOARDING = "DESC_ON_BOARDING"

        const val IMG_ON_BOARDING = "IMG_ON_BOARDING"

        fun newInstance(title: String, desc: String, img: Int): OnBoardingFragment {
            val args = Bundle()
            val fragment = OnBoardingFragment()
            args.putString(TITLE_ON_BOARDING, title)
            args.putString(DESC_ON_BOARDING, desc)
            args.putInt(IMG_ON_BOARDING, img)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_on_boarding, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = arguments
        val request: RequestOptions = RequestOptions()
            .fitCenter()

        if (null != bundle) {
            titleSlide.text = bundle.getString(TITLE_ON_BOARDING)
            descSlide.text = bundle.getString(DESC_ON_BOARDING)
            Glide.with(this)
                .asBitmap()
                .apply(request)
                .load(bundle.getInt(IMG_ON_BOARDING))
                .into(imgSlide)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleSlide = view.find(R.id.tv_title_slide)
        descSlide = view.find(R.id.tv_desc_slide)
        imgSlide = view.find(R.id.img_slide)
    }
}
