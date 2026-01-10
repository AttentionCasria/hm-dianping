package com.hmdp.service.impl;

import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result redisOrderByAsc(String sort) {
        // 假设入参 sort 就是 Redis 的 key
        List<String> jsonList = stringRedisTemplate.opsForList().range(sort, 0, -1);
        if (jsonList == null || jsonList.isEmpty()) {
            // 3. 缓存未命中，查询数据库
            List<ShopType> shopTypeList = query().orderByAsc("sort").list();

            // 4. 将数据库结果写入 Redis (建议判空，防止无效写入)
            if (shopTypeList != null && !shopTypeList.isEmpty()) {
                stringRedisTemplate.opsForList().rightPushAll(sort, shopTypeList.stream()
                        .map(JSONUtil::toJsonStr)
                        .collect(Collectors.toList()));
                return Result.ok(shopTypeList);
            }
            return Result.fail("查询失败");
        }
        List<ShopType> redisShopTypeList = jsonList.stream().map(json -> JSONUtil.toBean(json, ShopType.class)).collect(Collectors.toList());
        return Result.ok(redisShopTypeList);
    }
}
