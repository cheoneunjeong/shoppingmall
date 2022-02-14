package com.lcomputerstudy.example.service;

import java.util.List;

import com.lcomputerstudy.example.domain.QABoard;

public interface BoardService {

	void insertQAPost(QABoard post);

	List<QABoard> getQABoardList();

	QABoard getQAPostDetails(int num);

	void insertAnswerPost(QABoard post);

	void addHit(int num);

	void deletePost(int num);

}
