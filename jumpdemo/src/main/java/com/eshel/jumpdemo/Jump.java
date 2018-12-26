package com.eshel.jumpdemo;

import android.content.Context;

import com.eshel.jump.IntentType;
import com.eshel.jump.anno.Intent;
import com.eshel.jump.anno.Params;

public interface Jump {
    @Intent(target = DemoAct.class, intentType = IntentType.Intent)
    void jumpDemoAct(Context context, @Params(key = "Int") int pInt, @Params(key = "Float") float pFloat, @Params(key = "String") String pString);
}
