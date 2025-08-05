package com.apicollabdev.odk.collabdev.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionAuth {
        public static Map<String, Long> sessions = new ConcurrentHashMap<>();


}
