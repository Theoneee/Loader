package com.loader.sample

import SuccessCallback
import android.app.Application
import com.loader.sample.callback.ErrorCallback
import com.loader.sample.callback.LoadingCallback
import com.theone.loader.Loader
import com.theone.mvvm.base.BaseApplication

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
 * @date 2022-03-28 09:30
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class App : BaseApplication() {

    override fun init(application: Application) {
        Loader.beginBuilder()
            .addCallback(LoadingCallback::class.java)
            .addCallback(ErrorCallback::class.java)
            .defaultCallback(SuccessCallback::class.java)
            .commit()
    }

    override fun isDebug(): Boolean = BuildConfig.DEBUG

}