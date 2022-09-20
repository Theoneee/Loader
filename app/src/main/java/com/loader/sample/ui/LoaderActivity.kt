package com.loader.sample.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import com.loader.sample.callback.ErrorCallback
import com.loader.sample.callback.LoadingCallback
import com.loader.sample.databinding.ActivityTestBinding
import com.loader.sample.delay
import com.loader.sample.showErrorPage
import com.loader.sample.showLoadingPage
import com.qmuiteam.qmui.widget.QMUITopBarLayout
import com.theone.loader.Loader
import com.theone.mvvm.base.activity.BaseVbActivity
import com.theone.mvvm.base.activity.BaseVmDbActivity
import com.theone.mvvm.base.viewmodel.BaseViewModel

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
 * @date 2022-03-28 10:54
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class LoaderActivity : BaseVbActivity<ActivityTestBinding>() {


    companion object {

        val ROOT = 0
        val CONTENT = 1
        val VIEW = 2

        private val TYPE = "type"
        private val NAME = "name"

        fun start(activity: Activity, type: Int, name: String) {
            activity.startActivity(Intent(activity, LoaderActivity::class.java).apply {
                putExtra(TYPE, type)
                putExtra(NAME, name)
            })
        }

    }

    override fun QMUITopBarLayout.initTopBar() {
        setTitle("Loader - ${intent.getStringExtra(NAME)}")
    }

    override fun initView(root: View) {
        val registerView = when (intent.getIntExtra(TYPE, ROOT)) {
            ROOT -> getViewConstructor().getRootView()
            CONTENT -> getViewBinding().root
            else -> getViewBinding().center
        }

        val mLoader = Loader.getDefault().register(registerView, LoadingCallback::class.java)

        delay(2000) {
            mLoader.run {
                showErrorPage("当前无网络，请检查网络状态") {
                    showLoadingPage("再次加载中")
                    delay(1000) {
                        showSuccessPage()
                    }
                }
            }
        }
    }

}