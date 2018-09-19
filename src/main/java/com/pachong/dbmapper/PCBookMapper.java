package com.pachong.dbmapper;

import com.pachong.model.PCBook;

public interface PCBookMapper {
    int deleteByPrimaryKey(Integer bookId);

    int insertSelective(PCBook record);

    PCBook selectByPrimaryKey(Integer bookId);

    PCBook selectByBookName(String bookName);

    int updateByPrimaryKeySelective(PCBook record);
}