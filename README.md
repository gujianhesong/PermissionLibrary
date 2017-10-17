# PermissionLibrary

### 简介
一个android权限检测请求的库，暂时提供三种特殊权限的请求：
- 悬浮窗
- 通知
- 辅助功能
  
悬浮窗权限适配了两部分：
- 6.0版本以上非定制系统
- 6.0以下的部分国产机，包括华为，小米，魅族，oppo, vivo, 锤子等。

提供申请框和跳转引导框，同时每个权限管理类中也都包含判断和请求的方法，有额外需要的可以单独使用。

### 使用说明
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

判断权限是否开启调用：
```
  //是否需要请求悬浮窗权限
  AccessibilityPermissionMgr.needRequestFloatPermission(context);
  
  //通知权限是否开启
  AccessibilityPermissionMgr.isNotificationListenerEnabled(context);
  
  //辅助功能权限是否开启
  AccessibilityPermissionMgr.isAccessibilityServiceEnabled(context);
```


### 效果图
</br>
<img src="https://github.com/gujianhesong/PermissionLibrary/blob/master/screenshot/main.png?raw=true" width="280"/> <img src="https://github.com/gujianhesong/PermissionLibrary/blob/master/screenshot/request_dialog.png?raw=true" width="280"/>  
</br>
<img src="https://github.com/gujianhesong/PermissionLibrary/blob/master/screenshot/guide_float_dialog.png?raw=true" width="280"/> <img src="https://github.com/gujianhesong/PermissionLibrary/blob/master/screenshot/guide_notification_dialog.png?raw=true" width="280"/>  
</br>


## LICENSE

```
Copyright 2016 Pinery Team, All right reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```