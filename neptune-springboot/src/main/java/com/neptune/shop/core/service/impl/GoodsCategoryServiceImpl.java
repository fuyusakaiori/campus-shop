package com.neptune.shop.core.service.impl;

import com.neptune.shop.core.dao.GoodsCategoryMapper;
import com.neptune.shop.core.dao.GoodsInfoMapper;
import com.neptune.shop.core.dto.Message;
import com.neptune.shop.core.entity.GoodsCategory;
import com.neptune.shop.core.entity.GoodsInfo;
import com.neptune.shop.core.exception.GoodsCategoryException;
import com.neptune.shop.core.service.GoodsCategoryService;
import com.neptune.shop.core.util.enums.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GoodsCategoryServiceImpl implements GoodsCategoryService
{
    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public Message<GoodsCategory> findShopGoodsCategory(int id) {
        if (id <= 0)
            return new Message<>(State.INVALID_SHOP_ID);
        List<GoodsCategory> categories = goodsCategoryMapper.findShopGoodsCategory(id);
        return new Message<>(State.SUCCESS, categories, categories.size());
    }

    @Override
    @Transactional
    public Message<GoodsCategory> batchInsertGoodsCategory(List<GoodsCategory> categories) throws GoodsCategoryException
    {
        // 1. 传入的集合为空的时候, 就不执行插入操作
        if(categories == null || categories.isEmpty())
            return new Message<>(State.NULL);
        try{
            // 3. 在实际添加商品类型之前, 需要新增每个商品类型创建的时间
            categories.forEach((category) -> category.setCreateTime(new Date()));
            // 4. 根据返回结果确定是否添加成功, 如果没有任何的影响行数那么就是失败了
            int result = goodsCategoryMapper.batchInsertGoodsCategory(categories);
            if (result <= 0)
                throw new GoodsCategoryException("新增商品类型失败!");
        }catch (Exception e){
            // 2. 如果添加数据出现异常, 那么就需要抛出异常让事务回滚
            throw new GoodsCategoryException("新增商品类型失败![错误原因: " + e.getMessage() + "]");
        }
        return new Message<>(State.SUCCESS);
    }

    @Override
    @Transactional
    public Message<GoodsCategory> deleteGoodsCategory(int categoryId, int shopId) throws GoodsCategoryException {
        // 1. 如果传入的类型编号或者说店铺编号为空, 那么都是直接返回
        if (categoryId <= 0)
            return new Message<>(State.INVALID_CATEGORY_ID);
        if (shopId <= 0)
            return new Message<>(State.INVALID_SHOP_ID);
        // 2. 如果商品类型包含相应的商品也是不可以删除的
        GoodsInfo condition = new GoodsInfo();
        GoodsCategory category = new GoodsCategory();
        category.setGoodsCategoryId(categoryId);
        condition.setCategory(category);
        List<GoodsInfo> goods = goodsInfoMapper.findAllGoodsInfo(condition, 1, 100);
        if (!goods.isEmpty())
            return new Message<>(State.FAILURE);
        // 3. 开始删除
        try {
            int result = goodsCategoryMapper.deleteGoodsCategory(categoryId, shopId);
            // 如果影响的行数那么就需要事务回滚
            if (result <= 0)
                throw new GoodsCategoryException("删除商品类型失败!");
        }
        catch (Exception e) {
            throw new GoodsCategoryException("删除商品类型错误![错误原因: " + e.getMessage() + "]");
        }
        return new Message<>(State.SUCCESS);
    }

}
