package com.github.davityle.encrypt.aes;

public class Word {

    private final byte[] data;

    public Word(Word word) {
        this(word.data);
    }

    public Word(byte[] data) {
        this(data[0], data[1], data[2], data[3]);
    }

    public Word(int word) {
        this((byte) (word >> 24), (byte) (word >> 16), (byte) (word >> 8), (byte) word);
    }

    public Word(byte first, byte second, byte third, byte fourth) {
        this.data = new byte[] {first, second, third, fourth};
    }

    public int word() {
        return (first() << 24) | ((second() & 0xff) << 16) | ((third() & 0xff) <<  8) | (fourth() & 0xff);
    }

    public byte first() {
        return data[0];
    }

    public byte second() {
        return data[1];
    }

    public byte third() {
        return data[2];
    }

    public byte fourth() {
        return data[3];
    }

    public Word substituteWord() {
        return new Word(subByte(first()), subByte(second()), subByte(third()), subByte(fourth()));
    }

    private byte subByte(byte sub) {
        return SubstitutionBox.getSubstitution(sub);
    }

    public Word rotateWord() {
        return new Word(second(), third(), fourth(), first());
    }

    @Override
    public String toString() {
        return Integer.toHexString(word());
    }

}
