# Loader

[![](https://jitpack.io/v/Theoneee/Loader.svg)](https://jitpack.io/#Theoneee/Loader)

更改自 [LoadSir](https://github.com/KingJA/LoadSir)

由于使用LoadSir中存在以下问题：
1. 布局总会多一层`LoadLayout`嵌套。
2. 注册之后马上更改Callback失效。


### 优化

 `Loader`使用`targetView`的`layoutParams`，将`Loading`，`Error`等状态页面直接显示到`targetView`位置，并处理`ConstraintLayout`布局问题。


### Demo

![APK下载](https://qr.api.cli.im/newqr/create?data=http%253A%252F%252Ftheone.0851zy.com%252F2022%252F03%252F28%252F95cebb8540013e2c80912aae6544b054.apk&level=H&transparent=false&bgcolor=%23FFFFFF&forecolor=%23000&blockpixel=12&marginblock=2&logourl=&logoshape=no&size=244&embed_text_fontfamily=simhei.ttc&eye_use_fore=&background=images%2Fbackground%2Fbg25.png&wper=0.84&hper=0.84&tper=0.08&lper=0.08&qrcode_eyes=pin-3.png&outcolor=&incolor=%231694e3&body_type=0&qr_rotate=0&text=&fontfamily=syr.otf&fontsize=40&fontcolor=&logo_pos=0&kid=cliim&key=b77586f1acccfbd6ed2cb90e97b80de6)

### 使用

**目前测试阶段.... 慎用**

和`LoadSir`使用基本一样

1. 添加依赖
```
dependencies {
      implementation 'com.github.Theoneee:Loader:Tag'
}
```
2. 定义Callback
```kotlin
class LoadingCallback: Callback() {

    override fun layoutId(): Int  = R.layout.loader_loading_layout

}

class ErrorCallback: Callback() {

    override fun layoutId(): Int  = R.layout.loader_error_layout

}
```
3. 配置
```java

Loader.beginBuilder()
            .addCallback(LoadingCallback::class.java)
            .addCallback(ErrorCallback::class.java)
            .defaultCallback(SuccessCallback::class.java)
            .commit()

```

4. 添加几个设置的扩展方法

```kotlin

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

```

5. 调用

```kotlin

        val mLoader = Loader.getDefault().register(registerView)

        mLoader.run {
            showLoadingPage("加载中")
            delay(2000) {
                showErrorPage("当前无网络，请检查网络状态") {
                    showLoadingPage("再次加载中")
                    delay(1000) {
                        showSuccessPage()
                    }
                }
            }

        }

```

具体使用参考Demo

