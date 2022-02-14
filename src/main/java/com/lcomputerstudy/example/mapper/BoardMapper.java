package com.lcomputerstudy.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lcomputerstudy.example.domain.QABoard;

@Mapper
public interface BoardMapper {

	void insertQAPost1(QABoard post);

	void insertQAPost2(QABoard post);

	List<QABoard> getQABoardList();

	QABoard getQAPostDetails(int num);

	void insertAnswerPost1(QABoard post);

	void insertAnswerPost2(QABoard post);

	void addHit(int num);

	void deletePost(int num);

}
