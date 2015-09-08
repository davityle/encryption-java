package com.github.davityle.encrypt.aes;

import javax.xml.bind.DatatypeConverter;
import java.util.function.Consumer;
import java.util.function.Function;

public class AESCipher {

    private final static int COLUMN_COUNT = 4;

    private final byte[][] state;
    private final Word[] cipherKey;
    private final int numberOfRounds;

    public AESCipher(byte[] block, byte[] cipherKey) {
        this.state = new byte[COLUMN_COUNT][COLUMN_COUNT];
        this.numberOfRounds = Rounds.getNumberOfRounds(cipherKey.length/4);
        this.cipherKey = new KeyExpansion(cipherKey).expandKey();

        for(int i = 0; i < COLUMN_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                state[i][j] = block[j*COLUMN_COUNT + i];
            }
        }
    }

    public AESCipher encode() {
        addRoundKey(0);
        for(int i = 1; i < numberOfRounds; i++) {
            substituteBytes(SubstitutionBox::getSubstitution);
            shiftRows(this::shiftRow);
            mixColumns();
            addRoundKey(i);
        }

        substituteBytes(SubstitutionBox::getSubstitution);
        shiftRows(this::shiftRow);
        addRoundKey(numberOfRounds);

        return this;
    }

    public AESCipher decode() {

        addRoundKey(numberOfRounds);
        for(int i = numberOfRounds - 1; i > 0; i--) {
            shiftRows(this::inverseShiftRow);
            substituteBytes(SubstitutionBox::getInverseSubstitution);
            addRoundKey(i);
            inverseMixColumns();
        }

        shiftRows(this::inverseShiftRow);
        substituteBytes(SubstitutionBox::getInverseSubstitution);
        addRoundKey(0);

        return this;
    }

    private void addRoundKey(int round) {
        for(int c = 0; c < COLUMN_COUNT; c++) {
            Word newWord = new Word(new Word(state[0][c], state[1][c], state[2][c], state[3][c]).word() ^ cipherKey[round * 4 + c].word());
            state[0][c] = newWord.first();
            state[1][c] = newWord.second();
            state[2][c] = newWord.third();
            state[3][c] = newWord.fourth();
        }
    }

    private void substituteBytes(Function<Byte, Byte> sub) {
        for(int i = 0; i < state.length; i++) {
            for(int j = 0; j < state[i].length; j++) {
                state[j][i] = sub.apply(state[j][i]);
            }
        }
    }

    private void shiftRows(Consumer<byte[]> inv) {
        for(int i = 1; i < state.length; i++) {
            for(int j = 0; j < i; j++) {
                inv.accept(state[i]);
            }
        }
    }

    private void shiftRow(byte[] row) {
        byte first = row[0];
        row[0] = row[1];
        row[1] = row[2];
        row[2] = row[3];
        row[3] = first;
    }

    private void inverseShiftRow(byte[] row) {
        byte last = row[3];
        row[3] = row[2];
        row[2] = row[1];
        row[1] = row[0];
        row[0] = last;
    }

    private void mixColumns() {
        final byte[] row0 = state[0].clone(), row1 = state[1].clone(), row2 = state[2].clone(), row3 = state[3].clone();
        for (int c = 0; c < COLUMN_COUNT; c++) {
            Function<byte[], Byte> m2 = partialApplyFfm((byte) 0x02, c);
            Function<byte[], Byte> m3 = partialApplyFfm((byte) 0x03, c);
            state[0][c] = (byte) (m2.apply(row0) ^ m3.apply(row1) ^ row2[c] ^ row3[c]);
            state[1][c] = (byte) (row0[c] ^ m2.apply(row1) ^ m3.apply(row2) ^ row3[c]);
            state[2][c] = (byte) (row0[c] ^ row1[c] ^ m2.apply(row2) ^ m3.apply(row3));
            state[3][c] = (byte) (m3.apply(row0) ^ row1[c] ^ row2[c] ^ m2.apply(row3));
        }
    }

    private void inverseMixColumns() {
        final byte[] row0 = state[0].clone(), row1 = state[1].clone(), row2 = state[2].clone(), row3 = state[3].clone();
        for (int c = 0; c < COLUMN_COUNT; c++) {
            Function<byte[], Byte> me = partialApplyFfm((byte) 0x0e, c);
            Function<byte[], Byte> mb = partialApplyFfm((byte) 0x0b, c);
            Function<byte[], Byte> md = partialApplyFfm((byte) 0x0d, c);
            Function<byte[], Byte> m9 = partialApplyFfm((byte) 0x09, c);
            state[0][c] = (byte) (me.apply(row0) ^ mb.apply(row1) ^ md.apply(row2) ^ m9.apply(row3));
            state[1][c] = (byte) (m9.apply(row0) ^ me.apply(row1) ^ mb.apply(row2) ^ md.apply(row3));
            state[2][c] = (byte) (md.apply(row0) ^ m9.apply(row1) ^ me.apply(row2) ^ mb.apply(row3));
            state[3][c] = (byte) (mb.apply(row0) ^ md.apply(row1) ^ m9.apply(row2) ^ me.apply(row3));
        }
    }

    private Function<byte[], Byte> partialApplyFfm(byte num, int col) {
        return row -> Galois.finiteFieldMultiply(num, row[col]);
    }

    public byte[] getBytes() {
        byte[] out = new byte[COLUMN_COUNT * COLUMN_COUNT];
        for(int i = 0; i < COLUMN_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                out[j*COLUMN_COUNT + i] = state[i][j];
            }
        }
        return out;
    }

    @Override
    public String toString() {
        return DatatypeConverter.printHexBinary(getBytes());
    }
}
