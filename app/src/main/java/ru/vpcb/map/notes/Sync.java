package ru.vpcb.map.notes;

import ru.vpcb.map.notes.data.Result;

public class Sync<T> {
    private final Object lock;
    private boolean isReady;
    private Result<T> mResult;

    public Sync() {
        this.lock = new Object();
        this.isReady = false;
    }

    public void unlock() {
        synchronized (lock) {
            setReady(true);
            lock.notifyAll();
        }
    }

    public void waiting() {
        synchronized (lock) {
            while (!isReady()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setResult(Result<T> result) {
        mResult = result;
    }

    public Result<T> getResult() {
        return mResult;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public boolean isReady() {
        return this.isReady;
    }
}