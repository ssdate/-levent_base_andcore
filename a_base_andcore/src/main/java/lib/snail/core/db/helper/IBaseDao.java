package lib.snail.core.db.helper;

import java.util.List;

public interface IBaseDao<T> {

    /***
     * 查询数据库
     */
    List<T> queryData(T where);
    List<T> queryData(T where ,String orderBy,int startIndex,int limit);

    /***
     *  插入数据库
     *  2019-4-5 levent
     */
    Long insert(T entity);

    /***
     * 更新数据库
     * 2019-4-5 levent
     */
    int update(T entry , T where);

    /***
     * 删除
     * 2019-4-9 levent
     */
    int delete(T where );

}
