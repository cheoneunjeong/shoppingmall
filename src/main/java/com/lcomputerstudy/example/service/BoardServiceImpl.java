package com.lcomputerstudy.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcomputerstudy.example.domain.QABoard;
import com.lcomputerstudy.example.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardMapper boardmapper;
	
	@Override
	public void insertQAPost(QABoard post) {
		boardmapper.insertQAPost1(post);
		boardmapper.insertQAPost2(post);
		
	}

	@Override
	public List<QABoard> getQABoardList() {
		// TODO Auto-generated method stub
		return boardmapper.getQABoardList();
	}

	@Override
	public QABoard getQAPostDetails(int num) {
		// TODO Auto-generated method stub
		return boardmapper.getQAPostDetails(num);
	}

	@Override
	public void insertAnswerPost(QABoard post) {
		boardmapper.insertAnswerPost1(post);
		boardmapper.insertAnswerPost2(post);
		
	}

	@Override
	public void addHit(int num) {
		boardmapper.addHit(num);
		
	}

	@Override
	public void deletePost(int num) {
		boardmapper.deletePost(num);
		
	}

}
