package com.android.lixiang.nongbao.presenter.data.bean;

public class LoginInfoBean {
    /**
     * userInfo : {"passWord":"E10ADC3949BA59ABBE56E057F20F883E","role_name":"admin","collect_area":"","role_id":1,"manage_area":"","loginName":"admin","sex":"","name":"超级管理员","remark":"there is no remark","telephone":"15764304339","id":1}
     * message : 登录成功
     * status : 200
     */

    private UserInfoBean userInfo;
    private String message;
    private int status;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class UserInfoBean {
        /**
         * passWord : E10ADC3949BA59ABBE56E057F20F883E
         * role_name : admin
         * collect_area :
         * role_id : 1
         * manage_area :
         * loginName : admin
         * sex :
         * name : 超级管理员
         * remark : there is no remark
         * telephone : 15764304339
         * id : 1
         */

        private String passWord;
        private String role_name;
        private String collect_area;
        private int role_id;
        private String manage_area;
        private String loginName;
        private String sex;
        private String name;
        private String remark;
        private String telephone;
        private int id;

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public String getCollect_area() {
            return collect_area;
        }

        public void setCollect_area(String collect_area) {
            this.collect_area = collect_area;
        }

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }

        public String getManage_area() {
            return manage_area;
        }

        public void setManage_area(String manage_area) {
            this.manage_area = manage_area;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
