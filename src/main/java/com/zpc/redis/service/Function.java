package com.zpc.redis.service;

public interface Function<T, E> {
    T callback(E e);
}
