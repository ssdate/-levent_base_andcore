package lib.snail.core.http;

import java.io.InputStream;

public interface IHttpListener {

    void onSuccess(InputStream inputStream);

    void onFailure(Exception e);

    void onLoading(String arg,String msg);

}
