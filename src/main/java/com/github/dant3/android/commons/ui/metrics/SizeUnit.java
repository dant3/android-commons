package com.github.dant3.android.commons.ui.metrics;

import android.util.TypedValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public enum SizeUnit {
    PX(TypedValue.COMPLEX_UNIT_PX),
    PT(TypedValue.COMPLEX_UNIT_PT),
    SP(TypedValue.COMPLEX_UNIT_SP),
    MM(TypedValue.COMPLEX_UNIT_MM),
    IN(TypedValue.COMPLEX_UNIT_IN),
    DP(TypedValue.COMPLEX_UNIT_DIP);

    /*package*/ final int index;
}
