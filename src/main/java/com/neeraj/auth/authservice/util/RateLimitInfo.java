package com.neeraj.auth.authservice.util;

import java.time.Instant;

public class RateLimitInfo {
    private int requestCount;
    private Instant windowStart;
    public RateLimitInfo(){
        this.requestCount = 1;
        this.windowStart = Instant.now();
    }
    public int getRequestCount(){
        return requestCount;
    }
    public void increment(){
        this.requestCount++;
    }
    public Instant getWindowStart(){
        return windowStart;
    }
    public void reset(){
        this.requestCount = 1;
        this.windowStart  = Instant.now();
    }

}
