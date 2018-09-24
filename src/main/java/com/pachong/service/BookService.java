package com.pachong.service;

import com.github.pagehelper.Page;

import java.util.Map;

public interface BookService {

    Map insertBookIn(String url);

    void exChangeText(Integer pageNum);

}
