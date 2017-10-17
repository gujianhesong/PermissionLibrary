# PermissionLibrary
这是一个android权限检测请求的库，暂时提供三种权限的请求：
  - 悬浮窗
  - 通知
  - 辅助功能

悬浮窗权限检测请求调用：
```
  FloatPermissionMgr.checkOpenPermission(this, new OnPermissionListener() {
            @Override public void onFetchPermission(boolean success) {
  
            }
          });
```

通知权限检测请求调用：
```
  NotificationPermissionMgr.checkOpenPermission(this, new OnPermissionListener() {
            @Override public void onFetchPermission(boolean success) {
  
            }
          });
```

通知权限检测请求调用：
```
  //MyService代表注册的辅助功能服务Service
  AccessibilityPermissionMgr.checkOpenPermission(this, MyService.class, new OnPermissionListener() {
            @Override public void onFetchPermission(boolean success) {
  
            }
          });
```

效果图如下：
![](https://github.com/gujianhesong/PermissionLibrary/blob/master/screenshot/main.png)
![](https://github.com/gujianhesong/PermissionLibrary/blob/master/screenshot/request_dialog.png)
![](https://github.com/gujianhesong/PermissionLibrary/blob/master/screenshot/guide_float_dialog.png)
![](https://github.com/gujianhesong/PermissionLibrary/blob/master/screenshot/guide_notification_dialog.png)

当然每个权限管理类中也都包含判断和请求的方法，有额外需要的可以使用。
