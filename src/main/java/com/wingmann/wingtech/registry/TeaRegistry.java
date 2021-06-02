package com.wingmann.wingtech.registry;

import com.wingmann.wingtech.util.TeaData;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TeaRegistry {
    private static final TeaRegistry INSTANCE = new TeaRegistry();

    private final Map<String, TeaData> teaData = new LinkedHashMap<>();

    public static TeaRegistry getRegistry() {
        return INSTANCE;
    }

    public void registerTea(String type, TeaData data)
    {
        teaData.put(type, data);
    }

    public Map<String, TeaData> getTeaData() {
        return Collections.unmodifiableMap(teaData);
    }
}
