package com.neptune.shop.service;


import com.neptune.shop.core.entity.CampusArea;
import com.neptune.shop.core.exception.CampusAreaException;
import com.neptune.shop.core.service.CacheService;
import com.neptune.shop.core.service.CampusAreaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampusAreaServiceTest
{
    @Resource
    private CampusAreaService campusAreaService;
    @Resource
    private CacheService cacheService;

    @Test
    public void findAllCampusAreaService() throws CampusAreaException {
        List<CampusArea> areas = campusAreaService.findAllCampusArea();
        Assert.assertEquals(2, areas.size());
    }
}
