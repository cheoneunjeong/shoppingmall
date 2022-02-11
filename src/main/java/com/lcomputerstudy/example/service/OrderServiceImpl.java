package com.lcomputerstudy.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcomputerstudy.example.domain.OrderInfo;
import com.lcomputerstudy.example.domain.OrderRequest;
import com.lcomputerstudy.example.domain.ReceiverInfo;
import com.lcomputerstudy.example.mapper.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;
	
	@Override
	public void insertOrderDetails(OrderRequest o) {
		orderMapper.insertOrderDetails(o);
		
	}

	@Override
	public void insertOrderInfo(OrderInfo order) {
		orderMapper.insertOrderInfo(order);
		
	}

	@Override
	public void insertReceiverInfo(ReceiverInfo receiverInfo) {
		orderMapper.insertReceiverInfo(receiverInfo);
		
	}

	@Override
	public int getOrderCode() {
		// TODO Auto-generated method stub
		return orderMapper.getOrderCode();
	}

}
