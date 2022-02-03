package com.lcomputerstudy.example.service;

import com.lcomputerstudy.example.domain.Option;
import com.lcomputerstudy.example.domain.Product;

public interface ProductService {

	void createProduct(Product product);

	void insertOptions(Option option, int code);

}
