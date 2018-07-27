package dxcom.test.test.info;

/**
 * package: dxcom.test.test.info
 * <br> author: dongxiang
 * <br>   date: 2018/4/25  9:04
 * <br>  descrp:
 * <br>  using:
 * <br>  e-mail:dongxiang_android_sdk@aliyun.com
 */

public class Company {

    private String NAME;
    private Integer AGE;
    private String ADDRESS;
    private Integer SALARY;

    public Company(String NAME, Integer AGE, String ADDRESS, Integer SALARY) {
        this.NAME = NAME;
        this.AGE = AGE;
        this.ADDRESS = ADDRESS;
        this.SALARY = SALARY;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Integer getAGE() {
        return AGE;
    }

    public void setAGE(Integer AGE) {
        this.AGE = AGE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public Integer getSALARY() {
        return SALARY;
    }

    public void setSALARY(Integer SALARY) {
        this.SALARY = SALARY;
    }
}
