package com.vxiaokang.video.bean;

import java.util.List;

public class ConfigDataBean {

    /**
     * code : 0
     * msg : success
     * data : [{"configName":"客服QQ","configKey":"app.qq","configValue":"123456789","remark":"客服QQ"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * configName : 客服QQ
         * configKey : app.qq
         * configValue : 123456789
         * remark : 客服QQ
         */

        private String configName;
        private String configKey;
        private String configValue;
        private String remark;

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }

        public String getConfigKey() {
            return configKey;
        }

        public void setConfigKey(String configKey) {
            this.configKey = configKey;
        }

        public String getConfigValue() {
            return configValue;
        }

        public void setConfigValue(String configValue) {
            this.configValue = configValue;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
