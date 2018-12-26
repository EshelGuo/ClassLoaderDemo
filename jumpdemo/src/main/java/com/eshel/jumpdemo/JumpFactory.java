package com.eshel.jumpdemo;

import com.eshel.jump.JumpUtil;

public class JumpFactory {

    private static Jump mJump;

    public static Jump getJump(){
        if(mJump == null)
            mJump = JumpUtil.create(Jump.class);
        return mJump;
    }
}
