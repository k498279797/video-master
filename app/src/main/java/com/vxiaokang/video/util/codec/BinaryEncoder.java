package com.vxiaokang.video.util.codec;
public interface BinaryEncoder extends Encoder {
    byte[] encode(byte[] source) throws EncoderException;
}
