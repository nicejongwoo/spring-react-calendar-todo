package nice.jongwoo.common;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {
    private static final int TOKEN_LENGTH = 20;

    //총20자의 특정 접두사로 시작하는 랜덤 문자열 반환
    public static String randomCharacterWithPrefix(String prefix) {
        return prefix + randomCharacter(TOKEN_LENGTH - prefix.length());
    }

    //지정한 길이만큼의 랜덤 문자열 생성
    private static String randomCharacter(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

}
