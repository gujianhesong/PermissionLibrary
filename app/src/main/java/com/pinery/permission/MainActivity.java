package com.pinery.permission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.pinery.lib_permission.callback.OnPermissionListener;
import com.pinery.lib_permission.manager.AccessibilityPermissionMgr;
import com.pinery.lib_permission.manager.FloatPermissionMgr;
import com.pinery.lib_permission.manager.NotificationPermissionMgr;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.btn_float).setOnClickListener(this);
    findViewById(R.id.btn_notification).setOnClickListener(this);
    findViewById(R.id.btn_accessability).setOnClickListener(this);


  }

  @Override public void onClick(View view) {
    switch (view.getId()){
      case R.id.btn_float:

        FloatPermissionMgr.checkOpenPermission(this, new OnPermissionListener() {
          @Override public void onFetchPermission(boolean success) {

          }
        });

        break;
      case R.id.btn_notification:

        NotificationPermissionMgr.checkOpenPermission(this, new OnPermissionListener() {
          @Override public void onFetchPermission(boolean success) {

          }
        });

        break;
      case R.id.btn_accessability:

        AccessibilityPermissionMgr.checkOpenPermission(this, MyService.class, new OnPermissionListener() {
          @Override public void onFetchPermission(boolean success) {

          }
        });

        break;
    }
  }
}
