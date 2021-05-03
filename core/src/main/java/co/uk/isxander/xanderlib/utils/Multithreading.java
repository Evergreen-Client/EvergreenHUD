/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.xanderlib.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class Multithreading {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final ScheduledThreadPoolExecutor SCHEDULED_POOL = new ScheduledThreadPoolExecutor(20, (r) -> new Thread(r, String.format("Scheduled Thread #%s", counter.incrementAndGet())));
    public static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(20, 20, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), (r) -> new Thread(r, String.format("Thread #%s", counter.incrementAndGet())));

    public static ScheduledFuture<?> scheduleAsyncAtFixedRate(Runnable runnable, long initialDelay, long delayBetweenExecution, TimeUnit unit) {
        return SCHEDULED_POOL.scheduleAtFixedRate(runnable, initialDelay, delayBetweenExecution, unit);
    }

    public static ScheduledFuture<?> scheduleAsync(Runnable runnable, long delay, TimeUnit unit) {
        return SCHEDULED_POOL.schedule(runnable, delay, unit);
    }

    public static void runAsync(Runnable runnable) {
        POOL.execute(runnable);
    }

    public static Future<?> submit(Runnable runnable) {
        return POOL.submit(runnable);
    }

}
