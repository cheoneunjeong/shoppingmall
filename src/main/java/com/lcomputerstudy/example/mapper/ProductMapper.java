package com.lcomputerstudy.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lcomputerstudy.example.domain.Option;
import com.lcomputerstudy.example.domain.Product;

@Mapper
public interface ProductMapper {

	void createProduct(Product product);

	void insertOptions(@Param("o")String o,@Param("od") String od,@Param("code") int code);

	void insertfilesname(@Param("filesname")String filesname, @Param("code")String code);

	List<Product> getProductList();

	void insertMainPhoto(@Param("mainPhoto")String mainPhoto, @Param("code")String code);

	void deleteProduct(int code);

	Product getProductDetails(int code);

	List<Option> getOptions(int code);

	void editProduct(Product product);

	void editOptions(@Param("o")String o,@Param("od") String od,@Param("num") int num);

	List<Product> getproductlist_shop(int code);

}
