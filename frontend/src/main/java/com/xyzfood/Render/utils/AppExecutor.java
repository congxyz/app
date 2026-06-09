package com.xyzfood.Render.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

    public static ExecutorService getExecutor() {
        return EXECUTOR;
    }
}