package com.pachong.dbmapper;

import com.pachong.model.PCChapter;

import java.util.List;
import java.util.Map;

public interface PCChapterMapper {
    int deleteByPrimaryKey(Integer chapterId);

    int insertSelective(PCChapter record);

    PCChapter selectByPrimaryKey(Integer chapterId);

    PCChapter selectByChapterNameAndBookId(Map map);

    List<PCChapter> selectByBookId(Integer bookId);

    Integer selectByPreChapterId(Map map);

    Integer selectByNextChapterId(Map map);
}