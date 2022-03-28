package com.theone.loader

import SuccessCallback
import android.content.Context
import android.os.Looper
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
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
 * @remark SuccessPage 直接 remove 还是 INVISIBLE/GONE ?
 */
class LoaderView {

    private val TAG = this.javaClass.simpleName

    private val callbacks = mutableListOf<Callback>()

    private var preCallback: Class<out Callback>? = null

    private var curCallback: Class<out Callback> = SuccessCallback::class.java

    private var rootView: ViewGroup? = null

    private var successCallback: SuccessCallback? = null

    private var loaderParams: ViewGroup.LayoutParams? = null

    private var successId: Int = 0

    fun getCurrentCallback() = curCallback

    fun showSuccessPage() = showCallbackView(SuccessCallback::class.java)

    fun register(target: View, builder: Loader.Builder?): LoaderView {
        if (null != rootView) {
            throw RuntimeException("Loader has been registered.")
        }
        val parent = target.parent
        rootView = when {
            parent is ViewGroup -> {
                successCallback = SuccessCallback().apply {
                    view = target
                    callbacks.add(this)
                }
                successId = target.id
//                preView = successCallback?.view
                parent
            }
            else -> {
                throw RuntimeException("Loader target must have a parent")
            }
        }
        loaderParams = target.layoutParams
        builder?.run {
            for (callback in getCallbacks()) {
                callbacks.add(callback.newInstance())
            }
            showCallbackView(getDefaultCallback())
        }
        return this
    }

    private fun ensureRootView(): ViewGroup {
        if (null == rootView) {
            throw IllegalArgumentException("Loader must have a rootView")
        }
        return rootView as ViewGroup
    }

    fun showCallbackView(
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
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(status, transport)
        } else {
            ensureRootView().post {
                show(status, transport)
            }
        }
    }

    private fun show(
        status: Class<out Callback>,
        transport: ((context: Context, view: View?) -> Unit)? = null
    ) {
        for (item in callbacks) {
            if (item.javaClass == status) {
                preCallback = status
                // 显示内容界面
                if (status == SuccessCallback::class.java) {
                    // 移除显示过的加载View
                    preView?.let {
                        ensureRootView().removeViewInLayout(it)
                    }
                    // 显示内容层，设置id
                    successCallback?.view?.run {
                        id = successId
                        visibility = View.VISIBLE
                    }
                } else {
                    // 显示状态界面
                    with(item) {
                        // 获取状态界面，为null则创建
                        ensureView(ensureRootView()).let {
                            // 替换
                            replaceContentWithView(it)
                            // 传递给外部更改界面
                            transport?.invoke(it.context, it)
                        }
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
            var index = 0
            preView?.let {
                index = indexOfChild(it)
                removeViewInLayout(it)
            }
            successCallback?.view?.let {
                it.visibility = View.GONE
                id = NO_ID
                // 设置id,解决ConstraintLayout布局问题
                if (successId > 0) {
                    view.id = id
                }
            }
            if (index > 0) {
                addView(view, index, loaderParams)
            } else {
                addView(view, loaderParams)
            }
            // 某些机型addView后不执行此方法，这里手动执行下
//                requestApplyInsets()
        }
        preView = view
    }

}