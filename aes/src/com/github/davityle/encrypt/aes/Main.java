package com.github.davityle.encrypt.aes;

public class Main {

    public static void main(String[] args) {

        byte[] cipherKey128 = new byte[] {
            (byte)0x2b,
            (byte)0x7e,
            (byte)0x15,
            (byte)0x16,
            (byte)0x28,
            (byte)0xae,
            (byte)0xd2,
            (byte)0xa6,
            (byte)0xab,
            (byte)0xf7,
            (byte)0x15,
            (byte)0x88,
            (byte)0x09,
            (byte)0xcf,
            (byte)0x4f,
            (byte)0x3c
        };

        printWords(new KeyExpansion(cipherKey128).expandKey());

        byte[] cipherKey192 = new byte[] {
            (byte)0x8e,
            (byte)0x73,
            (byte)0xb0,
            (byte)0xf7,
            (byte)0xda,
            (byte)0x0e,
            (byte)0x64,
            (byte)0x52,
            (byte)0xc8,
            (byte)0x10,
            (byte)0xf3,
            (byte)0x2b,
            (byte)0x80,
            (byte)0x90,
            (byte)0x79,
            (byte)0xe5,
            (byte)0x62,
            (byte)0xf8,
            (byte)0xea,
            (byte)0xd2,
            (byte)0x52,
            (byte)0x2c,
            (byte)0x6b,
            (byte)0x7b
        };

        printWords(new KeyExpansion(cipherKey192).expandKey());


        byte[] cipherKey256 = new byte[] {
            (byte)0x60,
            (byte)0x3d,
            (byte)0xeb,
            (byte)0x10,
            (byte)0x15,
            (byte)0xca,
            (byte)0x71,
            (byte)0xbe,
            (byte)0x2b,
            (byte)0x73,
            (byte)0xae,
            (byte)0xf0,
            (byte)0x85,
            (byte)0x7d,
            (byte)0x77,
            (byte)0x81,
            (byte)0x1f,
            (byte)0x35,
            (byte)0x2c,
            (byte)0x07,
            (byte)0x3b,
            (byte)0x61,
            (byte)0x08,
            (byte)0xd7,
            (byte)0x2d,
            (byte)0x98,
            (byte)0x10,
            (byte)0xa3,
            (byte)0x09,
            (byte)0x14,
            (byte)0xdf,
            (byte)0xf4
        };

        printWords(new KeyExpansion(cipherKey256).expandKey());


        byte[] input0 = new byte[] {
            (byte)0x32,
            (byte)0x43,
            (byte)0xf6,
            (byte)0xa8,
            (byte)0x88,
            (byte)0x5a,
            (byte)0x30,
            (byte)0x8d,
            (byte)0x31,
            (byte)0x31,
            (byte)0x98,
            (byte)0xa2,
            (byte)0xe0,
            (byte)0x37,
            (byte)0x07,
            (byte)0x34
        };

        byte[] key0 = new byte[] {
            (byte)0x2b,
            (byte)0x7e,
            (byte)0x15,
            (byte)0x16,
            (byte)0x28,
            (byte)0xae,
            (byte)0xd2,
            (byte)0xa6,
            (byte)0xab,
            (byte)0xf7,
            (byte)0x15,
            (byte)0x88,
            (byte)0x09,
            (byte)0xcf,
            (byte)0x4f,
            (byte)0x3c
        };

        System.out.println(new AESCipher(input0, key0).encode());
        System.out.println();

        byte[] input1 = new byte[] {
            (byte)0x00,
            (byte)0x11,
            (byte)0x22,
            (byte)0x33,
            (byte)0x44,
            (byte)0x55,
            (byte)0x66,
            (byte)0x77,
            (byte)0x88,
            (byte)0x99,
            (byte)0xaa,
            (byte)0xbb,
            (byte)0xcc,
            (byte)0xdd,
            (byte)0xee,
            (byte)0xff
        };

        byte[] key1 = new byte[] {
            (byte)0x00,
            (byte)0x01,
            (byte)0x02,
            (byte)0x03,
            (byte)0x04,
            (byte)0x05,
            (byte)0x06,
            (byte)0x07,
            (byte)0x08,
            (byte)0x09,
            (byte)0x0a,
            (byte)0x0b,
            (byte)0x0c,
            (byte)0x0d,
            (byte)0x0e,
            (byte)0x0f
        };

        AESCipher cipher1 = new AESCipher(input1, key1);
        System.out.println(cipher1.encode());
        System.out.println(cipher1.decode());
        System.out.println();

        byte[] input2 = input1;
        byte[] key2 = new byte[] {
            (byte)0x00,
            (byte)0x01,
            (byte)0x02,
            (byte)0x03,
            (byte)0x04,
            (byte)0x05,
            (byte)0x06,
            (byte)0x07,
            (byte)0x08,
            (byte)0x09,
            (byte)0x0a,
            (byte)0x0b,
            (byte)0x0c,
            (byte)0x0d,
            (byte)0x0e,
            (byte)0x0f,
            (byte)0x10,
            (byte)0x11,
            (byte)0x12,
            (byte)0x13,
            (byte)0x14,
            (byte)0x15,
            (byte)0x16,
            (byte)0x17
        };

        AESCipher cipher2 = new AESCipher(input2, key2);
        System.out.println(cipher2.encode());
        System.out.println(cipher2.decode());
        System.out.println();

        byte[] input3 = input1;
        byte[] key3 = new byte[] {
            (byte)0x00,
            (byte)0x01,
            (byte)0x02,
            (byte)0x03,
            (byte)0x04,
            (byte)0x05,
            (byte)0x06,
            (byte)0x07,
            (byte)0x08,
            (byte)0x09,
            (byte)0x0a,
            (byte)0x0b,
            (byte)0x0c,
            (byte)0x0d,
            (byte)0x0e,
            (byte)0x0f,
            (byte)0x10,
            (byte)0x11,
            (byte)0x12,
            (byte)0x13,
            (byte)0x14,
            (byte)0x15,
            (byte)0x16,
            (byte)0x17,
            (byte)0x18,
            (byte)0x19,
            (byte)0x1a,
            (byte)0x1b,
            (byte)0x1c,
            (byte)0x1d,
            (byte)0x1e,
            (byte)0x1f
        };

        AESCipher cipher3 = new AESCipher(input3, key3);
        System.out.println(cipher3.encode());
        System.out.println(cipher3.decode());
        System.out.println();
    }

    private static void printWords(Word[] words) {
        for(int i = 0; i < words.length; i++) {
            System.out.println(i + ": " + words[i]);
        }
        System.out.println();
    }

}
