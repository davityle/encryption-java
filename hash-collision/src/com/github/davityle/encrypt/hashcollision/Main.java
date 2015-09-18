package com.github.davityle.encrypt.hashcollision;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

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
        findAnyCollision(Main::encrypt8bit);
        findAnyCollision(Main::encrypt16bit);
        findAnyCollision(Main::encrypt24bit);
        findAnyCollision(Main::encrypt32bit);
        findCollision(UUID.randomUUID().toString(), Main::encrypt24bit);
    }

    private static void findAnyCollision(Function<String, Integer> encrypt) {
        long startTime = System.currentTimeMillis();
        HashMap<Integer, String> map = new HashMap<>();
        String collision;
        int count = 0, hash;
        while(true){
            collision = UUID.randomUUID().toString();
            count++;
            hash = encrypt.apply(collision);
            if(map.containsKey(hash)) {
                break;
            }
            map.put(hash, collision);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(map.get(hash));
        System.out.println(collision);
        System.out.println(Integer.toHexString(hash));
        System.out.println(count);
        System.out.println(endTime - startTime);
    }

    private static void findCollision(String initial, Function<String, Integer> encrypt) {
        long startTime = System.currentTimeMillis();
        String collision;
        int count = 0, hash = encrypt.apply(initial);
        do {
            collision = UUID.randomUUID().toString();
            count++;
        } while (hash != encrypt.apply(collision));
        long endTime = System.currentTimeMillis();
        System.out.println(initial);
        System.out.println(collision);
        System.out.println(count);
        System.out.println(endTime - startTime);
    }

    private static byte[] encrypt(String x) {
        sha1.reset();
        sha1.update(x.getBytes());
        return sha1.digest();
    }

    // it would've been simpler to make each of these with a loop
    private static int encrypt8bit(String x) {
        return encrypt(x)[0] & 0xff;
    }

    private static int encrypt16bit(String x) {
        byte[] bytes = encrypt(x);
        return (((bytes[0] & 0xff) <<  8) | (bytes[1] & 0xff)) & 0x0000ffff;
    }

    private static int encrypt24bit(String x) {
        byte[] bytes = encrypt(x);
        return (((bytes[0] & 0xff) << 16) | ((bytes[1] & 0xff) <<  8) | (bytes[2] & 0xff)) & 0x00ffffff;
    }

    private static int encrypt32bit(String x) {
        byte[] bytes = encrypt(x);
        return (((bytes[0] & 0xff) << 24) | ((bytes[1] & 0xff) <<  16) | ((bytes[2] & 0xff) << 8) | ((bytes[3] & 0xff))) ;
    }
}
