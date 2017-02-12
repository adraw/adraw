package io.github.adraw.framework.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import io.github.adraw.framework.config.TargetDataSource;
import io.github.adraw.framework.mapper.StudentMapper;
import io.github.adraw.framework.model.Student;
import io.github.adraw.framework.service.IStudentService;

@Service
public class StudentService implements IStudentService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StudentMapper studentMapper;

	public List<Student> getList() {
		String sql = "SELECT ID,NAME FROM STUDENT";
		return (List<Student>) jdbcTemplate.query(sql,new RowMapper<Student>() {
					@Override
					public Student mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Student stu = new Student();
						stu.setId(rs.getInt("ID"));
						stu.setName(rs.getString("NAME"));
						return stu;
					}

				});
	}

	@TargetDataSource(name="ds1")
	public List<Student> getList4Ds1() {
		String sql = "SELECT ID,NAME FROM STUDENT";
		return (List<Student>) jdbcTemplate.query(sql,new RowMapper<Student>() {
			@Override
			public Student mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Student stu = new Student();
				stu.setId(rs.getInt("ID"));
				stu.setName(rs.getString("NAME"));
				return stu;
			}
			
		});
	}
	
	@TargetDataSource(name="ds2")
	public List<Student> getList4Ds2() {
		String sql = "SELECT ID,NAME FROM STUDENT";
		return (List<Student>) jdbcTemplate.query(sql,new RowMapper<Student>() {
			@Override
			public Student mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Student stu = new Student();
				stu.setId(rs.getInt("ID"));
				stu.setName(rs.getString("NAME"));
				return stu;
			}
			
		});
	}
	
	@TargetDataSource(name="ds1")
	public List<Student> getAll() {
        return studentMapper.selectAll();
	}
	
}
