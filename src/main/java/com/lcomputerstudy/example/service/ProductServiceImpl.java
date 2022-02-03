package com.lcomputerstudy.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcomputerstudy.example.domain.Option;
import com.lcomputerstudy.example.domain.Product;
import com.lcomputerstudy.example.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductMapper productmapper;
	
	@Override
	public void createProduct(Product product) {
		productmapper.createProduct(product);
		
	}

	@Override
	public void insertOptions(Option option, int code) {
		String o = option.getOption();
		String od = option.getOp_detail();
		productmapper.insertOptions(o, od, code);
		
	}

}
