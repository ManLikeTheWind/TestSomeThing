package dxcom.test.test.info;

/**
 * package: dxcom.test.test.info
 * <br> author: dongxiang
 * <br>   date: 2018/4/25  9:05
 * <br>  descrp:
 * <br>  using:
 * <br>  e-mail:dongxiang_android_sdk@aliyun.com
 */

public class Department {
    private String DEPT;
    private Integer EMP_ID;
    private String NAME;
    private long ID;

    public Department(String DEPT, Integer EMP_ID, String NAME) {
        this.DEPT = DEPT;
        this.EMP_ID = EMP_ID;
        this.NAME = NAME;
    }

    public Department(String DEPT, Integer EMP_ID, String NAME, long ID) {
        this.DEPT = DEPT;
        this.EMP_ID = EMP_ID;
        this.NAME = NAME;
        this.ID = ID;
    }

    public String getDEPT() {
        return DEPT;
    }

    public void setDEPT(String DEPT) {
        this.DEPT = DEPT;
    }

    public Integer getEMP_ID() {
        return EMP_ID;
    }

    public void setEMP_ID(Integer EMP_ID) {
        this.EMP_ID = EMP_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return "Department{" +
                " ID=" + ID +
                ",DEPT='" + DEPT + '\'' +
                ", EMP_ID=" + EMP_ID +
                ", NAME='" + NAME + '\'' +
                '}';
    }
}
