package cn.itcast.takeout.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;

import cn.itcast.takeout.model.dao.bean.UserInfo;


public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static DBHelper dbHelper;
    private HashMap<String, Dao> daoHashMap = new HashMap<>();

    public DBHelper(Context context) {
        super(context, "takeout,db", null, 1);
    }

    //构建DBHelper的单列
    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            synchronized (DBHelper.class) {
                if (dbHelper==null){
                    dbHelper = new DBHelper(context);
                }
            }
        }
        return dbHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        //创建表
        try {
            TableUtils.clearTable(connectionSource, UserInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    //创建getDao,用于操作数据库
    public Dao getDao(Class clazz) {
        Dao dao ;
        dao = daoHashMap.get(clazz.getSimpleName());

        if (dao == null) {
            try {
                dao = super.getDao(clazz);
                daoHashMap.put(clazz.getSimpleName(),dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    //释放资源
    @Override
    public void close() {
        for (String daoSimpleName : daoHashMap.keySet()) {
            //将每一个daoSimpleName，至为空
            Dao dao = daoHashMap.get(daoSimpleName);
            dao = null;
        }
        super.close();
    }
}
