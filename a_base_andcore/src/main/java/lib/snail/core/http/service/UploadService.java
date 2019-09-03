package lib.snail.core.http.service;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import lib.snail.core.http.IHttpListener;
import lib.snail.core.http.IHttpService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/***
 * 文件上传service'
 * 2019-5-8 levent
 */
public class UploadService implements IHttpService {
    private static String TAG = "UploadService";
    private String httpUrl;
    private byte[] requestData;
    private IHttpListener iHttpListener;
    //1.创建OkHttpClient对象
    OkHttpClient okHttpClient = new OkHttpClient();

    HashMap<String, Object> paramsMap ;
    /**
     * 监听构造函数
     * @param paramsMap 参数
     */
    public UploadService(HashMap<String, Object> paramsMap){
        this.paramsMap = paramsMap ;
    }

    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("multipart/form-data");

    @Override
    public void setMethod(String m) {

    }

    @Override
    public void setUrl(String url) { 
        this.httpUrl = url ;
    }

    @Override
    public void setRequestData(byte[] requestData) { 
        this.requestData = requestData ;
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) { 
        this.iHttpListener = httpListener ;
    }

    @Override
    public void execute() {
        upLoadFile();
    }

    /**
     * 上传文件
     * @param filePath  本地文件地址
     */
    /**
     *上传文件
     * @param <T>
     */
    public <T> void upLoadFile(  ) {
        try {
            //补全请求地址
//            String requestUrl = String.format("%s/%s", upload_head, actionUrl);
            String requestUrl = httpUrl ;
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file));
                }
            }
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().addHeader("Accept-Encoding", "gzip").url(requestUrl).post(body).build();
            final  Call call = okHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, e.toString());
                    iHttpListener.onLoading("err","上传失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String str = response.body().string();
//                        Log.e(TAG, "上传完成返回结果 ----->" + str);
//                        successCallBack((T) string, callBack);
//                        iHttpListener.onLoading("ok",str);
                        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
                        //response.body().byteStream()
                        iHttpListener.onSuccess(inputStream);
                    } else {
                        iHttpListener.onLoading("err","上传失败");
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            iHttpListener.onFailure(e);
        }
    }

    /**
     * 创建带进度的RequestBody
     * @param contentType MediaType
     * @param file  准备上传的文件
     * @param <T>
     * @return
     * 2019-5-8 levent
     */
    public <T> RequestBody createProgressRequestBody(final MediaType contentType, final File file ) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        Log.e(TAG, "current------>" + current);
//                        progressCallBack(remaining, current, callBack);
                        iHttpListener.onLoading("loading",current+"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
