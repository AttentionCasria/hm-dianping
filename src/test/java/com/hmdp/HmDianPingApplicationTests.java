package com.hmdp;

import com.hmdp.entity.Shop;
import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.CacheClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.hmdp.utils.RedisConstants.CACHE_SHOP_KEY;

@SpringBootTest
class HmdpApplicationTests {
    @Resource
    private CacheClient cacheClient;
    @Resource
    private ShopServiceImpl shopService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testSaveShop() {
        // 预热 ID 为 1 的店铺，设置逻辑过期时间为 1小时后
        // 这样在测试期间，数据就在 Redis 里了
        Shop shop = shopService.getById(1L);
        cacheClient.setWithLogicalExpire(CACHE_SHOP_KEY+1L,shop,10L, TimeUnit.SECONDS);
    }
    // 在你的测试类中运行一次，进行数据预热
    @Test
    void prepareStock() {
        // 务必确认这里的 Key 格式要和你业务代码 Lua 脚本里拼接的一模一样
        // 常见格式是 "seckill:stock:" + id
        stringRedisTemplate.opsForValue().set("seckill:stock:9", "200");
    }

    @Test
    void loadShopData(){
        List<Shop> list = shopService.list();
        Map<Long, List<Shop>> map = list.stream().collect(Collectors.groupingBy(Shop::getTypeId));
        for (Map.Entry<Long, List<Shop>> entry : map.entrySet()) {
            //3.1.获取类型id
            Long typeId = entry.getKey();
            String key = "shop:geo:" + typeId;
            // 3.2.获取同类型的店铺的集合
            List<Shop> value = entry.getValue();
            List<RedisGeoCommands.GeoLocation<String>> locations = new ArrayList<>(value.size());
            //3.3.写入redis GEOADD  key  经度 纬度 member
             for (Shop shop : value) {
                 // stringRedisTemplate.opsForGeo().add(key, new Point(shop.getX(), shop.getY()), shop.getId().toString());
                 locations.add(new RedisGeoCommands.GeoLocation<>(
                     shop.getId().toString(),
                     new Point(shop.getX(), shop.getY())
                 ));
             }
            stringRedisTemplate.opsForGeo().add(key, locations);
        }
    }
}

