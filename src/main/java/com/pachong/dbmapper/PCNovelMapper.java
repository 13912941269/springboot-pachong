package com.pachong.dbmapper;

import com.pachong.model.PCNovel;

public interface PCNovelMapper {
    int deleteByPrimaryKey(Integer novelId);

    int insertSelective(PCNovel record);

    PCNovel selectByPrimaryKey(Integer novelId);

    int updateByPrimaryKeySelective(PCNovel record);
}