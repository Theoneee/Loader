# Loader

更改自 [LoadSir](https://github.com/KingJA/LoadSir)

由于使用LoadSir中存在以下问题：
1. 布局总会多一层`LoadLayout`嵌套。
2. 注册之后马上更改Callback失效。


`Loader`使用`registerView`的`layoutParams`，将Loading，Error等状态页面直接显示到`registerView`位置。
`Callback`的`View`需要的时候才进行`inflate`加载。

### 使用
