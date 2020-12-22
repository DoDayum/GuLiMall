package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        //查出所有的1级分类
//        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntities);
//        System.out.println("最终所需时间:"+(System.currentTimeMillis()-l));
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJSON() {
        Map<String, List<Catelog2Vo>> catalogJSON = categoryService.getCatalogJSON();
        return catalogJSON;
    }

    /**
     * Test专用
     * @return
     */
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {

        RLock lock = redisson.getLock("myLock");
        lock.lock();

        //10秒钟自动解锁
//        lock.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println("加锁成功,执行业务\t" + Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (InterruptedException e) {

        } finally {
            System.out.println("释放锁\t" + Thread.currentThread().getId());
            lock.unlock();
        }

        return "hello " + Thread.currentThread().getId();

    }

    /**
     * 写锁
     * @return
     */
    @ResponseBody
    @GetMapping("/write")
    public String writeValue() {

        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
//        ReentrantReadWriteLock writeLock = new ReentrantReadWriteLock();
        String s = "";
        RLock rLock = lock.writeLock();
        try {
            rLock.lock();
            System.out.println("写锁成功\t"+Thread.currentThread().getId());
            s = UUID.randomUUID().toString();
            Thread.sleep(10);
            redisTemplate.opsForValue().set("writeValue",s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
        }
        return s;

    }

    /**
     * 读锁
     * @return
     */
    @ResponseBody
    @GetMapping("/read")
    public String readValue() {

        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        RLock rLock = lock.readLock();
        rLock.lock();
        try {
            System.out.println("读锁成功\t"+Thread.currentThread().getId());
            s =redisTemplate.opsForValue().get("writeValue");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
        }
        return s;

    }

    /**
     * 停车方法
     * @return “ok”
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping("/park")
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
//        park.acquire();//获取一个值，占用
        boolean b = park.tryAcquire();
        String ansuer = "";
        if (b){
            ansuer = "有车位";
        }else
            ansuer = "无车位";
        return "ok => "+ansuer;
    }

    /**
     * 车开走
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping("/go")
    public String go() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        park.release();//释放一个值
        return "ok";
    }

    @ResponseBody
    @GetMapping("/lockDoor")
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);
        door.await();//等待闭锁完成
        return "放假";
    }

    @ResponseBody
    @GetMapping("/gogogo/{id}")
    public String gogogo(@PathVariable("id") Long id){
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();
        return id+"\t班的人都走了";
    }

}
