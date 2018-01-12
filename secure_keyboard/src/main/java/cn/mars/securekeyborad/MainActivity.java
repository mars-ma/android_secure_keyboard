package cn.mars.securekeyborad;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SecureEditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.sample_text);
        et = (SecureEditText) findViewById(R.id.et);
    }

    public void getPassword(View view) {
        tv.setText(et.getPassword());
    }
}
