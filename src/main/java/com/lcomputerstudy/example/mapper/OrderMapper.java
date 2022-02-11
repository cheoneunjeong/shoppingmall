package com.lcomputerstudy.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lcomputerstudy.example.domain.OrderInfo;
import com.lcomputerstudy.example.domain.OrderRequest;
import com.lcomputerstudy.example.domain.ReceiverInfo;

@Mapper
public interface OrderMapper {

	void insertOrderDetails(OrderRequest o);

	void insertOrderInfo(OrderInfo order);

	void insertReceiverInfo(ReceiverInfo receiverInfo);

	int getOrderCode();

}
