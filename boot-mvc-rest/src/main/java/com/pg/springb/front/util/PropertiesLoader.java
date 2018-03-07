package com.pg.springb.front.util;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesLoader {

    static final String GLOBAL = "global";
    protected static final String BASE_PROPERTY_FILE = "application.properties";

    public Map<String, String> getProperties(String env) {

           if (isStringEmptyOrNull(env)) {
                  //throw new Exception("Environment name not found"); TBD
           }

           Map<String, String> resultMap = new LinkedHashMap<String, String>();

           Properties baseProps = loadProperties(BASE_PROPERTY_FILE);
           Properties envProps = loadProperties(getEnvBasedFileName(env));

           Map<String, String> globalProps = getGlobalProperties(baseProps, envProps);
           Map<String, String> envProperties = getProperties(baseProps, envProps);

           resultMap = mergeMaps(globalProps, envProperties);
           return resultMap;
    }

    public String getEnvBasedFileName(String env) {
           env.trim();
           return "application-" + env.toLowerCase() + ".properties";
    }

    public Properties loadProperties(String filename) {
           Properties props = new Properties();
           try {
                  InputStream is = (getClass().getClassLoader().getResourceAsStream(filename));
                  if (is != null) {
                        props = new Properties();
                        props.load(is);
                  }
           } catch (Exception e) {
                  throw new RuntimeException(filename + " file not found. Failed loading.", e);
           }
           return props;
    }
    
    private Map<String, String> getGlobalProperties(Properties baseProps, Properties envProps) {
           int offset = GLOBAL.trim().length() + 1;
           Stream<Entry<Object, Object>> basePropsStream = baseProps.entrySet().stream()
                        .filter(e -> e.getKey().toString().startsWith(GLOBAL, 0));

           Stream<Entry<Object, Object>> envPropsStream = envProps.entrySet().stream()
                        .filter(e -> e.getKey().toString().startsWith(GLOBAL, 0));

           Map<String, String> basePropsMap = basePropsStream
                        .collect(Collectors.toMap(e -> e.getKey().toString().substring(offset), e -> (String) e.getValue()));

           Map<String, String> envPropsMap = envPropsStream
                        .collect(Collectors.toMap(e -> e.getKey().toString().substring(offset), e -> (String) e.getValue()));

           return mergeMaps(basePropsMap, envPropsMap);
    }

    
    
    private Map<String, String> getProperties(Properties baseProps, Properties envProps) {

           int offset = GLOBAL.trim().length() + 1;
           Stream<Entry<Object, Object>> basePropsStream = baseProps.entrySet().stream()
                        .filter(e -> !e.getKey().toString().startsWith(GLOBAL, 0));

           Stream<Entry<Object, Object>> envPropsStream = envProps.entrySet().stream()
                        .filter(e -> !e.getKey().toString().startsWith(GLOBAL, 0));

           Map<String, String> basePropsMap = basePropsStream
                        .collect(Collectors.toMap(e -> e.getKey().toString(), e -> (String) e.getValue()));

           Map<String, String> envPropsMap = envPropsStream
                        .collect(Collectors.toMap(e -> e.getKey().toString(), e -> (String) e.getValue()));

           return mergeMaps(basePropsMap, envPropsMap);
    }

    private Map<String, String> mergeMaps(Map<String, String> m1, Map<String, String> m2) {
           return Stream.concat(m1.entrySet().stream(), m2.entrySet().stream())
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (v1, v2) -> v2));
    }

    private boolean isStringEmptyOrNull(String inputStr) {
           return inputStr == null || inputStr.isEmpty();
    }
}

