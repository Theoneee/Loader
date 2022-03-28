package com.loader.sample

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.loader.sample.callback.ErrorCallback
import com.loader.sample.callback.LoadingCallback
import com.theone.loader.LoaderView

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 * @author The one
 * @date 2022-03-28 09:39
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
fun LoaderView.showLoadingPage(msg: String? = null) {
    showCallbackView(LoadingCallback::class.java){ _, view ->
        msg?.let {
            view?.findViewById<TextView>(R.id.loading_tips)?.text = it
        }
    }
}

fun LoaderView.showErrorPage(
    msg: String?,
    imageRes: Int = R.mipmap.status_loading_view_loading_fail,
    click: ((View) -> Unit)? = null
) {
    showCallbackView(ErrorCallback::class.java){ _, view ->
        view?.run {
            msg?.let {
                findViewById<TextView>(R.id.stateContentTextView).text = it
            }
            val ivStatus = findViewById<ImageView>(R.id.stateImageView)
            ivStatus.setImageResource(imageRes)
            click?.let {
                setOnClickListener(it)
            }
        }
    }

}