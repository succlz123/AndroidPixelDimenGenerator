# AndroidPixelDimenGenerator

![1](https://github.com/succlz123/AndroidPixelDimenGenerator/blob/master/snapshot/1.jpeg "")

## Android Studio插件简介
根据输入的像素来自动生成Android项目的dimen.xml文件,主要是为了适配国产Android TV盒子的各种分辨率。

## 使用方法
- 下载编译好的jar文件

- 打开Android Studio - Preferences - Plugins - Install plugin from disk - Restart Android Studio

- 点击Tools菜单 - AndroidPixelDimenGenerator

- 根据提示输入分辨率的取值范围和分辨率

## 输入注意事项
```
第一行是正数范围
1000
```

```
第二行是负数范围
-1000
```

```
1920 1080
宽分辨率空格高分辨率换行
1280 720
宽分辨率空格高分辨率换行
```

## 感谢
生成xml文件内容的代码来自于https://github.com/yann9/DimenGenerator