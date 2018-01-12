package cn.mars.securekeyborad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Random;

import cn.mars.securekeyborad.util.LogUtil;

/**
 * 字母键盘管理
 * Created by ma.xuanwei on 2018/1/10.
 */

public class LetterKeyboard implements View.OnClickListener {
    //键盘根布局
    private View mView;
    private Context context;
    private Button btnSpace,btnDel,btnConvert;
    private final Button[] mLetterBtns = new Button[26];
    private boolean allowRandom = false;
    //是否大写
    private boolean isCapital =false;
    private View.OnClickListener onClickListener;

    LetterKeyboard(Context context,boolean allowRandom, View.OnClickListener onClickListener){
        this.allowRandom = allowRandom;
        this.context = context;
        this.onClickListener = onClickListener;
        loadViews();
        if(allowRandom){
            makeLetterButtonsRandom();
        }
    }

    public void makeLetterButtonsRandom() {
        LinkedList<String> digitList = new LinkedList<>();
        for(int i = 0;i<26;i++){
            digitList.add(String.valueOf((char)(isCapital?(65+i):(97+i))));
        }
        Random random = new Random();
        for(int j = 0; j < mLetterBtns.length;j++) {
            int pos = random.nextInt(digitList.size());
            String value = digitList.remove(pos);
            LogUtil.D("["+j+"] = "+value);
            mLetterBtns[j].setText(value);
        }
    }

    public View getView(){
        return mView;
    }

    private void loadViews() {
        mView = LayoutInflater.from(context).inflate(R.layout.letter_keyboard_layout,null);
        btnSpace = (Button) mView.findViewById(R.id.btnSpace);
        btnSpace.setOnClickListener(onClickListener);
        btnDel = (Button) mView.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(onClickListener);
        btnConvert = (Button) mView.findViewById(R.id.btnConvert);
        btnConvert.setOnClickListener(this);
        for (int i = 0; i < mLetterBtns.length ; i++){
            mLetterBtns[i] = (Button) mView.findViewById(context.getResources().getIdentifier("btnLetter"+i,"id",context.getPackageName()));
            LogUtil.D(mLetterBtns[i].getText().toString());
            mLetterBtns[i].setOnClickListener(onClickListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConvert:
                convert();
                break;
            default:
                break;
        }
    }

    private void convert() {
        isCapital = !isCapital;
        makeLetterCapital();
        btnConvert.setTextColor(context.getResources().getColor(isCapital?R.color.DarkBlue:R.color.white));

    }

    private void makeLetterCapital() {
        for(int j = 0; j < mLetterBtns.length;j++) {
            mLetterBtns[j].setText(isCapital?mLetterBtns[j].getText().toString().toUpperCase():mLetterBtns[j].getText().toString().toLowerCase());
        }
    }
}
