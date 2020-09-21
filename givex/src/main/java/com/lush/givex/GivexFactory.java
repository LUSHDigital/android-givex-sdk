package com.lush.givex;

import com.android.volley.RequestQueue;

public final class GivexFactory {

    public static Givex volleyInstance(RequestQueue queue, GivexConfig config) {
        return new VolleyGivex(queue, config);
    }

    private GivexFactory() {}
}
