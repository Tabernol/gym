package com.krasnopolskyi.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdGenerator {
    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    /**
     * Generate unique ID also after restart project IDs will be unique
     * @return
     */
    public static long generateId() {
        return snowflake.nextId();
    }
}
