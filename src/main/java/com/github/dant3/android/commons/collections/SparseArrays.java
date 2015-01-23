package com.github.dant3.android.commons.collections;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

public final class SparseArrays {
    public static SparseIntArray newIntArray(int... values) {
        SparseIntArray array = new SparseIntArray(values.length);
        for (int i = 0; i < values.length; ++i) {
            array.put(i, values[i]);
        }
        return array;
    }

    public static SparseBooleanArray newBooleanArray(boolean... values) {
        SparseBooleanArray array = new SparseBooleanArray(values.length);
        for (int i = 0; i < values.length; ++i) {
            array.put(i, values[i]);
        }
        return array;
    }

    @SafeVarargs
    public static <T> SparseArray<T> newArray(T... values) {
        SparseArray<T> array = new SparseArray<T>(values.length);
        for (int i = 0; i < values.length; ++i) {
            array.put(i, values[i]);
        }
        return array;
    }
}
