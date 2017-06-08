package com.sq.common.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qishang on 2017/6/8.
 */
public class NamedThreadFactory implements ThreadFactory {
    private static final Map<String, AtomicInteger> SEQ_BUCKET = new HashMap<String, AtomicInteger>();
    private final String mPrefix;
    private final boolean isDaemo;

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean isDaemo) {
        this.mPrefix = prefix;
        this.isDaemo = isDaemo;
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = mPrefix + getSeq(mPrefix);
        Thread ret = new Thread(r, name);
        ret.setDaemon(isDaemo);
        return ret;
    }

    private static int getSeq(String prefix) {
        AtomicInteger seq = SEQ_BUCKET.get(prefix);
        if (seq != null) {
            return seq.incrementAndGet();
        } else {
            synchronized (SEQ_BUCKET) {
                seq = SEQ_BUCKET.get(prefix);
                if (seq == null) {
                    seq = new AtomicInteger(0);
                    SEQ_BUCKET.put("prefix", seq);
                    return seq.incrementAndGet();
                } else {
                    return seq.incrementAndGet();
                }
            }

        }
    }


}
