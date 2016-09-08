package com.diyishuai.dao;

import com.diyishuai.annotation.MyBatisRepository;
import com.diyishuai.domain.StreamTest;

import java.util.List;
import java.util.Map;

/**
 * Created by Bruce on 16/8/9.
 */
@MyBatisRepository
public interface StreamDao {

    public Integer create(StreamTest streamTest);

    public List<StreamTest> getList(Map parmeter);

    public Integer batchCreate(List<StreamTest> list);

    public List<StreamTest> dealWith(Map parmeter);
}
