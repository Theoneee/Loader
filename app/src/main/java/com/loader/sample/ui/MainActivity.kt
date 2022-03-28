package com.loader.sample.ui

import android.view.View
import com.loader.sample.databinding.ActivityMainBinding
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.theone.mvvm.base.activity.BaseVmDbActivity
import com.theone.mvvm.base.viewmodel.BaseViewModel
import com.theone.mvvm.entity.ProgressBean
import com.theone.mvvm.ext.qmui.addToGroup
import com.theone.mvvm.ext.qmui.createItem

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
 * @date 2022-03-28 09:32
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class MainActivity : BaseVmDbActivity<BaseViewModel, ActivityMainBinding>() {

    private lateinit var ROOT: QMUICommonListItemView
    private lateinit var CONTENT: QMUICommonListItemView
    private lateinit var VIEW: QMUICommonListItemView

    override fun initView(root: View) {
        getTopBar()?.setTitle("Loader")

        getDataBinding().groupListView.run {
            ROOT = createItem("Root")
            CONTENT = createItemView("Content")
            VIEW = createItemView("View")


            addToGroup(ROOT, CONTENT, VIEW,title = "") {
                val type = when (it) {
                    ROOT -> LoaderActivity.ROOT
                    CONTENT -> LoaderActivity.CONTENT
                    else -> LoaderActivity.VIEW
                }
                val name = (it as QMUICommonListItemView).text
                LoaderActivity.start(this@MainActivity, type,name.toString())
            }
        }
    }

    override fun hideProgress() {
    }


    override fun showProgress(progress: ProgressBean) {
    }

}