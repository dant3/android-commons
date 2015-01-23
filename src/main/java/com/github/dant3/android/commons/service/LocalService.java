package com.github.dant3.android.commons.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LocalService<T extends LocalService<T>> extends Service {
    @SuppressWarnings("unchecked")
    private final Binder<T> binder = new BinderImpl<T>((T) this);

    @Override
    public LocalService.Binder<T> onBind(Intent intent) {
        return binder;
    }

    public interface Binder<T> extends IBinder {
        T getService();
    }


    protected static class BinderImpl<T extends LocalService<T>> extends android.os.Binder implements Binder<T> {
        private T service;

        public BinderImpl(T service) {
            this.service = service;
        }

        @Override
        public T getService() {
            return service;
        }
    }
}
