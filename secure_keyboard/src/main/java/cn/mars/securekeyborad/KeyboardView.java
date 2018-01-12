package cn.mars.securekeyborad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.mars.securekeyborad.util.LogUtil;

/**
 * 键盘View层
 * Created by ma.xuanwei on 2018/1/10.
 */

public class KeyboardView implements View.OnClickListener {
    private Context context;
    //根布局
    private ViewGroup rootLayout;
    //键盘帧布局
    private FrameLayout mContentLayout;
    //数字,字母,符号转换按钮
    private TextView tvDigit, tvLetter, tvSymbol;
    //输入完毕按钮
    private Button btnOk;

    //当前键盘类型
    private KeyboardType mKeyboardType;

    private DigitKeyboard digitKeyboard;
    private LetterKeyboard letterKeyboard;
    private SymbolKeyboard symbolKeyboard;
    private KeyListener keyListener;

    public KeyboardView(Context c,KeyListener keyListener) {
        context = c;
        this.keyListener = keyListener;
        //加载布局
        initRootLayout();
        //初始加载字母键盘
        loadKeyboard(KeyboardType.LETTER);
    }

    public View getView() {
        return rootLayout;
    }

    private void initRootLayout() {
        rootLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.secure_keyboard_layout, null);
        mContentLayout = (FrameLayout) rootLayout.findViewById(R.id.content);
        tvDigit = (TextView) rootLayout.findViewById(R.id.tvDigit);
        tvLetter = (TextView) rootLayout.findViewById(R.id.tvLetter);
        tvSymbol = (TextView) rootLayout.findViewById(R.id.tvSymbol);
        btnOk = (Button) rootLayout.findViewById(R.id.btnOK);

        tvDigit.setOnClickListener(this);
        tvLetter.setOnClickListener(this);
        tvSymbol.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDigit:
                loadKeyboard(KeyboardType.DIGIT);
                break;
            case R.id.tvLetter:
                loadKeyboard(KeyboardType.LETTER);
                break;
            case R.id.tvSymbol:
                loadKeyboard(KeyboardType.SYMBOL);
                break;
            case R.id.btnOK:
                LogUtil.D("输入完毕");
                keyListener.onFinish();
                break;
            case R.id.btnSpace:
                LogUtil.D("输入空格");
                keyListener.onInputSpace();
                break;
            case R.id.btnDel:
                LogUtil.D("删除字符");
                keyListener.onDelete();
                break;
            default:
                if(v instanceof Button){
                    String str = ((Button)v).getText().toString();
                    LogUtil.D("输入字符:"+str);
                    keyListener.onInputChar(str);
                }
                break;
        }
    }

    /**
     * 根据键盘类型加载键盘View
     * @param type
     */
    private void loadKeyboard(KeyboardType type) {
        switch (type) {
            case DIGIT:
                loadDigitKeyboard();
                break;
            case LETTER:
                loadLetterKeyboard();
                break;
            case SYMBOL:
                loadSymbolKeyboard();
                break;
            default:
                break;
        }
        mKeyboardType = type;
    }

    /**
     * 加载符号键盘
     */
    private void loadSymbolKeyboard() {
        if(symbolKeyboard == null){
            symbolKeyboard = new SymbolKeyboard(context,false,this);
        }
        if(mKeyboardType != KeyboardType.SYMBOL){
            //替换帧布局
            mContentLayout.removeAllViews();
            mContentLayout.addView(symbolKeyboard.getView());

            //高亮字母标签
            mark(tvSymbol);
        }
    }

    /**
     * 加载字母键盘
     */
    private void loadLetterKeyboard() {
        if(letterKeyboard == null){
            letterKeyboard = new LetterKeyboard(context,true,this);
        }
        if(mKeyboardType != KeyboardType.LETTER){
            //替换帧布局
            mContentLayout.removeAllViews();
            mContentLayout.addView(letterKeyboard.getView());

            //高亮字母标签
            mark(tvLetter);
        }
    }

    /**
     * 加载数字键盘
     */
    private void loadDigitKeyboard() {
        if(digitKeyboard == null){
            digitKeyboard = new DigitKeyboard(context,true,this);
        }
        if(mKeyboardType != KeyboardType.DIGIT) {
            //替换帧布局
            mContentLayout.removeAllViews();
            mContentLayout.addView(digitKeyboard.getView());

            //高亮数字标签
            mark(tvDigit);
        }
    }

    private void mark(TextView textView) {
        tvDigit.setTextColor(context.getResources().getColor(R.color.LightGray));
        tvSymbol.setTextColor(context.getResources().getColor(R.color.LightGray));
        tvLetter.setTextColor(context.getResources().getColor(R.color.LightGray));
        textView.setTextColor(context.getResources().getColor(R.color.DarkBlue));
    }

    public void reset() {
        if(digitKeyboard!=null){
            digitKeyboard.makeDigitButtonsRandom();
        }
        if(letterKeyboard!=null){
            letterKeyboard.makeLetterButtonsRandom();
        }

    }

    enum KeyboardType {
        DIGIT, LETTER, SYMBOL
    }

    public interface KeyListener{
        void onFinish();
        void onDelete();
        void onInputSpace();
        void onInputChar(String str);
    }
}
