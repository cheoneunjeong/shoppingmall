package com.lcomputerstudy.example.service;

import java.util.List;

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

	@Override
	public void insertfilesname(String filesname, String code) {
		productmapper.insertfilesname(filesname, code);
		
	}

	@Override
	public List<Product> getProductList() {
		// TODO Auto-generated method stub
		return productmapper.getProductList();
	}

	@Override
	public void insertMainPhoto(String mainPhoto, String code) {
		productmapper.insertMainPhoto(mainPhoto, code);
		
	}

	@Override
	public void deleteProduct(int code) {
		productmapper.deleteProduct(code);
		
	}

	@Override
	public Product getProductDetails(int code) {
		// TODO Auto-generated method stub
		return productmapper.getProductDetails(code);
	}

	@Override
	public List<Option> getOptions(int code) {
		// TODO Auto-generated method stub
		return productmapper.getOptions(code);
	}

	@Override
	public void editProduct(Product product) {
		productmapper.editProduct(product);
		
	}

	@Override
	public void editOptions(Option option, int num) {
		String o = option.getOption();
		String od = option.getOp_detail();
		productmapper.editOptions(o, od, num);
		
	}

}
