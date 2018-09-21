package com.pachong.dbmapper;

import com.pachong.model.PCColum;

import java.util.List;
import java.util.Map;

public interface PCColumMapper {
    int deleteByPrimaryKey(Integer columId);

    int insertSelective(PCColum record);

    PCColum selectByPrimaryKey(Integer columId);

    PCColum selectByColumName(Map map);

    int updateByPrimaryKeySelective(PCColum record);

    List<PCColum> selectByParentId(Integer parentId);
}