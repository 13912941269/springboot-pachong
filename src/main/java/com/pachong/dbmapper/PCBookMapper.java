package com.pachong.dbmapper;

import com.pachong.model.PCBook;

import java.util.List;
import java.util.Map;

public interface PCBookMapper {
    int deleteByPrimaryKey(Integer bookId);

    int insertSelective(PCBook record);

    PCBook selectByPrimaryKey(Integer bookId);

    PCBook selectByBookName(String bookName);

    List<PCBook> selectByParam(Map map);

    int updateByPrimaryKeySelective(PCBook record);
}