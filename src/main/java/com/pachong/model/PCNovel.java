package com.pachong.model;

public class PCNovel {
    private Integer novelId;

    private Integer chapterId;

    private String vovelDetail;

    //自定义章节名称
    private String chapterTitle;

    public Integer getNovelId() {
        return novelId;
    }

    public void setNovelId(Integer novelId) {
        this.novelId = novelId;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getVovelDetail() {
        return vovelDetail;
    }

    public void setVovelDetail(String vovelDetail) {
        this.vovelDetail = vovelDetail == null ? null : vovelDetail.trim();
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }
}