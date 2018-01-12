package cn.mars.securekeyborad;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * 键盘对话框
 * Created by ma.xuanwei on 2018/1/10.
 */

public class KeyboardDialog extends Dialog implements KeyboardView.KeyListener {
    private SecureEditText secureEditText;
    private KeyboardView keyboardView;

    public KeyboardDialog(Context context,SecureEditText editText){
        this(context,true,null,editText,R.style.KeyboardDialog);
    }

    public KeyboardDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener,SecureEditText editText,int themeId) {
        super(context,themeId);
        secureEditText = editText;
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
        init();
    }

    @Override
    public void show(){
        super.show();
        //设置对话框的窗口位于底部,宽与屏幕同宽
        //int screenWidth =  getWindow().getWindowManager().getDefaultDisplay().getWidth();
        WindowManager.LayoutParams mParams = getWindow().getAttributes();
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setGravity(Gravity.BOTTOM|Gravity.LEFT);
        getWindow().setAttributes(mParams);
        keyboardView.reset();
    }

    private void init() {
        //加载键盘布局
        keyboardView = new KeyboardView(getContext(),this);
        setContentView(keyboardView.getView());
    }

    @Override
    public void onFinish() {
        dismiss();
    }

    @Override
    public void onDelete() {
        secureEditText.delete();
    }

    @Override
    public void onInputSpace() {
        secureEditText.append(" ");
    }

    @Override
    public void onInputChar(String str) {
        secureEditText.append(str);
    }
}
