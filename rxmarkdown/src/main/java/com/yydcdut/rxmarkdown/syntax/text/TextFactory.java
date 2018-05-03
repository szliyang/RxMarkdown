/*
 * Copyright (C) 2016 yydcdut (yuyidong2015@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yydcdut.rxmarkdown.syntax.text;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.chain.ISpecialChain;
import com.yydcdut.rxmarkdown.chain.MultiSyntaxChain;
import com.yydcdut.rxmarkdown.chain.SyntaxChain;
import com.yydcdut.rxmarkdown.chain.SyntaxDoElseChain;
import com.yydcdut.rxmarkdown.chain.SyntaxMultiChains;
import com.yydcdut.rxmarkdown.syntax.Syntax;
import com.yydcdut.rxmarkdown.syntax.SyntaxFactory;

/**
 * This factory's purpose is parsing content <b>correctly</b>, as the same time, it destroys the integrity of the content.
 * This factory will delete the key words of markdown syntax in content.
 * So, hope that it will be used in TextView, not in EditText.
 * <p>
 * Created by yuyidong on 16/5/12.
 */
public class TextFactory implements SyntaxFactory {
    private static final String NEWLINE = "\n";
    private RxMDConfiguration mRxMDConfiguration;
    private ISpecialChain mLineChain;
    private ISpecialChain mTotalChain;

    private TextFactory() {
    }

    /**
     * get AndroidFactory object
     *
     * @return {@link SyntaxFactory}
     */
    public static SyntaxFactory create() {
        return new TextFactory();
    }

    @Override
    public Syntax getHorizontalRulesSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new HorizontalRulesSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getBlockQuotesSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new BlockQuotesSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getTodoSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new TodoSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getTodoDoneSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new TodoDoneSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getOrderListSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new OrderListSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getUnOrderListSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new UnOrderListSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getCenterAlignSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new CenterAlignSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getHeaderSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new HeaderSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getBoldSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new BoldSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getItalicSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new ItalicSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getInlineCodeSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new InlineCodeSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getStrikeThroughSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new StrikeThroughSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getFootnoteSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new FootnoteSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getImageSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new ImageSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getHyperLinkSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new HyperLinkSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getCodeSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new CodeBlockSyntax(rxMDConfiguration);
    }

    @Override
    public Syntax getBackslashSyntax(@NonNull RxMDConfiguration rxMDConfiguration) {
        return new BackslashSyntax(rxMDConfiguration);
    }

    private void init(@NonNull RxMDConfiguration rxMDConfiguration) {
        mRxMDConfiguration = rxMDConfiguration;
        mTotalChain = new MultiSyntaxChain(
                getCodeSyntax(rxMDConfiguration),
                getUnOrderListSyntax(rxMDConfiguration),
                getOrderListSyntax(rxMDConfiguration));
        mLineChain = new SyntaxChain(getHorizontalRulesSyntax(rxMDConfiguration));
        SyntaxDoElseChain blockQuitesChain = new SyntaxDoElseChain(getBlockQuotesSyntax(rxMDConfiguration));
        SyntaxDoElseChain todoChain = new SyntaxDoElseChain(getTodoSyntax(rxMDConfiguration));
        SyntaxDoElseChain todoDoneChain = new SyntaxDoElseChain(getTodoDoneSyntax(rxMDConfiguration));
        SyntaxMultiChains centerAlignChain = new SyntaxMultiChains(getCenterAlignSyntax(rxMDConfiguration));
        SyntaxMultiChains headerChain = new SyntaxMultiChains(getHeaderSyntax(rxMDConfiguration));
        MultiSyntaxChain multiChain = new MultiSyntaxChain(
                getImageSyntax(rxMDConfiguration),
                getHyperLinkSyntax(rxMDConfiguration),
                getInlineCodeSyntax(rxMDConfiguration),
                getBoldSyntax(rxMDConfiguration),
                getItalicSyntax(rxMDConfiguration),
                getStrikeThroughSyntax(rxMDConfiguration),
                getFootnoteSyntax(rxMDConfiguration));
        SyntaxChain backslashChain = new SyntaxChain(getBackslashSyntax(rxMDConfiguration));

        mLineChain.setNextHandleSyntax(blockQuitesChain);

        blockQuitesChain.setNextHandleSyntax(todoChain);
        blockQuitesChain.addNextHandleSyntax(multiChain);

        todoChain.setNextHandleSyntax(todoDoneChain);
        todoChain.addNextHandleSyntax(multiChain);

        todoDoneChain.setNextHandleSyntax(centerAlignChain);
        todoDoneChain.addNextHandleSyntax(multiChain);

        centerAlignChain.addNextHandleSyntax(headerChain);
        centerAlignChain.addNextHandleSyntax(multiChain);

        multiChain.setNextHandleSyntax(backslashChain);
    }

    @NonNull
    @Override
    public CharSequence parse(@NonNull CharSequence charSequence, @NonNull RxMDConfiguration rxMDConfiguration) {
        if (rxMDConfiguration == null) {
            return charSequence;
        }
        if (mTotalChain == null || mLineChain == null || mRxMDConfiguration == null || mRxMDConfiguration != rxMDConfiguration) {
            init(rxMDConfiguration);
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(charSequence);
        ssb = parseTotal(mTotalChain, ssb);
        ssb = parseByLine(mLineChain, ssb);
        return ssb;
    }

    private SpannableStringBuilder parseTotal(ISpecialChain totalChain, SpannableStringBuilder ssb) {
        totalChain.handleSyntax(ssb);
        return ssb;
    }

    private SpannableStringBuilder parseByLine(ISpecialChain lineChain, SpannableStringBuilder content) {
        String text = content.toString();
        String[] lines = text.split("\n");
        SpannableStringBuilder[] ssbLines = new SpannableStringBuilder[lines.length];
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int index = 0;
        for (int i = 0; i < lines.length; i++) {
            ssbLines[i] = (SpannableStringBuilder) content.subSequence(index, index + lines[i].length());
            lineChain.handleSyntax(ssbLines[i]);
            index += (lines[i]).length();
            if (i < lines.length - 1) {
                ssbLines[i].append(NEWLINE);
                index += NEWLINE.length();
            }
            ssb.append(ssbLines[i]);
        }
        return ssb;
    }

}
