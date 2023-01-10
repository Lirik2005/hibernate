package com.lirik.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
