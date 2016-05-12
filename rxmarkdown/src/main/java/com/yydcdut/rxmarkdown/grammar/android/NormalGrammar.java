package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.grammar.IGrammar;

/**
 * Created by yuyidong on 16/5/4.
 */
class NormalGrammar implements IGrammar {

    @Override
    public boolean isMatch(@Nullable String text) {
        return false;
    }

    @Nullable
    @Override
    public SpannableStringBuilder format(@Nullable SpannableStringBuilder ssb) {
        return ssb;
    }

    @Override
    public String toString() {
        return "NormalGrammar{}";
    }
}
