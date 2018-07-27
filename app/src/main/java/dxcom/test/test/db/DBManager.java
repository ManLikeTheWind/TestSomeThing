package dxcom.test.test.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import dxcom.test.test.info.Company;
import dxcom.test.test.info.Department;

/**
 * package: dxcom.test.test.db
 * <br> author: dongxiang
 * <br>   date: 2018/4/23  15:54
 * <br>  descrp:
 * <br>  using:
 * <br>  e-mail:dongxiang_android_sdk@aliyun.com
 */

public class DBManager {
    public static final String TAG=DBManager.class.getSimpleName();
    private static Lock lock=new ReentrantLock(true);

    private static DBManager dbManager;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager() {
        dbHelper=new DBHelper();
        db=dbHelper.getWritableDatabase();
    }

    public static DBManager get(){
        if (dbManager==null){
            lock.lock();
            if (dbManager==null){
            dbManager=new DBManager();
            }
            lock.unlock();
        }
        return dbManager;
    }

    public long insert(String tableName, String nullColumnHack, ContentValues values){
        long longId=-1;
        db.beginTransaction();
        try {
            longId=db.insert(tableName, nullColumnHack, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, ":insert", e);
        } finally {
            if (null != db) {
                db.endTransaction();
            }
        }
        return longId;
    }


    public long replace(String tableName, String nullColumnHack, ContentValues values){
        long longId=-1;
        try{
            db.beginTransaction();

            longId=db.replace(tableName, nullColumnHack, values);

            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(TAG, "replace: values = "+values+";\n",e );
        }finally {
            db.endTransaction();
        }
        return longId;
    }

    public ArrayList<Long> replaceBatch(String tableName, String nullColumnHack, List<ContentValues> valuesList){
        ArrayList<Long> result=new ArrayList<>();
        int index=-1;
        long autoID=-1;
        try {
            db.beginTransaction();

            for (index=0;index<valuesList.size();index++){
                autoID=db.replace(tableName,nullColumnHack,valuesList.get(index));
                result.add(autoID);
                if (index % 100 == 0) {//1.1这里异常跳出来，
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.beginTransaction();
                }
            }

            if (valuesList.size()%100!=0||valuesList.size()==0){//1.2.正常结束，不等于100或者等于0；
                db.setTransactionSuccessful();
            }
        }catch (Exception e){
            Log.e(TAG,Thread.currentThread()+"----replaceBatch---index = "+index+"\n  ",e);
        }finally {
            if (valuesList.size()!=index){//2.1 这个时候就是报异常了，
                db.setTransactionSuccessful();
            }
             db.endTransaction();//2.2 正常结束+不正常结束
        }
        return result;
    }






    public long insertTest(){
        ContentValues values=new ContentValues();

        values.put(DBConstansts.TABLE_TEST_hanzi_nocollate, "hanzi_nocollate");
        values.put(DBConstansts.TABLE_TEST_hanzi_collate_nocase, "hanzi_collate_nocase");
        values.put(DBConstansts.TABLE_TEST_hanzi_collate_rtrim, "hanzi_collate_rtrim");
        values.put(DBConstansts.TABLE_TEST_hanzi_collate_binary, "hanzi_collate_binary");
        values.put(DBConstansts.TABLE_TEST_hanzi_nocollate, "hanzi_nocollate2");
        long longId= insert(DBConstansts.TABLE_TEST, null, values);
        return longId;
    }



    public long insertCompany(){

        ArrayList<Company>companies=new ArrayList<>();
        companies.add(new Company("Company_Paul",32,"California",20000));
        companies.add(new Company("Company_Allen",25,"Texas",15000));
        companies.add(new Company("Company_Teddy",23,"Norway",20000));
        companies.add(new Company("Company_Mark",27,"Rich-Mond",65000));
        companies.add(new Company("Company_David",32,"Texas",85000));
        companies.add(new Company("Company_Kim",22,"South-Hall",45000));
        companies.add(new Company("Company_James",24,"Houston",10000));

        ContentValues values=new ContentValues();
        long longId=-1;
        for (int i=0;i<companies.size();i++){
            values.clear();
            values.put(DBConstansts.TABLE_COMPANY_NAME, companies.get(i).getNAME());
            values.put(DBConstansts.TABLE_COMPANY_AGE, companies.get(i).getAGE());//integer
            values.put(DBConstansts.TABLE_COMPANY_ADDRESS, companies.get(i).getADDRESS());
            values.put(DBConstansts.TABLE_COMPANY_SALARY, companies.get(i).getSALARY());//integer
             longId= insert(DBConstansts.TABLE_COMPANY_inner_join_COMPANY, null, values);
        }
        return longId;
    }
    public ArrayList<Long> insertDepartment(){
        ArrayList<Long> result=new ArrayList<>();
        ArrayList<Department>departments=new ArrayList<>();
        departments.add(new Department("IT Billing",1,"Department-Paul"));
        result.add(-1l);
        departments.add(new Department("Engineerin",2,"Department-Allen"));
        result.add(-1l);
        departments.add(new Department("Finance",7,"Department-James"));
        result.add(-1l);


        ContentValues values=new ContentValues();
        for (int i=0;i<departments.size();i++){
            values.clear();
            values.put(DBConstansts.TABLE_DEPARTMENT_DEPT, departments.get(i).getDEPT());
            values.put(DBConstansts.TABLE_DEPARTMENT_EMP_ID, departments.get(i).getEMP_ID());//integer
            values.put(DBConstansts.TABLE_DEPARTMENT_NAME, departments.get(i).getNAME());
            long longId= replace(DBConstansts.TABLE_DEPARTMENT_inner_join_DEPARTMENT, null, values);
            result.add(i,longId);
        }
        return result;
    }
    public int queryDepartment(){
        int cursorCount=-1;
        Cursor cursorD = null;
        String sql_CD_All = "SELECT *  FROM inner_join_DEPARTMENT";
        cursorD = db.rawQuery(sql_CD_All, null);
        cursorCount=cursorD.getCount();

        int columnIndex_ID = cursorD.getColumnIndex(DBConstansts.ID);
        int columnIndex_DEPT = cursorD.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_DEPT);
        int columnIndex_EMP_ID = cursorD.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_EMP_ID);
        int columnIndex_NAME = cursorD.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_NAME);

        ArrayList<Department> departments = new ArrayList<>();
        while (cursorD.moveToNext()){
            String NAME=cursorD.getString(columnIndex_NAME);
            int EMP_ID=cursorD.getInt(columnIndex_EMP_ID);
            String DEPT=cursorD.getString(columnIndex_DEPT);
            long ID=cursorD.getLong(columnIndex_ID);
            departments.add(new Department( DEPT,  EMP_ID,  NAME,  ID));
        }

        Log.e(TAG,"queryDepartMent cursorCount = "+cursorCount+"; departments.toString = "+ departments);
        return cursorCount;
    }





    public ArrayList<HashMap<String, String>> queryCompanyDepartment(){
        ArrayList<HashMap<String, String>> lists = new ArrayList<>();
        HashMap<String, String> maps;
        Cursor cursorC = null;
        String sql_C = "SELECT * FROM inner_join_COMPANY\n" ;
        cursorC = db.rawQuery(sql_C, null);
        int count_C=cursorC.getCount();

        Cursor cursorD = null;
        String sql_D = "SELECT * FROM inner_join_DEPARTMENT";
        cursorD = db.rawQuery(sql_D, null);
        int count_D=cursorD.getCount();


        Cursor cursorCD_All = null;
        String sql_CD_All = "SELECT *\n" +
                "  FROM inner_join_COMPANY\n" +
                "       INNER JOIN\n" +
                "       inner_join_DEPARTMENT ON inner_join_COMPANY.ID = inner_join_DEPARTMENT.EMP_ID;";
        cursorCD_All = db.rawQuery(sql_CD_All, null);
        int count_CD_All=cursorCD_All.getCount();

        Cursor cursorCD_C_Name = null;
        String sql_CD_C_Name = "SELECT *,inner_join_COMPANY.NAME\n" +
                "  FROM inner_join_COMPANY\n" +
                "       INNER JOIN\n" +
                "       inner_join_DEPARTMENT ON inner_join_COMPANY.ID = inner_join_DEPARTMENT.EMP_ID;";
        cursorCD_C_Name = db.rawQuery(sql_CD_C_Name, null);
        int count_CD_C_Name=cursorCD_C_Name.getCount();

        Cursor cursorCD_C_T_Name = null;
        String sql_CD_C_T_Name = "SELECT *,t1.NAME\n" +
                "  FROM inner_join_COMPANY t1\n" +
                "       INNER JOIN\n" +
                "      (select inner_join_DEPARTMENT.* from inner_join_DEPARTMENT ) t2 " +
                "ON t1.ID = t2.EMP_ID;";
        cursorCD_C_T_Name = db.rawQuery(sql_CD_C_T_Name, null);
        int count_CD_C_T_Name=cursorCD_C_T_Name.getCount();

        Cursor cursorCD_C_T_Name_T = null;
        String sql_CD_C_T_Name_T = "SELECT *,t1.NAME\n" +
                "  FROM inner_join_COMPANY t1\n" +
                "       INNER JOIN\n" +
                "      (select T.* from inner_join_DEPARTMENT T ) t2 " +
                "ON t1.ID = t2.EMP_ID;";
        cursorCD_C_T_Name_T = db.rawQuery(sql_CD_C_T_Name_T, null);
        int count_CD_C_T_Name_T=cursorCD_C_T_Name_T.getCount();

        try{
            Cursor cursorCD_C_Name_T = null;
            String sql_CD_C_Name_T = "SELECT *,inner_join_COMPANY.NAME\n" +
                    "  FROM inner_join_COMPANY t1\n" +
                    "       INNER JOIN\n" +
                    "      (select T.* from inner_join_DEPARTMENT T ) t2 " +
                    "ON t1.ID = t2.EMP_ID;";
            cursorCD_C_Name_T = db.rawQuery(sql_CD_C_Name_T, null);
            int count_CD_C_Name_T=cursorCD_C_Name_T.getCount();
        }catch (Exception e){
            e.printStackTrace();
        }

        Cursor cursorCD_C_Name_T2 = null;
        String sql_CD_C_Name_T2 = "SELECT *,inner_join_COMPANY.NAME\n" +
                "  FROM inner_join_COMPANY \n" +
                "       INNER JOIN\n" +
                "      (select T.* from inner_join_DEPARTMENT T ) t2 " +
                "ON inner_join_COMPANY.ID = t2.EMP_ID;";
        cursorCD_C_Name_T2 = db.rawQuery(sql_CD_C_Name_T2, null);
        int count_CD_C_Name_T2=cursorCD_C_Name_T2.getCount();

        if (count_CD_C_T_Name>0){
            int company_name=cursorCD_C_T_Name.getColumnIndex(DBConstansts.TABLE_COMPANY_NAME);
            int company_age=cursorCD_C_T_Name.getColumnIndex(DBConstansts.TABLE_COMPANY_AGE);
            int company_address=cursorCD_C_T_Name.getColumnIndex(DBConstansts.TABLE_COMPANY_ADDRESS);
            int company_sqlary=cursorCD_C_T_Name.getColumnIndex(DBConstansts.TABLE_COMPANY_SALARY);

            int department_name=cursorCD_C_T_Name.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_NAME);
            int department_dept=cursorCD_C_T_Name.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_DEPT);
            int department_emp_id=cursorCD_C_T_Name.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_EMP_ID);

            int last_count=cursorCD_C_T_Name.getColumnIndex("t1.NAME");
            int last_count1=cursorCD_C_T_Name.getColumnIndex("t1.NAME2");
            int last_count_2=cursorCD_C_T_Name.getColumnCount()-1;

            try{
                String[] columnNames = cursorCD_C_T_Name.getColumnNames();
                String columnName_last_count = cursorCD_C_T_Name.getColumnName(last_count);
                String columnName_last_count2 = cursorCD_C_T_Name.getColumnName(last_count_2);
                String columnName_last_count1 = cursorCD_C_T_Name.getColumnName(last_count1);

                try{
                    int columnCount=cursorCD_C_T_Name.getColumnCount();
                    if (cursorCD_C_T_Name.getColumnIndex("t1.logon_msg")>0&&"t1.logon_msg".equals(cursorCD_C_T_Name.getColumnName(columnCount-1))){
                        String data=cursorCD_C_T_Name.getString(columnCount-1);
                    }
                }catch (Exception e){//董翔-2018年4月25日12:35:33-替换的原因：最外层不能使用别名 来代替表名取真实值：若使用别名代替真实值取列的时候：
                    // 要使用这个别名代替取列的号数：例如ursor_T_Name.getColumnCount()-1;//ursor_T_Name.getColumnIndex(t1.logon_msg ) 这样不好使，等价于ursor_T_Name.getColumnIndex(logon_msg )
                    Log.e(TAG, "initData1 c.getColumnIndex(\"t1.logon_msg\")>0\"t1.logon_msg\".equals(c.getColumnName(columnCount-1)) ", e);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            while (cursorCD_C_T_Name.moveToNext()) {
                String data_company_name = cursorCD_C_T_Name.getString(company_name);
                String data_company_age = cursorCD_C_T_Name.getString(company_age);
                String data_company_address = cursorCD_C_T_Name.getString(company_address);
                String data_company_sqlary = cursorCD_C_T_Name.getString(company_sqlary);

                String data_department_name = cursorCD_C_T_Name.getString(department_name);
                String data_department_dept = cursorCD_C_T_Name.getString(department_dept);
                String data_department_emp_id = cursorCD_C_T_Name.getString(department_emp_id);
                try {
                    String index0=cursorCD_C_T_Name.getString(0);
                    String index1=cursorCD_C_T_Name.getString(1);
                    String index2=cursorCD_C_T_Name.getString(2);
                    String index3=cursorCD_C_T_Name.getString(3);
                    String index4=cursorCD_C_T_Name.getString(4);
                    String index5=cursorCD_C_T_Name.getString(5);
                    String index6=cursorCD_C_T_Name.getString(6);
                    String index7=cursorCD_C_T_Name.getString(7);
                    String index8=cursorCD_C_T_Name.getString(8);
                    String index9=cursorCD_C_T_Name.getString(9);//
                    String index10=cursorCD_C_T_Name.getString(10);
                    String index11=cursorCD_C_T_Name.getString(11);
                    String index12=cursorCD_C_T_Name.getString(12);
                    String index13=cursorCD_C_T_Name.getString(13);
                    String index14=cursorCD_C_T_Name.getString(14);
                    String index15=cursorCD_C_T_Name.getString(15);
                    String index16=cursorCD_C_T_Name.getString(16);
                    String index17=cursorCD_C_T_Name.getString(17);
                    String index18=cursorCD_C_T_Name.getString(18);
                }catch (Exception e){
                    Log.e(TAG,Thread.currentThread()+"queryCompanyDepartment ",e);
                }

                maps = new HashMap<>();
                maps.put(DBConstansts.TABLE_COMPANY_NAME, data_company_name);
                maps.put(DBConstansts.TABLE_COMPANY_AGE, data_company_age);
                maps.put(DBConstansts.TABLE_COMPANY_ADDRESS, data_company_address);
                maps.put(DBConstansts.TABLE_COMPANY_SALARY, data_company_sqlary);

                maps.put(DBConstansts.TABLE_DEPARTMENT_NAME, data_department_name);
                maps.put(DBConstansts.TABLE_DEPARTMENT_DEPT, data_department_dept);
                maps.put(DBConstansts.TABLE_DEPARTMENT_EMP_ID, data_department_emp_id);

                lists.add(maps);
            }

        }





        if (cursorCD_C_Name.getCount() < 1) {
            cursorCD_C_Name.close();
            return lists;
        } else {
            int company_name=cursorCD_C_Name.getColumnIndex(DBConstansts.TABLE_COMPANY_NAME);
            int company_age=cursorCD_C_Name.getColumnIndex(DBConstansts.TABLE_COMPANY_AGE);
            int company_address=cursorCD_C_Name.getColumnIndex(DBConstansts.TABLE_COMPANY_ADDRESS);
            int company_sqlary=cursorCD_C_Name.getColumnIndex(DBConstansts.TABLE_COMPANY_SALARY);

            int department_name=cursorCD_C_Name.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_NAME);
            int department_dept=cursorCD_C_Name.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_DEPT);
            int department_emp_id=cursorCD_C_Name.getColumnIndex(DBConstansts.TABLE_DEPARTMENT_EMP_ID);
            while (cursorCD_C_Name.moveToNext()) {
                String data_company_name = cursorCD_C_Name.getString(company_name);
                String data_company_age = cursorCD_C_Name.getString(company_age);
                String data_company_address = cursorCD_C_Name.getString(company_address);
                String data_company_sqlary = cursorCD_C_Name.getString(company_sqlary);

                String data_department_name = cursorCD_C_Name.getString(department_name);
                String data_department_dept = cursorCD_C_Name.getString(department_dept);
                String data_department_emp_id = cursorCD_C_Name.getString(department_emp_id);
                try {
                    String index0=cursorCD_C_Name.getString(0);
                    String index1=cursorCD_C_Name.getString(1);
                    String index2=cursorCD_C_Name.getString(2);
                    String index3=cursorCD_C_Name.getString(3);
                    String index4=cursorCD_C_Name.getString(4);
                    String index5=cursorCD_C_Name.getString(5);
                    String index6=cursorCD_C_Name.getString(6);
                    String index7=cursorCD_C_Name.getString(7);
                    String index8=cursorCD_C_Name.getString(8);
                    String index9=cursorCD_C_Name.getString(9);//
                    String index10=cursorCD_C_Name.getString(10);
                    String index11=cursorCD_C_Name.getString(11);
                    String index12=cursorCD_C_Name.getString(12);
                    String index13=cursorCD_C_Name.getString(13);
                    String index14=cursorCD_C_Name.getString(14);
                    String index15=cursorCD_C_Name.getString(15);
                    String index16=cursorCD_C_Name.getString(16);
                    String index17=cursorCD_C_Name.getString(17);
                    String index18=cursorCD_C_Name.getString(18);
                }catch (Exception e){
                    Log.e(TAG,Thread.currentThread()+"queryCompanyDepartment ",e);
                }

                maps = new HashMap<>();
                maps.put(DBConstansts.TABLE_COMPANY_NAME, data_company_name);
                maps.put(DBConstansts.TABLE_COMPANY_AGE, data_company_age);
                maps.put(DBConstansts.TABLE_COMPANY_ADDRESS, data_company_address);
                maps.put(DBConstansts.TABLE_COMPANY_SALARY, data_company_sqlary);

                maps.put(DBConstansts.TABLE_DEPARTMENT_NAME, data_department_name);
                maps.put(DBConstansts.TABLE_DEPARTMENT_DEPT, data_department_dept);
                maps.put(DBConstansts.TABLE_DEPARTMENT_EMP_ID, data_department_emp_id);

                lists.add(maps);
            }
        }
        cursorC.close();
        cursorD.close();
        cursorCD_All.close();
        cursorCD_C_Name.close();
        return lists;

    }



}
