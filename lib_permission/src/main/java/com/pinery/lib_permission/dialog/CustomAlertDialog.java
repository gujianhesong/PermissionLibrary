package com.pinery.lib_permission.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.pinery.lib_permission.R;
import com.pinery.lib_permission.util.ViewUtil;

/**
 * 自定义提示框, Material风格
 *
 * Created by hesong on 16/7/1.
 */

public class CustomAlertDialog {

    private Context mContext;
    private Dialog mDialog;

    private View mContentView;

    private TextView tvTitle, tvMessage;
    private TextView btnCancel, btnConfirm;

    public CustomAlertDialog(Context context){
        mContext = context;
        mDialog = new Dialog(context, R.style.CustomDialog);

        initContentView();

        mDialog.setContentView(mContentView);

        //背景设为透明,去黑边
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void initContentView(){

        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_confirm, null);
        tvTitle = ViewUtil.findViewById(mContentView, R.id.tv_tip_title);
        tvMessage = ViewUtil.findViewById(mContentView, R.id.tv_tip_content);
        btnCancel = ViewUtil.findViewById(mContentView, R.id.btn_cancel);
        btnConfirm = ViewUtil.findViewById(mContentView, R.id.btn_confirm);

        //ThemeUtil.setLollipopEffect(btnCancel);
        //ThemeUtil.setLollipopEffect(btnConfirm);

    }

    public CustomAlertDialog setTitle(String title){
        tvTitle.setText(title);
        tvTitle.setVisibility(View.VISIBLE);

        return this;
    }

    public CustomAlertDialog setTitle(int titleId){
        tvTitle.setText(titleId);
        tvTitle.setVisibility(View.VISIBLE);

        return this;
    }

    public CustomAlertDialog setMessage(String message){
        tvMessage.setText(message);

        return this;
    }

    public CustomAlertDialog setMessage(int messageId){
        tvMessage.setText(messageId);

        return this;
    }

    public CustomAlertDialog setPositiveButton(final View.OnClickListener listener){
        btnConfirm.setVisibility(View.VISIBLE);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if(listener != null){
                    listener.onClick(v);
                }
            }
        });

        return this;
    }

    public CustomAlertDialog setPositiveButton(String text, final View.OnClickListener listener){
        btnConfirm.setVisibility(View.VISIBLE);
        btnConfirm.setText(text);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if(listener != null){
                    listener.onClick(v);
                }
            }
        });

        return this;
    }

    public CustomAlertDialog setPositiveButton(int textId, final View.OnClickListener listener){
        btnConfirm.setVisibility(View.VISIBLE);
        btnConfirm.setText(textId);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if(listener != null){
                    listener.onClick(v);
                }
            }
        });

        return this;
    }

    public CustomAlertDialog setNegativeButton(final View.OnClickListener listener){
        btnCancel.setVisibility(View.VISIBLE);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if(listener != null){
                    listener.onClick(v);
                }
            }
        });

        return this;
    }

    public CustomAlertDialog setNegativeButton(String text, final View.OnClickListener listener){
        btnCancel.setVisibility(View.VISIBLE);
        btnCancel.setText(text);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if(listener != null){
                    listener.onClick(v);
                }
            }
        });

        return this;
    }

    public CustomAlertDialog setNegativeButton(int textId, final View.OnClickListener listener){
        btnCancel.setVisibility(View.VISIBLE);
        btnCancel.setText(textId);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

                if(listener != null){
                    listener.onClick(v);
                }

            }
        });

        return this;
    }

    public CustomAlertDialog setCancelable(boolean cancelable){
        mDialog.setCancelable(cancelable);

        return this;
    }

    public CustomAlertDialog setOnCancelListener(DialogInterface.OnCancelListener listener){
        mDialog.setOnCancelListener(listener);

        return this;
    }

    public CustomAlertDialog setOnDismissListener(DialogInterface.OnDismissListener listener){
        mDialog.setOnDismissListener(listener);

        return this;
    }

    public CustomAlertDialog show(){
        mDialog.show();

        return this;
    }

    public CustomAlertDialog cancel(){
        mDialog.cancel();

        return this;
    }

    public CustomAlertDialog dismiss(){
        mDialog.dismiss();

        return this;
    }

}
