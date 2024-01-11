package com.mjc.school.service;

public interface Validator<T> {

    Boolean validate(T dto);
}
