package com.theone.loader

import android.view.View
import com.theone.loader.callback.Callback
import com.theone.loader.service.LoaderReplaceService
import com.theone.loader.service.LoaderService
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
 * @date 2022-03-27 10:12
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class Loader private constructor(){

    companion object {

        private val loader: Loader by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Loader()
        }

        fun getDefault() = loader

        fun beginBuilder() = Builder()

    }

    private var builder: Builder? = null

    fun register( target:View, default: Class<out Callback>? = null): LoaderService {
        if(null == builder){
            throw RuntimeException("builder is null, please init it first.")
        }
       return builder!!.getDefaultService().newInstance().register(target,builder,default)
    }

    class Builder {

        private val callbacks = mutableListOf<Class<out Callback>>()
        private var defaultCallback: Class<out Callback>? = null
        private var defaultService:Class<out LoaderService> = LoaderReplaceService::class.java

        fun addCallback(callback: Class<out Callback>): Builder {
            for (cb in callbacks) {
                if (cb == callback) {
                    throw RuntimeException("${callback.name} have already added.")
                }
            }
            callbacks.add(callback)
            return this
        }

        fun defaultCallback(default: Class<out Callback>?): Builder {
            defaultCallback = default
            return this
        }

        fun defaultService(default: Class<out LoaderService>):Builder{
            defaultService = default
            return this
        }

        fun getCallbacks() = callbacks

        fun getDefaultCallback() = defaultCallback

        fun getDefaultService() = defaultService

        fun commit() {
            if (callbacks.isEmpty()) {
                throw RuntimeException("callbacks is empty, please use addCallback method add your callback first.")
            }
            getDefault().builder = this
        }

    }

}