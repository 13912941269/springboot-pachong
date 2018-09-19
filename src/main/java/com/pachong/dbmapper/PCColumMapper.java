package com.pachong.dbmapper;

import com.pachong.model.PCColum;

public interface PCColumMapper {
    int deleteByPrimaryKey(Integer columId);

    int insertSelective(PCColum record);

    PCColum selectByPrimaryKey(Integer columId);

    PCColum selectByColumName(String columName);

    int updateByPrimaryKeySelective(PCColum record);
}