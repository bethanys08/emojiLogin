package com.example.emojiLogin;

import android.text.InputFilter;
import android.text.Spanned;

public class EmojiInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            char currentChar = source.charAt(i);
            if (isCharAllowed(currentChar)) {
                builder.append(currentChar);
            }
        }
        return builder.toString();
    }

    private boolean isCharAllowed(char c) {
        // Allow emojis and non-control characters
        return !Character.isISOControl(c) && c != 0xFFFD;
    }
}
