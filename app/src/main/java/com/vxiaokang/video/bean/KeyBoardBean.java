package com.vxiaokang.video.bean;

/**
 * 快捷键
 */
public class KeyBoardBean {
    private String keyName;
    private String keyContent;
    public KeyBoardBean(){}
    public KeyBoardBean(String keyName,String keyContent){
        this.keyContent = keyContent;
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
    public String getKeyContent() {
        return keyContent;
    }
    public void setKeyContent(String keyContent) {
        this.keyContent = keyContent;
    }
}
