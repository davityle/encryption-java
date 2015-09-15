package com.github.davityle.encrypt.aes;

public class Galois {

    public static byte finiteFieldAdd(byte a, byte b) {
        return (byte) (a ^ b);
    }

    public static byte finiteFieldMultiply(byte a, byte b) {
        byte ans = 0;
        for(int i = 0; i < 8; i++) {
            if(hasBit(b, i)) {
                ans = finiteFieldAdd(ans, a);
            }
            a = xTime(a);
        }
        return ans;
    }

    private static byte xTime(byte b) {
        boolean overflow = (b & 0x80) == 0x80;
        b <<= 1;
        if(overflow)
            return (byte) (b ^ 0x1b);
        else
            return b;
    }

    private static boolean hasBit(byte b, int position) {
        return ((b >> position) & 1) == 1;
    }
}
