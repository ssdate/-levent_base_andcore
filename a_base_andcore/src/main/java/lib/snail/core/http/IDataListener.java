package lib.snail.core.http;

public interface IDataListener<M> {
    void onSuccess(M responseData) ;
    void onFailure(Exception e);
}
