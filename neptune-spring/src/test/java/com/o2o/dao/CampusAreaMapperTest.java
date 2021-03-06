package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.CampusArea;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>测试校园区域接口中的方法</p>
 */
public class CampusAreaMapperTest extends BaseTest
{
    @Autowired
    private CampusAreaMapper campusAreaMapper;

    @Test
    public void findAllCampusAreaMapperTest(){
        List<CampusArea> areas = campusAreaMapper.findAllCampusArea();
        areas.forEach(System.out::println);
        Assert.assertEquals(2, areas.size());
    }
}
