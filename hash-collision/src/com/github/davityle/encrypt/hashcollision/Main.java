package com.github.davityle.encrypt.hashcollision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Main {

    private static final MessageDigest sha1;

    static {
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String arg[]) {
        String original = UUID.randomUUID().toString();
        System.out.println(original);
        System.out.println(findCollision(encrypt24bit(original)));
        System.out.println(findCollision(getUserHash()));
    }

    private static String findCollision(int initial) {
        String collision;
        int count = 0;
        do {
            collision = UUID.randomUUID().toString();
            count++;
        } while (initial != encrypt24bit(collision));
        System.out.println(count);
        return collision;
    }

    private static int encrypt24bit(String x) {
        sha1.reset();
        sha1.update(x.getBytes());
        byte[] bytes = sha1.digest();
        return (((bytes[0] & 0xff) << 16) | ((bytes[1] & 0xff) <<  8) | (bytes[2] & 0xff)) & 0x00ffffff;
    }


    private static int getUserHash() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter Hex Hash value: ");
        try {
            return Integer.parseInt(br.readLine().trim(), 16);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
