package cn.mars.securekeyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Random;

import cn.mars.securekeyboard.util.LogUtil;

/**
 * 数字键盘管理
 * Created by ma.xuanwei on 2018/1/10.
 */

public class DigitKeyboard {
    //键盘根布局
    private View mView;
    private Context context;
    private Button btnSpace,btnDel;
    private final Button[] mDigitBtns = new Button[10];
    private boolean allowRandom = false;
    private View.OnClickListener onClickListener;

    DigitKeyboard(Context context, boolean allowRandom, View.OnClickListener onClickListener){
        this.allowRandom = allowRandom;
        this.context = context;
        this.onClickListener = onClickListener;
        loadViews();
        if(allowRandom){
            makeDigitButtonsRandom();
        }
    }

    public void makeDigitButtonsRandom() {
        LinkedList<String> digitList = new LinkedList<>();
        for(int i = 0;i<10;i++){
            digitList.add(String.valueOf(i));
        }
        Random random = new Random();
        for(int j = 0; j < mDigitBtns.length;j++) {
            int pos = random.nextInt(digitList.size());
            String value = digitList.remove(pos);
            LogUtil.D("["+j+"] = "+value);
            mDigitBtns[j].setText(value);
        }
    }

    public View getView(){
        return mView;
    }

    private void loadViews() {
        mView = LayoutInflater.from(context).inflate(R.layout.digit_keyboard_content,null);
        btnSpace = (Button) mView.findViewById(R.id.btnSpace);
        btnSpace.setOnClickListener(onClickListener);
        btnDel = (Button) mView.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(onClickListener);
        for (int i = 0; i < mDigitBtns.length ; i++){
            mDigitBtns[i] = (Button) mView.findViewById(context.getResources().getIdentifier("btnNum"+i,"id",context.getPackageName()));
            LogUtil.D(mDigitBtns[i].getText().toString());
            mDigitBtns[i].setOnClickListener(onClickListener);
        }
    }
}
