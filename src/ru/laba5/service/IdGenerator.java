package ru.laba5.service;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private final AtomicLong counter = new AtomicLong(1);

    public long nextId() {
        return counter.getAndIncrement();
    }
}
