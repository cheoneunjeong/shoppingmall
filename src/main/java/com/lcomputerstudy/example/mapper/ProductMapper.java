package com.lcomputerstudy.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lcomputerstudy.example.domain.Option;
import com.lcomputerstudy.example.domain.Product;

@Mapper
public interface ProductMapper {

	void createProduct(Product product);

	void insertOptions(@Param("o")String o,@Param("od") String od,@Param("code") int code);

}
