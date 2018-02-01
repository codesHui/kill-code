package com.imi.base.entity;


import java.io.Serializable;

/**
 * 可唯一标准的
 */
public interface Idable<T extends Serializable> {

    public void setId(T id);

    public T getId();

}
