package com.theone.loader

import SuccessCallback
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ContentFrameLayout
import com.theone.loader.callback.Callback
import java.lang.RuntimeException

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
 * @date 2022-03-25 09:51
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

class LoaderView {

    private val TAG = this.javaClass.simpleName

    private var mLayoutInflater: LayoutInflater? = null

    private val callbacks = mutableListOf<Callback>()

    private var preCallback: Class<out Callback>? = null

    private var curCallback: Class<out Callback> = SuccessCallback::class.java

    private var rootView: ViewGroup? = null

    private var contentView: View? = null

    private var loaderParams: ViewGroup.LayoutParams? = null

    fun getCurrentCallback() = curCallback

    fun showSuccessPage() = show(SuccessCallback::class.java)

    fun register(target: View, builder: Loader.Builder?): LoaderView {
        if (null != rootView) {
            throw RuntimeException("Loader has been registered.")
        }
        val parent = target.parent
        rootView = when {
            parent is ViewGroup -> {
                contentView = target
                preView = contentView
                parent
            }
            target is ViewGroup -> {
                target
            }
            else -> {
                throw RuntimeException("Loader target must have a parent")
            }
        }
        loaderParams = target.layoutParams ?: ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        builder?.run {
            for (callback in getCallbacks()) {
                callbacks.add(callback.newInstance())
            }
            show(getDefaultCallback())
        }
        return this
    }

    private fun ensureRootView(): ViewGroup {
        if (null == rootView) {
            throw IllegalArgumentException("Loader must have a rootView")
        }
        return rootView as ViewGroup
    }

    private fun Callback.ensureCallbackView(): View {
        if (null == view) {
            val layoutId = layoutId()
            if (layoutId != 0) {
                rootView?.run {
                    val inflater = mLayoutInflater ?: LayoutInflater.from(context)
                    view = inflater.inflate(layoutId, this, false)
                }
            } else {
                throw IllegalArgumentException("${this.javaClass.simpleName} must have a valid layoutResource")
            }
        }
        return view!!
    }

    fun show(
        status: Class<out Callback>?,
        transport: ((context: Context, view: View?) -> Unit)? = null
    ) {
        if (null == status) {
            return
        }
        if (preCallback != null) {
            if (preCallback == status) {
                return
            }
        }
        if (status == SuccessCallback::class.java) {
            if (null == contentView) {
                preView?.let {
                    ensureRootView().removeViewInLayout(it)
                }
            } else {
                contentView?.let {
                    replaceContentWithView(it)
                }
            }
            return
        }
        for (item in callbacks) {
            if (item.javaClass == status) {
                preCallback = status
                with(item) {
                    ensureCallbackView().let {
                        replaceContentWithView(it)
                        transport?.invoke(it.context, it)
                    }
                }
                break
            }
        }
        curCallback = status
    }

    private var preView: View? = null

    private fun replaceContentWithView(view: View) {
        with(ensureRootView()) {
            if (contentView == null) {
                preView?.let {
                    removeViewInLayout(it)
                }
                addView(view, loaderParams)
            } else {
                preView?.let {
                    val id = it.id
                    if(id > 0){
                        view.id = id
                    }
                    val index = indexOfChild(it)
                    removeViewInLayout(it)
                    addView(view, index, loaderParams)
                    requestApplyInsets()
                }
            }
            preView = view
        }
    }

}