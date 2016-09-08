package com.diyishuai.controller;

import com.diyishuai.dao.StreamDao;
import com.diyishuai.domain.StreamTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bruce on 16/8/9.
 */
@RestController
@RequestMapping("stream")
public class StreamTestController {


    @Autowired
    private StreamDao streamDao;

    @RequestMapping("/batchCreate")
    public void batchCreate(@RequestParam Integer size){
        int flag = 1;
        List linkedList = new LinkedList<StreamTest>();
        for (int i=1; i<=size; i++){
            linkedList.add(new StreamTest(null,i,size-i+1,flag+""));
            if (i%10000==0){
                streamDao.batchCreate(linkedList);
                linkedList.clear();
                flag++;
            }
        }
        if (!linkedList.isEmpty())
            streamDao.batchCreate(linkedList);
        linkedList.clear();
    }


    @ResponseBody
    @RequestMapping("/dealWith")
    public List dealWith() {
        List<StreamTest> streamTests = streamDao.dealWith(null);
        return streamTests;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List getList(){
        List<StreamTest> list = streamDao.getList(null);
        return list;
    }
}
