package lib.snail.core.exception;

public class MyExctption extends  Exception {

    int httpCode = 0 ;
    public MyExctption(int httpCode) throws MyExctption {
        this.httpCode = httpCode ;
    }

    public MyExctption() {

    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
}
