package com.lush.givex.fallback;

import com.android.volley.TimeoutError;

public interface GivexTimeoutErrorListener {

    void onTimeoutErrorResponse(TimeoutError timeoutError);
}
