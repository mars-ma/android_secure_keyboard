package cn.mars.securekeyborad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import cn.mars.securekeyborad.util.LogUtil;

/**
 * 符号键盘
 * Created by ma.xuanwei on 2018/1/11.
 */

public class SymbolKeyboard {
    //键盘根布局
    private View mView;
    private Context context;
    private Button btnSpace, btnDel, btnConvert;
    private final Button[] mSymBtns = new Button[32];
    private boolean allowRandom = false;
    private View.OnClickListener onClickListener;

    SymbolKeyboard(Context context, boolean allowRandom, View.OnClickListener onClickListener) {
        this.allowRandom = allowRandom;
        this.context = context;
        this.onClickListener = onClickListener;
        loadViews();
        if (allowRandom) {
            makeSymbolButtonsRandom();
        }else{
            makeNormal();
        }
    }

    private void makeNormal() {
        Queue<String> digitList = new LinkedList<>();
        for (int i = 33; i < 48; i++) { //15
            digitList.offer(String.valueOf((char) i));
        }
        for (int i = 58; i < 65; i++) { //7
            digitList.offer(String.valueOf((char) i));
        }
        for (int i = 91; i < 97; i++) { //6
            digitList.offer(String.valueOf((char) i));
        }
        for (int i = 123; i < 127; i++) { //4
            digitList.offer(String.valueOf((char) i));
        }
        for (int j = 0; j < mSymBtns.length; j++) {
            String value = digitList.poll();
            LogUtil.D("[" + j + "] = " + value);
            mSymBtns[j].setText(value);
        }
    }

    public View getView(){
        return mView;
    }

    private void makeSymbolButtonsRandom() {
        LinkedList<String> digitList = new LinkedList<>();
        for (int i = 33; i < 48; i++) { //15
            digitList.add(String.valueOf((char) i));
        }
        for (int i = 58; i < 65; i++) { //7
            digitList.add(String.valueOf((char) i));
        }
        for (int i = 91; i < 97; i++) { //6
            digitList.add(String.valueOf((char) i));
        }
        for (int i = 123; i < 127; i++) { //4
            digitList.add(String.valueOf((char) i));
        }
        Random random = new Random();
        for (int j = 0; j < mSymBtns.length; j++) {
            int pos = random.nextInt(digitList.size());
            String value = digitList.remove(pos);
            LogUtil.D("[" + j + "] = " + value);
            mSymBtns[j].setText(value);
        }
    }

    private void loadViews() {
        mView = LayoutInflater.from(context).inflate(R.layout.symbol_keyboard_layout, null);
        btnSpace = (Button) mView.findViewById(R.id.btnSpace);
        btnSpace.setOnClickListener(onClickListener);
        btnDel = (Button) mView.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(onClickListener);
        for (int i = 0; i < mSymBtns.length; i++) {
            mSymBtns[i] = (Button) mView.findViewById(context.getResources().getIdentifier("btnSymbol" + i, "id", context.getPackageName()));
            LogUtil.D(mSymBtns[i].getText().toString());
            mSymBtns[i].setOnClickListener(onClickListener);
        }
    }
}
