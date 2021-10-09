# ScutWiFiManager

#### 备注：由于Android10 API扫描wifi极其耗电且需要定位权限，已放弃实现断线重连功能
#### 目前北校北区已全部通过测试

[点击下载]: https://github.com/suzakuwcx/ScutWiFiManager/releases/download/v1.1/ScutWIFIManager.apk

[点击下载][]

一个小工具，因为自己的手机浏览器无法记住密码，并且华工宿舍的wifi经常需要重新验证，于是就有了这个app

使用方法：

1. 在username处填写学号
2. 在password处填写密码
3. 点击连接即可，之后会一直保存帐号密码
4. 回到主界面或切换至其他程序，**程序进入后台时会直接退出，不会滞留在后台**

如果打开自动连接按钮，则下次打开程序会直接尝试连接学校wifi，如果
```
1. 该wifi已经联网

2. 该wifi不需验证

3. 连接学校wifi成功
```
   则程序会直接退出，**不需要再次手动点击连接按钮并手动退出**

### Q&A

- 打开了自动连接如何关闭：

  如果想设置其他的帐号密码，可以先断开网络再打开app防止程序退出，然后进行设置即可



- 安装软件提示有危险

  如果安装时提示该软件很危险，请忽略，该app只要了联网权限，非常安全



如果有好的想法或者建议，可以提交issues，一定会看的。
如果想添加一些功能，欢迎大家提交pull request。
