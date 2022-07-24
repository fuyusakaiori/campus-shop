package com.fuyusakaiori.csms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuyusakaiori.csms.cache.JedisUtil;
import com.fuyusakaiori.csms.dao.ShopCategoryMapper;
import com.fuyusakaiori.csms.entity.ShopCategory;
import com.fuyusakaiori.csms.exception.ShopCategoryException;
import com.fuyusakaiori.csms.service.ShopCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ShopCategoryServiceImpl implements ShopCategoryService
{
    @Resource
    private ShopCategoryMapper shopCategoryMapper;

    @Resource
    private JedisUtil.Keys keys;
    @Resource
    private JedisUtil.Strings strings;


    /**
     * <h3>查询所有的父类型</h3>
     */
    @Override
    @Transactional
    public List<ShopCategory> findAllShopCategory() {
        String key = ShopCategoryService.SHOPCATEGORYLIST + "-parent";
        ObjectMapper mapper = new ObjectMapper();

        return findShopCategoryFromCache(key, mapper, null);
    }

    /**
     * @param category 父类型
     * @return 店铺类型列表
     */
    @Override
    @Transactional
    public List<ShopCategory> findAllShopCategory(ShopCategory category) {
        String key = ShopCategoryService.SHOPCATEGORYLIST;
        ObjectMapper mapper = new ObjectMapper();
        if (category != null && category.getParentCategoryId() >= 1){
            key += "-" + category.getParentCategoryId();
        }else if (category != null){
            key += "-children";
        }
        return findShopCategoryFromCache(key, mapper, category);
    }

    private List<ShopCategory> findShopCategoryFromCache(String key, ObjectMapper mapper, ShopCategory category){
        List<ShopCategory> categories;
        if (!keys.isExist(key)){
            String categoryListJson;
            try {
                categories = shopCategoryMapper.findAllShopCategory(category);
                categoryListJson = mapper.writeValueAsString(categories);
            }
            catch (JsonProcessingException e) {
                throw new ShopCategoryException(e.getMessage());
            }
            strings.set(key, categoryListJson);
        }else{
            String categoryListJson = strings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try {
                categories = mapper.readValue(categoryListJson, javaType);
            }
            catch (IOException e) {
                throw new ShopCategoryException(e.getMessage());
            }
        }
        return categories;
    }


}
