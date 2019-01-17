package id.bukusaku.bukusaku.ui.detail.product


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.utils.loadImage
import org.jetbrains.anko.find

class ProductDetailImageFragment : Fragment() {

    companion object {
        const val IMG_URL = "IMG_URL"
        fun instance(url: String?): ProductDetailImageFragment {
            val args = Bundle()
            val fragment = ProductDetailImageFragment()
            args.putString(IMG_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var imageDetail: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_detail_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageDetail = view.find(R.id.img_product_detail_list)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments
        if (null != args){
            imageDetail.loadImage(args.getString(IMG_URL))
        }
    }
}
