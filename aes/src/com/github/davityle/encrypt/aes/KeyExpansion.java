package com.github.davityle.encrypt.aes;

/**
 *
 */
public class KeyExpansion {

    private final Word[] key;

    public KeyExpansion(byte[] key) {
        this(keyToWords(key));
    }

    public KeyExpansion(Word[] key) {
        this.key = key;
    }

    public Word[] expandKey() {
        Word[] words = new Word[4 * (Rounds.getNumberOfRounds(key.length) + 1)];

        for(int i = 0; i < key.length; i++) {
            words[i] = new Word(key[i]);
        }

        for(int i = key.length; i < words.length; i++) {
            Word temp = new Word(words[i - 1]);
            if(i % key.length == 0) {
                temp = new Word(temp.rotateWord().substituteWord().word() ^ RoundConstant.getConstant(i / key.length));
            } else if(key.length > 6 && i % key.length == 4){
                temp = temp.substituteWord();
            }
            words[i] = new Word(words[i - key.length].word() ^ temp.word());
        }

        return words;
    }

    private static Word[] keyToWords(byte[] cipherKey) {
        final Word[] key = new Word[cipherKey.length /4];
        for(int i = 0; i < key.length; i++) {
            int keyIndex = i*4;
            key[i] = new Word(cipherKey[keyIndex], cipherKey[keyIndex + 1], cipherKey[keyIndex + 2], cipherKey[keyIndex + 3]);
        }
        return key;
    }
}
