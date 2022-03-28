# Loader

更改自 [LoadSir](https://github.com/KingJA/LoadSir)

由于使用LoadSir中存在以下问题：
1. 布局总会多一层`LoadLayout`嵌套。
2. 注册之后马上更改Callback失效。


### 优化
1. `Loader`使用`targetView`的`layoutParams`，将`Loading`，`Error`等状态页面直接显示到`targetView`位置，并处理`ConstraintLayout`布局问题。
2. `Callback`的`View`需要的时候才进行`inflate`加载，减少时间。


### Demo

![APK下载](https://qr.api.cli.im/newqr/create?data=http%253A%252F%252Ftheone.0851zy.com%252F2022%252F03%252F28%252F95cebb8540013e2c80912aae6544b054.apk&level=H&transparent=false&bgcolor=%23FFFFFF&forecolor=%23000&blockpixel=12&marginblock=2&logourl=&logoshape=no&size=244&embed_text_fontfamily=simhei.ttc&eye_use_fore=&background=images%2Fbackground%2Fbg25.png&wper=0.84&hper=0.84&tper=0.08&lper=0.08&qrcode_eyes=pin-3.png&outcolor=&incolor=%231694e3&body_type=0&qr_rotate=0&text=&fontfamily=syr.otf&fontsize=40&fontcolor=&logo_pos=0&kid=cliim&key=b77586f1acccfbd6ed2cb90e97b80de6)

### 使用

