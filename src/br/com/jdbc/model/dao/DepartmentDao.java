package br.com.jdbc.model.dao;

import br.com.jdbc.model.entities.Department;

import java.util.List;

public interface DepartmentDao {

    public void insert(Department department);
    public void update(Department department);
    public void deleteById(Integer id);
    public Department findById(Integer id);
    public List<Department> findAll();

}
