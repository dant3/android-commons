package com.github.dant3.android.commons.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

public class LocalServiceBinding<T> implements ServiceConnection {
    private final AtomicReference<T> binding = new AtomicReference<T>();
    private final Set<Listener<T>> listeners = new CopyOnWriteArraySet<Listener<T>>();

    public void addListener(Listener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener<T> listener) {
        listeners.remove(listener);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof LocalService.Binder) {
            LocalService.Binder<T> serviceBinding = (LocalService.Binder<T>) service;
            T serviceBound = serviceBinding.getService();
            binding.set(serviceBound);
            notifyServiceConnected(serviceBound);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        notifyServiceDisconnected(getBinding());
        binding.set(null);
    }

    public T getBinding() {
        return binding.get();
    }

    private void notifyServiceConnected(T service) {
        for (Listener<T> listener : listeners) {
            listener.onServiceConnected(service);
        }
    }

    private void notifyServiceDisconnected(T service) {
        for (Listener<T> listener : listeners) {
            listener.onServiceDisconnected(service);
        }
    }

    public interface Listener<T> {
        void onServiceConnected(T binding);
        void onServiceDisconnected(T binding);
    }
}
