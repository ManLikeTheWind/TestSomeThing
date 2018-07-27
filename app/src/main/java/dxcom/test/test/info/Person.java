package dxcom.test.test.info;

/**
 * package: dxcom.test.test.info
 * <br> author: dongxiang
 * <br>   date: 2018/4/28  10:36
 * <br>  descrp:
 * <br>  using:
 * <br>  e-mail:dongxiang_android_sdk@aliyun.com
 */

public class Person {
    private int ID;
    private String NAME;
    /** _unique*/
    private String PERSON_ID;

    public Person() {
    }

    public Person(String NAME, String PERSON_ID) {
        this.NAME = NAME;
        this.PERSON_ID = PERSON_ID;
    }

    public Person(int ID, String NAME, String PERSON_ID) {
        this.ID = ID;
        this.NAME = NAME;
        this.PERSON_ID = PERSON_ID;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", NAME='" + NAME + '\'' +
                ", PERSON_ID='" + PERSON_ID + '\'' +
                '}';
    }
}
