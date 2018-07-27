package dxcom.test.test.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dxcom.test.test.OSSApplication;

/**
 * package: dxcom.test.test.db
 * <br> author: dongxiang
 * <br>   date: 2018/4/23  15:51
 * <br>  descrp:
 * <br>  using:
 * <br>  e-mail:dongxiang_android_sdk@aliyun.com
 */

public class DBHelper  extends SQLiteOpenHelper {
    public final static int versionDB=19;
    public DBHelper() {
        super(OSSApplication.get(), "text_db", null, versionDB);
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableTest(db);
        createTableCompany(db);
        createTableDepartment(db);
//        Cursor cursor;
//        cursor.getColumnCount()
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DBConstansts.TABLE_TEST);
            db.execSQL("DROP TABLE IF EXISTS " + DBConstansts.TABLE_COMPANY_inner_join_COMPANY);
            db.execSQL("DROP TABLE IF EXISTS " + DBConstansts.TABLE_DEPARTMENT_inner_join_DEPARTMENT);
        }
        createTableTest(db);
        createTableCompany(db);
        createTableDepartment(db);

        createTableUnique(db);


    }





    private void createTableTest(SQLiteDatabase db){
        String dbSql="CREATE TABLE IF NOT  EXISTS "+DBConstansts.TABLE_TEST +" (\n" +
                "    "+DBConstansts.ID+"                  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    "+DBConstansts.TABLE_TEST_hanzi_nocollate+"      STRING,\n" +
                "    "+DBConstansts.TABLE_TEST_hanzi_collate_nocase+" STRING  COLLATE NOCASE,\n" +
                "    "+DBConstansts.TABLE_TEST_hanzi_collate_rtrim+"  STRING  COLLATE RTRIM,\n" +
                "    "+DBConstansts.TABLE_TEST_hanzi_collate_binary+" STRING  COLLATE BINARY\n" +
                ");\n";
        db.execSQL(dbSql);
    }

    private void createTableCompany(SQLiteDatabase db){
        String dbSql="CREATE TABLE IF NOT  EXISTS "+DBConstansts.TABLE_COMPANY_inner_join_COMPANY +" (\n" +
                "    "+DBConstansts.ID+"      INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    "+DBConstansts.TABLE_COMPANY_NAME+"    TEXT ,\n" +
                "    "+DBConstansts.TABLE_COMPANY_AGE+"     INTEGER,\n" +
                "    "+DBConstansts.TABLE_COMPANY_ADDRESS+" TEXT,\n" +
                "    "+DBConstansts.TABLE_COMPANY_SALARY+"  INTEGER\n" +
                ");";
        db.execSQL(dbSql);
    }

    private void createTableDepartment(SQLiteDatabase db){
        String dbSql="CREATE TABLE IF NOT  EXISTS "+DBConstansts.TABLE_DEPARTMENT_inner_join_DEPARTMENT +" (\n" +
                "    "+DBConstansts.ID+"     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    "+DBConstansts.TABLE_DEPARTMENT_DEPT+"   TEXT,\n" +
                "    "+DBConstansts.TABLE_DEPARTMENT_EMP_ID+" INTEGER UNIQUE ,\n" +
                "    "+DBConstansts.TABLE_DEPARTMENT_NAME+"   TEXT\n" +
                ");\n";
        db.execSQL(dbSql);
    }

    private void createTableUnique(SQLiteDatabase db) {
        String dbSql="CREATE TABLE IF NOT  EXISTS  " + DBConstansts.TABLE_CONSTRAINTS_UNIQUE+" (\n" +
                DBConstansts.ID+"          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_NAME+" TEXT,\n" +
                DBConstansts.TABLE_CONSTRAINTS_UNIQUE_PERSON_ID_unique +"  TEXT    UNIQUE\n" +
                ");\n";
        db.execSQL(dbSql);
    }


}
