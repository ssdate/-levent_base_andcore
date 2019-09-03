package lib.snail.core.base.common;


/***
 * 通用回调类
 * @param <T>
 */
public interface LibBaseCallback<T> {
    void callback(String arg,T t);
}
