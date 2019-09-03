package lib.snail.core.db.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.snail.core.db.annotion.DbField;
import lib.snail.core.db.annotion.DbTable;


/***
 * 数据库操作实现公共类
 * @param <T>
 */
public abstract   class BaseDao<T> implements IBaseDao<T> {

    /***
     * 持有数据库操作对象的引用
     */
    SQLiteDatabase sqLiteDatabase ;
    /****
     * 表名
     */
    String tableName ;

    /****
     * 是否初始化
     */
    boolean isInit = false ;

    /***
     * 操作数据对应类型
     */
    Class<T> entity ;

    /***
     *表与成员变量映射关系
     */
    Map<String, Field> caCheMap = new HashMap<String,Field>();



    /***
     * 初始化basedao
     * @param clazz
     * @param sqlDb
     * @return
     */
    public boolean init(Class<T> clazz,SQLiteDatabase sqlDb){
        if(!isInit){
            this.entity = clazz;
            this.sqLiteDatabase = sqlDb ;
            if(entity.getAnnotation(DbTable.class)==null){
                tableName = entity.getSimpleName();
            }else{
                tableName = entity.getAnnotation(DbTable.class).value();
            }
            if(!sqLiteDatabase.isOpen()){
                return false;
            }
            if(!TextUtils.isEmpty(createTable())){
                sqLiteDatabase.execSQL(createTable());
            }

            initCacheMap();
            isInit = true ;

        }
        return isInit ;
    }

    //创建表
    public abstract    String createTable() ;

    /***
     * 实现更新数据
     * @return
     * 2019-4-9 levent
     */
    @Override
    public int update(T entity ,T where) {
        int res = -1 ;
        Map<String,String> values = getValues(where);
        Map<String,String> whereClause = getValues(entity);
        BuildCondition buildcondition = new BuildCondition(whereClause);
        ContentValues contentValues = getContentValues(values);
        res = sqLiteDatabase.update(tableName,contentValues,buildcondition.getWhereClsuse(),buildcondition.getStringArgs());
        return 0;
    }

    /***
     * 实现查询数据
     * @param where
     * @return
     * 2019-4-9 levent
     */
    @Override
    public List<T> queryData(T where) {
        return null;
    }

    /***
     * 实现查询数据，多条件
     * @param where
     * @param orderBy
     * @param limit
     * @return
     * 2019-4-9 levent
     */
    @Override
    public List<T> queryData(T where, String orderBy,int startIndex, int limit) {
        Map values = getValues(where);
        String limitStr  = null ;
        if(-1!=startIndex && -1 !=limit){
            limitStr = startIndex +"," + limit ;
        }
        BuildCondition condition = new BuildCondition(values);
        Cursor cursor = sqLiteDatabase.query(tableName,null,condition.getWhereClsuse(),condition.getStringArgs(),null,null,orderBy,limitStr);
        List<T> result = getResult(cursor,where);
        cursor.close();
        return result;
    }

    /***
     * 实现插入数据
     * @param obj
     * @return
     */
    @Override
    public Long insert(T obj) {
        Map<String,String> res  = getValues(obj);
        ContentValues contentValues = getContentValues(res);
        Long inres = sqLiteDatabase.insert(tableName,null,contentValues);
        return inres;
    }

    @Override
    public int delete(T where) {
        int res = -1 ;
        Map map = getValues(where);
        BuildCondition buildcondition = new BuildCondition(map);
        res = sqLiteDatabase.delete(tableName,buildcondition.getWhereClsuse(),buildcondition.getStringArgs());
        return 0;
    }

    /***
     * 处理成员变量与缓存关系映射
     */
    private void initCacheMap() {
        String sql = "select * from "+tableName + " limit 0,10";
        Cursor cursor = null ;
        try {
            cursor = sqLiteDatabase.rawQuery(sql,null);
            //field数组
            Field columnFields[] = entity.getFields() ;
            for(Field f : columnFields){
                f.setAccessible(false);
            }
            //列名
            String[] columnNames = cursor.getColumnNames() ;
            for(String column :columnNames){
                Field tempField = null ;
                for(Field f : columnFields){
                    String fieldName = null ;
                    if(entity.getAnnotation(DbField.class) == null){
                        fieldName  = entity.getSimpleName();
                    }else{
                        fieldName = entity.getAnnotation(DbField.class).value();
                    }
                    if(fieldName.equals(column)){
                        tempField = f ;
                        break;
                    }
                }
                if(tempField !=null){
                    caCheMap.put(column,tempField);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } finally {
            if(cursor !=null){
                cursor.close();
                cursor = null;
            }
        }
    }



    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set keys =map.entrySet();
        Iterator<Map.Entry<String,String>> iterator = keys.iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String> m = iterator.next() ;
            String key = m.getKey() ;
            String val = m.getValue();
           if(null != val){
               contentValues.put(key,val);
           }
        }
        return contentValues ;
    }


    private Map<String, String> getValues(T obj) {
        Map<String,String> map = new HashMap<String,String>();
        Iterator<Field> iterator = caCheMap.values().iterator();
        while(iterator.hasNext()){
            Field tempField = iterator.next();
            String caKey = null ;
            String caValue = null ;
            if(tempField.getAnnotation(DbField.class)==null){
                caKey = tempField.getClass().getSimpleName();
            }else{
                caKey = tempField.getAnnotation(DbField.class).value();
            }
            try {
                if(null == tempField.get(entity )){
                    continue;
                }
                caValue = tempField.get(entity).toString();
                map.put(caKey,caValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
            }
        }
        return map ;
    }


    /***8
     * 封装查询语句
     * 2019-4-9 levent
     */
    class BuildCondition {
        private String whereClsuse ;
        private String stringArgs[] ;

        public BuildCondition(Map<String,String> _whereClause ) {
//            this.whereClsuse = whereClsuse;
//            this.stringargs = stringargs;
            ArrayList list = new ArrayList();
            StringBuffer sb = new StringBuffer();
            sb.append(" 1=1 ") ;
            Set keys =_whereClause.entrySet();
            Iterator iterator = keys.iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> entry = (Map.Entry<String, String>) iterator.next();
                String key = entry.getKey();
                String val = entry.getValue();
                if(null != val){
                    sb.append(" and "+key+"=?");
                    list.add(val);
                }
            }
            this.whereClsuse = sb.toString() ;
            this.stringArgs = (String[]) list.toArray(new String[list.size()]);
        }

        public String getWhereClsuse() {
            return whereClsuse;
        }

        public void setWhereClsuse(String whereClsuse) {
            this.whereClsuse = whereClsuse;
        }

        public String[] getStringArgs() {
            return stringArgs;
        }

        public void setStringArgs(String[] stringArgs) {
            this.stringArgs = stringArgs;
        }
    }

    /***
     * 将查询结果转换成list
     * @param cursor
     * @param where
     * @return
     */
    private List<T> getResult(Cursor cursor, T where) {
        ArrayList list=new ArrayList();

        Object item;
        while (cursor.moveToNext())
        {
            try {
                item=where.getClass().newInstance();
                /**
                 * 列名  name
                 * 成员变量名  Filed;
                 */
                Iterator iterator= caCheMap.entrySet().iterator();
                while (iterator.hasNext())
                {
                    Map.Entry entry= (Map.Entry) iterator.next();
                    /**
                     * 得到列名
                     */
                    String colomunName= (String) entry.getKey();
                    /**
                     * 然后以列名拿到  列名在游标的位子
                     */
                    Integer colmunIndex=cursor.getColumnIndex(colomunName);

                    Field field= (Field) entry.getValue();

                    Class type=field.getType();
                    if(colmunIndex!=-1)
                    {
                        if(type==String.class)
                        {
                            //反射方式赋值
                            field.set(item,cursor.getString(colmunIndex));
                        }else if(type==Double.class)
                        {
                            field.set(item,cursor.getDouble(colmunIndex));
                        }else  if(type==Integer.class)
                        {
                            field.set(item,cursor.getInt(colmunIndex));
                        }else if(type==Long.class)
                        {
                            field.set(item,cursor.getLong(colmunIndex));
                        }else  if(type==byte[].class)
                        {
                            field.set(item,cursor.getBlob(colmunIndex));
                            /*
                            不支持的类型
                             */
                        }else {
                            continue;
                        }
                    }

                }
                list.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

}
