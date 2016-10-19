package com.diyishuai.controller;

import com.diyishuai.dao.PersonDao;
import com.diyishuai.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

/**
 * Created by Bruce on 16/7/21.
 */
@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonDao personDao;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Person save(){
        Person p = new Person(1+"","zuishuai", 18);
        Location loc1 = new Location("上海", " 2009");
        Location loc2 = new Location("山西", " 2008");
        Location loc3 = new Location("云南", " 2014");
        Location loc4 = new Location("天空", " 2017");
        Collection<Location> locations = p.getLocations();
        locations.add(loc1);
        locations.add(loc2);
        locations.add(loc3);
        locations.add(loc4);
        p.setLocations(locations);
        return personDao.save(p);
    }

    @ResponseBody
    @RequestMapping(value = "/q1",method = RequestMethod.GET)
    public Person q1(String name) {
        return personDao.findByName(name);
    }

    @ResponseBody
    @RequestMapping(value = "/q2",method = RequestMethod.GET)
    public List<Person> q2(Integer age){
        return personDao.withQueryFindByAge(age);
    }



}
