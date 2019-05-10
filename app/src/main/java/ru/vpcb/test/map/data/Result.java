package ru.vpcb.test.map.data;

public class Result<T> {
    T mData;
    Throwable mException;

    public static class Success<T> extends Result<T>{
        public Success(T data) {
            this.mData = data;
        }
    }

    public static class Error<T> extends Result<T>{
        public Error(Throwable exception) {
            this.mException = exception;
        }
    }

    public Throwable getException() {
        return mException;
    }

    public T getData() {
        return mData;
    }

}
