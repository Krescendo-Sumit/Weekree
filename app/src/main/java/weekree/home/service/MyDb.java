package weekree.home.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;


public class MyDb extends SQLiteOpenHelper {

    final static String DBName = "weekree";
    final static int version = 1;

    public MyDb(Context context) {
        super(context, DBName, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//[{"UserId":1,"PersonName":"VAN-MH-20-A 2245","UserName":"pratik","Password":"12345","UserTypeId":1}]
       try {
           String tbl_user = "CREATE TABLE user(id INTEGER PRIMARY KEY,uid text,name text,mobile text)";
           db.execSQL(tbl_user);
           Log.i("Local Record ", "Table Created");
           tbl_user = "insert into user values(5,'0','0','0')";
           db.execSQL(tbl_user);

       }catch(Exception e)
       {

       }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    //user(id INTEGER PRIMARY KEY,uid text,pname text,uname text,usertype text,pass text
    public boolean updateUser(String uid,String name,String mobile) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "update user set uid='" + uid + "',name='" + name + "',mobile='" + mobile + "' where id=5";
            Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            Log.i("Query is -------> Done ", "" + q);
            return true;
        } catch (Exception e) {
            Log.i("Error is Add User", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }







    public Vector getUser() {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v = new Vector();
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM user";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {
                v.addElement(c.getString(0).trim());
                v.addElement(c.getString(1).trim());
                v.addElement(c.getString(2).trim());
                v.addElement(c.getString(3).trim());


            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }


    public String getUserID() {
        SQLiteDatabase mydb = null;
        String k = "";
        String v = null;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM user";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {
                v = c.getString(1).trim();
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public String getUserName() {
        SQLiteDatabase mydb = null;
        String k = "";
        String v = null;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM user";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {

                v = "" + c.getString(2).trim();

            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }
    public String getUserMobile() {
        SQLiteDatabase mydb = null;
        String k = "";
        String v = null;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM user";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {

                v = "" + c.getString(3).trim();

            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }


}
