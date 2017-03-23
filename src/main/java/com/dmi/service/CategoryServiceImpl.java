package com.dmi.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmi.dao.CategoryDaoImpl;
import com.dmi.domain.DCategory;
import com.dmi.domain.ResponseList;

@Service("categoryService")
public class CategoryServiceImpl {

    @Autowired
    private CategoryDaoImpl categoryDao;
    
    public Collection<DCategory> getAll() {
        return categoryDao.findByFields(null);
    }
    
    public Collection<DCategory> findByFields(DCategory domain) {
        return categoryDao.findByFields(domain);
    }
    
    public ResponseList<DCategory> findPage(String cursor, int pageSize) {
        return categoryDao.findByPage(cursor, pageSize);
    }
    
    public DCategory findById(Long id) {
        return categoryDao.findById(id);
    }
    
    public DCategory create(DCategory category) {
        //create new datastore
       return categoryDao.add(category);
    }
    
    public DCategory update(DCategory category) {
        categoryDao.update(category);
        return category;
    }
    
    public void remove(long id) {
        categoryDao.delete(id);
    }
}
