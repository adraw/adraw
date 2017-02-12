package io.github.adraw.framework.service;

import java.util.List;

import io.github.adraw.framework.model.Student;

public interface IStudentService {
	public List<Student> getList();
	public List<Student> getList4Ds1();
	public List<Student> getList4Ds2();
	public List<Student> getAll();
}
