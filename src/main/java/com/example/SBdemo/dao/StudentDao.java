package com.example.SBdemo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.SBdemo.model.Student;

@Mapper
public interface StudentDao {

	List<Student> queryStudentByName(String name);

	void delStduByName(String name);

}
