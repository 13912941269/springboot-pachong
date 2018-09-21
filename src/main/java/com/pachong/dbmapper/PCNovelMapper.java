package com.pachong.dbmapper;

import com.pachong.model.PCNovel;

import java.util.List;
import java.util.Map;

public interface PCNovelMapper {
    int deleteByPrimaryKey(Integer novelId);

    int insertSelective(PCNovel record);

    PCNovel selectByPrimaryKey(Integer novelId);

    int updateByPrimaryKeySelective(PCNovel record);

    List<PCNovel> selectByChapterId(Integer chapterId);

    Integer selectByPreChapterId(Map map);

    Integer selectByNextChapterId(Map map);
}