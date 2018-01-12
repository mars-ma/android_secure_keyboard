package cn.mars.securekeyboard;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * 安全的输入框，与安全键盘配套使用
 * Created by ma.xuanwei on 2018/1/9.
 */

public class SecureEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher, View.OnTouchListener {
    private KeyboardDialog keyboardDialog;
    private IEncryption encryption;
    private LinkedList<String> mPassword;
    private StringBuilder stringBuilder;

    public SecureEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SecureEditText(Context context, AttributeSet attributeSets) {
        super(context, attributeSets);
        init();
    }

    public SecureEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        disableShowInput();
        addTextChangedListener(this);
        setCustomKeyboard();
        mPassword = new LinkedList<>();
        stringBuilder = new StringBuilder();
    }

    /**
     * 禁止显示系统输入法
     */
    private void disableShowInput() {
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(this, false);
        } catch (Exception e) {//TODO: handle exception
        }
    }

    /**
     * 设置输入框触摸后弹出自定义键盘dialog
     */
    private void setCustomKeyboard() {
        setOnTouchListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setSelection(SecureEditText.this.length());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("mars_ma", "secure edittext onTouchEvent " + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("mars_ma", "secure edittext onTouchListener " + event.getAction());
        clear();
        if (keyboardDialog == null) {
            keyboardDialog = new KeyboardDialog(getContext(), this);
        }
        if (!keyboardDialog.isShowing()) {
            keyboardDialog.show();
        }
        return false;
    }

    public void setEncryption(IEncryption encryption) {
        this.encryption = encryption;
    }

    private IEncryption getEncryption() {
        if (encryption == null) {
            encryption = new DefaultEncryption();
        }
        return encryption;
    }

    public void append(String str) {
        mPassword.add(getEncryption().encode(str));
        stringBuilder.append("*");
        update();
    }

    public void delete() {
        if (mPassword.size() > 0) {
            mPassword.poll();
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        update();
    }

    private void update() {
        setText(stringBuilder.toString());
    }

    public String getPassword() {
        if (mPassword.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mPassword.size(); i++) {
            sb.append(encryption.decode(mPassword.get(i)));
        }
        return sb.toString();
    }

    public void clear() {
        mPassword.clear();
        stringBuilder.delete(0, stringBuilder.length());
        update();
    }
}
