package com.dmi.service;

import java.util.List;

import com.dmi.domain.DCategory;
import com.dmi.domain.ResponseList;

public interface CategoryService {
    List<DCategory> list();
    DCategory get(Long id);
    DCategory create(DCategory dCategory);
    Long delete(Long id);
    DCategory update(Long id, DCategory dCategory);
    ResponseList<DCategory> getPage(int pagesize, String cursorkey);
}
