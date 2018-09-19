package com.example.SBdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SBdemo.dao.StudentDao;
import com.example.SBdemo.model.Student;

@Service
public class StudentService {
	@Autowired
	private StudentDao stuDao;

	public List<Student> queryByName(String name) {
		List<Student> res = stuDao.queryStudentByName(name);
		if (res.isEmpty()) {
			throw new RuntimeException("查无此人");
		}
		return res;
	}

	public void delStuByName(String name) {
		stuDao.delStduByName(name);
	}
}
