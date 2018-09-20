package com.example.SBdemo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.SBdemo.model.Student;

@Mapper
public interface StudentDao {

	List<Student> selectStudentByName(String name);

	void delStduByName(String name);

	List<Student> readStudentBySex(String sex);

}
