package com.lcomputerstudy.example.domain;

import java.util.List;

public class Product {

	private int code;
	private List<String> category;
	private String name;
	private String desc;
	private List<String> type;
	private boolean isSale;
	private String detail_desc;
	private String material;
	private String size;
	private String manufacturer;
	private String caution;
	private int price;
	private String point;
	private int stock;
	private String shipping;
	private List<Option> options;
	private String category_s;
	private String type_s;
	private String filesname;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<String> getCategory() {
		return category;
	}
	public void setCategory(List<String> category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public boolean isSale() {
		return isSale;
	}
	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}
	public String getDetail_desc() {
		return detail_desc;
	}
	public void setDetail_desc(String detail_desc) {
		this.detail_desc = detail_desc;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getCaution() {
		return caution;
	}
	public void setCaution(String caution) {
		this.caution = caution;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public String getCategory_s() {
		return category_s;
	}
	public void setCategory_s(String category_s) {
		this.category_s = category_s;
	}
	public String getType_s() {
		return type_s;
	}
	public void setType_s(String type_s) {
		this.type_s = type_s;
	}
	public String getFilesname() {
		return filesname;
	}
	public void setFilesname(String filesname) {
		this.filesname = filesname;
	}
	
}
