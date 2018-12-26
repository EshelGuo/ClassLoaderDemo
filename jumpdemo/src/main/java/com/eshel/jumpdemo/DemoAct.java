package com.eshel.jumpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eshel.jump.IntentType;
import com.eshel.jump.JumpUtil;
import com.eshel.jump.anno.IntentParser;
import com.eshel.jump.anno.Params;

import java.util.Locale;

public class DemoAct extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mTextView = findViewById(R.id.textView2);
        JumpUtil.parseIntent(this, getIntent());
    }

    @IntentParser(intentType = IntentType.Intent)
    public void parseIntent(@Params(key = "Int") int pInt, @Params(key = "Float") float pFloat, @Params(key = "String") String pString){
        mTextView.setText(String.format(Locale.getDefault(),
                "parseIntent() called with: pInt = [%d], pFloat = [%s], pString = [%s]", pInt, pFloat, pString));
    }
}
