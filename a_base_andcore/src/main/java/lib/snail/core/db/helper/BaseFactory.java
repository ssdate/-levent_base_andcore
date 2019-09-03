package lib.snail.core.db.helper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

import lib.snail.core.utils.LogUtil;

public class BaseFactory {

    /***
     * 数据库路径
     */
    private String databasePath ;

    /***
     * 数据库操作对象
     */
    private SQLiteDatabase sqLiteDatabase ;

    private static BaseFactory baseFactory  ;
    public static BaseFactory getInstance(){
        if(baseFactory == null){
            baseFactory = new BaseFactory();
        }
        return baseFactory ;
    }

    public BaseFactory(){
        String dir = Environment.getExternalStorageDirectory()+"/goaltall/cache";
        File dirFile = new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        databasePath = dir +"/gtcache.db";
        openDababase();
    }

    private void openDababase() {
        this.sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
    }


    public synchronized <T extends BaseDao<M>,M> T TgetDatabseHepler(Class<T> clazz,Class<M> entityClass){
        BaseDao baseDao = null ;
        try {
            baseDao = clazz.newInstance();
            baseDao.init(entityClass,sqLiteDatabase);
        } catch (InstantiationException e) {
            LogUtil.e(e);
        } catch (IllegalAccessException e) {
            LogUtil.e(e);
        }
        return (T) baseDao;
    }

}
