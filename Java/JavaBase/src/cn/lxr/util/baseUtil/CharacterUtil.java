package cn.lxr.util.baseUtil;

public class CharacterUtil {

	/**
	 * 字符串长度
	 * 中文及字符占2位其余占1位
	 */
	public static int fullLength(String str) {
		int length = 0;
		if (str == null) {
			return length;
		}
		char[] charArray = str.toCharArray();
		for (char c : charArray) {
			if (isChinese(c) || isFullWidthPunctuation(c)) {
				length += 2;
			} else {
				length ++;
			}
		}
		return length;
	}
	
	/**
	 * 是否是中文字符
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        if (sc == Character.UnicodeScript.HAN) {
            return true;
        }
        return false;
    }
    
	/**
	 * 是否是标点符号
	 * @param c
	 * @return
	 */
    public static boolean isPunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (    // punctuation, spacing, and formatting characters
                ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                // symbols and punctuation in the unified Chinese, Japanese and Korean script
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                // fullwidth character or a halfwidth character
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                // vertical glyph variants for east Asian compatibility
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                // vertical punctuation for compatibility characters with the Chinese Standard GB 18030
                || ub == Character.UnicodeBlock.VERTICAL_FORMS
                // ascii
                || ub == Character.UnicodeBlock.BASIC_LATIN
                ) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 是否是占两位字符的标点符号
     * @param c
     * @return
     */
    public static boolean isFullWidthPunctuation(char c) {
    	Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    	if (    // symbols and punctuation in the unified Chinese, Japanese and Korean script
    			ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
    			// fullwidth character or a halfwidth character
    			|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
    			// vertical glyph variants for east Asian compatibility
    			|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
    			// vertical punctuation for compatibility characters with the Chinese Standard GB 18030
    			|| ub == Character.UnicodeBlock.VERTICAL_FORMS
    			) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public static void main(String[] args) {
		System.out.println(CharacterUtil.isChinese('の'));
		System.out.println(CharacterUtil.isChinese('아'));
		System.out.println(CharacterUtil.isPunctuation('。'));
		System.out.println(CharacterUtil.isPunctuation('．'));
		System.out.println(CharacterUtil.isPunctuation('？'));
		System.out.println(CharacterUtil.isPunctuation('？'));
		System.out.println(CharacterUtil.isPunctuation('＄'));
		System.out.println(CharacterUtil.isPunctuation('×'));
		System.out.println(CharacterUtil.isPunctuation('②'));
	}
}
