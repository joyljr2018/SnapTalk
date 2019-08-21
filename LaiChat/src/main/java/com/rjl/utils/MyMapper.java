package com.rjl.utils;


import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * extends mapper from tk
 * @param <T>
 */

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
