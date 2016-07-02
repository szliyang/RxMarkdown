package com.yydcdut.rxmarkdown.grammar.android;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.span.MDTodoDoneSpan;

/**
 * Created by yuyidong on 16/5/17.
 * Key 与 UnOrderListGrammar 有关联
 */
class TodoDoneGrammar extends AbsAndroidGrammar {
    private static final int START_POSITION = 6;

    private int mColor;

    TodoDoneGrammar(@NonNull RxMDConfiguration rxMDConfiguration) {
        super(rxMDConfiguration);
        mColor = rxMDConfiguration.getTodoDoneColor();
    }

    @Override
    boolean isMatch(@NonNull String text) {
        return text.startsWith(KEY_0_TODO_DONE) ||
                text.startsWith(KEY_1_TODO_DONE);
    }

    @NonNull
    @Override
    SpannableStringBuilder encode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }

    @Override
    SpannableStringBuilder format(@NonNull SpannableStringBuilder ssb) {
        ssb.delete(0, START_POSITION);
        ssb.setSpan(new MDTodoDoneSpan(mColor), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @NonNull
    @Override
    SpannableStringBuilder decode(@NonNull SpannableStringBuilder ssb) {
        return ssb;
    }
}