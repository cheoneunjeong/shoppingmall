package com.lcomputerstudy.example.service;

import com.lcomputerstudy.example.domain.OrderInfo;
import com.lcomputerstudy.example.domain.OrderRequest;
import com.lcomputerstudy.example.domain.ReceiverInfo;

public interface OrderService {

	void insertOrderDetails(OrderRequest o);

	void insertOrderInfo(OrderInfo order);

	void insertReceiverInfo(ReceiverInfo receiverInfo);

	int getOrderCode();

}
