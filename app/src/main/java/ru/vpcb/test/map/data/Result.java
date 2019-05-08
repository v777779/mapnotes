package ru.vpcb.test.map.data;

public class Result<T> {

    public static class Success<T> extends Result<T>{
     private T mData;
        public Success(T data) {
            this.mData = data;
        }

        public T getData() {
            return mData;
        }
    }

    public static class Error<T> extends Result<T>{
        private Throwable mException;
        public Error(Throwable exception) {
            this.mException = exception;
        }

        public Throwable getException() {
            return mException;
        }
    }



}
