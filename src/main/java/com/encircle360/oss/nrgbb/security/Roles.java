package com.encircle360.oss.nrgbb.security;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Roles {

    private Roles() {
    }

    private final static String PREFIX = "ROLE_";

    private interface RoleSet {
    }

    public static List<String> allRoles() {
        List<String> roles = new ArrayList<>();
        Class<?>[] classes = Roles.class.getClasses();

        Arrays.stream(classes)
            .filter(c -> Arrays
                .stream(c.getInterfaces())
                .anyMatch(i -> i.isAssignableFrom(RoleSet.class))
            )
            .map(c -> {
                Field[] fields = c.getDeclaredFields();
                return Arrays.stream(fields)
                    .filter(field -> Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))
                    .map(field -> {
                        try {
                            String raw = (String) field.get(c);
                            return cleanRole(raw);
                        } catch (Exception ignored) {
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            })
            .forEach(roles::addAll);
        return roles;
    }

    public static String cleanRole(String raw) {
        if (raw.startsWith(PREFIX)) {
            return raw.substring(PREFIX.length());
        }
        return raw;
    }

    private static class Pattern {

        private Pattern() {
        }

        public static final String CAN_LIST = "CAN_LIST";
        public static final String CAN_GET = "CAN_GET";
        public static final String CAN_DELETE = "CAN_DELETE";
        public static final String CAN_DELETE_OWN = "CAN_DELETE_OWN";
        public static final String CAN_CREATE = "CAN_CREATE";
        public static final String CAN_CREATE_OWN = "CAN_CREATE_OWN";
        public static final String CAN_UPDATE = "CAN_UPDATE";
        public static final String CAN_UPDATE_OWN = "CAN_UPDATE_OWN";
    }

    public static class Author implements RoleSet {

        private Author() {
        }

        private final static String ENTITY = "_AUTHOR";

        public static final String CAN_LIST = PREFIX + Pattern.CAN_LIST + ENTITY;
        public static final String CAN_GET = PREFIX + Pattern.CAN_GET + ENTITY;
        public static final String CAN_DELETE = PREFIX + Pattern.CAN_DELETE + ENTITY;
        public static final String CAN_DELETE_OWN = PREFIX + Pattern.CAN_DELETE_OWN + ENTITY;
        public static final String CAN_CREATE = PREFIX + Pattern.CAN_CREATE + ENTITY;
        public static final String CAN_CREATE_OWN = PREFIX + Pattern.CAN_CREATE_OWN + ENTITY;
        public static final String CAN_UPDATE = PREFIX + Pattern.CAN_UPDATE + ENTITY;
        public static final String CAN_UPDATE_OWN = PREFIX + Pattern.CAN_UPDATE_OWN + ENTITY;
    }

    public static class Thread implements RoleSet {

        private Thread() {
        }

        private final static String ENTITY = "_THREAD";

        public static final String CAN_LIST = PREFIX + Pattern.CAN_LIST + ENTITY;
        public static final String CAN_GET = PREFIX + Pattern.CAN_GET + ENTITY;
        public static final String CAN_DELETE = PREFIX + Pattern.CAN_DELETE + ENTITY;
        public static final String CAN_DELETE_OWN = PREFIX + Pattern.CAN_DELETE_OWN + ENTITY;
        public static final String CAN_CREATE = PREFIX + Pattern.CAN_CREATE + ENTITY;
        public static final String CAN_UPDATE = PREFIX + Pattern.CAN_UPDATE + ENTITY;
        public static final String CAN_UPDATE_OWN = PREFIX + Pattern.CAN_UPDATE_OWN + ENTITY;
    }

    public static class Post implements RoleSet {

        private Post() {
        }

        private final static String ENTITY = "_POST";

        public static final String CAN_LIST = PREFIX + Pattern.CAN_LIST + ENTITY;
        public static final String CAN_GET = PREFIX + Pattern.CAN_GET + ENTITY;
        public static final String CAN_DELETE = PREFIX + Pattern.CAN_DELETE + ENTITY;
        public static final String CAN_DELETE_OWN = PREFIX + Pattern.CAN_DELETE_OWN + ENTITY;
        public static final String CAN_CREATE = PREFIX + Pattern.CAN_CREATE + ENTITY;
        public static final String CAN_CREATE_OWN = PREFIX + Pattern.CAN_CREATE_OWN + ENTITY;
        public static final String CAN_UPDATE = PREFIX + Pattern.CAN_UPDATE + ENTITY;
        public static final String CAN_UPDATE_OWN = PREFIX + Pattern.CAN_UPDATE_OWN + ENTITY;
    }

    public static class Category implements RoleSet {

        private Category() {
        }

        private final static String ENTITY = "_CATEGORY";

        public static final String CAN_LIST = PREFIX + Pattern.CAN_LIST + ENTITY;
        public static final String CAN_GET = PREFIX + Pattern.CAN_GET + ENTITY;
        public static final String CAN_DELETE = PREFIX + Pattern.CAN_DELETE + ENTITY;
        public static final String CAN_CREATE = PREFIX + Pattern.CAN_CREATE + ENTITY;
        public static final String CAN_UPDATE = PREFIX + Pattern.CAN_UPDATE + ENTITY;
    }

}
