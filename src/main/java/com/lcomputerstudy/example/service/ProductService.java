package com.lcomputerstudy.example.service;

import java.util.List;

import com.lcomputerstudy.example.domain.Option;
import com.lcomputerstudy.example.domain.Product;

public interface ProductService {

	void createProduct(Product product);

	void insertOptions(Option option, int code);

	void insertfilesname(String filesname, String code);

	List<Product> getProductList();

	void insertMainPhoto(String string, String code);

	void deleteProduct(int code);

}
