package com.github.davityle.encrypt.aes;

/**
 *
 */
public class Rounds {

    public static int getNumberOfRounds(int wordsInKey) {
        if(wordsInKey == 4 || wordsInKey == 6 || wordsInKey == 8)
            return 6 + wordsInKey;
        throw new RuntimeException("Invalid Key Length " + wordsInKey);
    }

}
