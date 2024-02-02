package com.common.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * 常用工具类型
 *
 * @author rends
 * @date 2019/11/2
 **/
public class Util {

    public static String BLANK = " ";

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isBlank(String str) {
        return Objects.isNull(str) || Objects.equals(BLANK, str.trim());
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isNotBlank(String str) {
        return Objects.nonNull(str) && !Objects.equals(BLANK, str.trim());
    }

    public static <K, V, R> Map<K, V> listToMap(List<R> list, Function<R, K> getKey, Function<R, V> getValue) {
        if (Util.isEmpty(list)) {
            return new HashMap<>(8);
        }
        Map<K, V> map = new HashMap<>(list.size());
        list.forEach(item -> map.put(getKey.apply(item), getValue.apply(item)));
        return map;
    }

    public static <K, V> Map<K, V> listToMap(List<V> list, Function<V, K> getKey) {
        return listToMap(list, getKey, Function.identity());
    }

    public static <R, E> List<R> toList(List<E> sourceList, Function<E, R> getValue) {
        if (isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<R> rList = new ArrayList<>(sourceList.size());
        for (E item : sourceList) {
            rList.add(getValue.apply(item));
        }
        return rList;
    }

    public static <K, V, R> List<R> mapToList(Map<K, V> source, Function<Map.Entry<K, V>, R> getValue) {
        if (isEmpty(source)) {
            return new ArrayList<>();
        }
        List<R> rList = new ArrayList<>(source.size());
        for (Map.Entry<K, V> e : source.entrySet()) {
            rList.add(getValue.apply(e));
        }
        return rList;
    }


    public static <K, V> Map<K, List<V>> listToListMap(List<V> list, Function<V, K> getKey) {
        return listToListMap(list, getKey, Function.identity());
    }

    public static <K, V, R> Map<K, List<R>> listToListMap(List<V> list, Function<V, K> getKey,
                                                          Function<V, R> getValue) {
        if (isEmpty(list)) {
            return new HashMap<>(8);
        }
        Map<K, List<R>> map = new HashMap<>(list.size());

        for (V v : list) {
            K key = getKey.apply(v);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(getValue.apply(v));
        }
        return map;
    }

    public static <K, V, R> Map<K, V> listToMap(List<R> list, Predicate<R> predicate, Function<R, K> getKey,
                                                Function<R, V> getValue) {
        if (Util.isEmpty(list)) {
            return new HashMap<>(8);
        }
        Map<K, V> map = new HashMap<>(Math.min(list.size(), 256));
        for (R r : list) {
            if (predicate.test(r)) {
                map.put(getKey.apply(r), getValue.apply(r));
            }
        }
        return map;
    }

    public static <E, R> Set<E> listToSet(List<R> list, Predicate<R> predicate, Function<R, E> getKey) {
        if (Util.isEmpty(list)) {
            return new HashSet<>(8);
        }
        Set<E> set = new HashSet<>(list.size());
        for (R r : list) {
            if (predicate.test(r)) {
                set.add(getKey.apply(r));
            }
        }
        return set;
    }

    public static <E, R> Set<E> listToSet(List<R> list, Function<R, E> getKey) {
        return listToSet(list, o -> true, getKey);
    }

    public static <R, E> List<R> toList(List<E> sourceList, Predicate<E> predicate, Function<E, R> getValue) {
        if (isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<R> rList = new ArrayList<>(sourceList.size());
        for (E item : sourceList) {
            if (predicate.test(item)) {
                rList.add(getValue.apply(item));
            }
        }
        return rList;
    }

    public static String blankElse(String value, String elValue) {
        return Util.isBlank(value) ? elValue : value;
    }

    public static Object nullElse(Object value, Object elValue) {
        return value == null ? elValue : value;
    }
}
