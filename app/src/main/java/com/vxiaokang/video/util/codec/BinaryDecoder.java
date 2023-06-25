package com.vxiaokang.video.util.codec;

public interface BinaryDecoder extends Decoder{
    byte[] decode(byte[] source) throws DecoderException;
}
