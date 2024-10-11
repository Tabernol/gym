package com.krasnopolskyi.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdGenerator {
    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    /**
     * Generate unique ID also after restart project IDs will be unique
     * @return id
     */
    public static long generateId() {
        return snowflake.nextId();
    }
}
