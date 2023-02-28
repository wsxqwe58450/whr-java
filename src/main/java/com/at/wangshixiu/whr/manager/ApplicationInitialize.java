package com.at.wangshixiu.whr.manager;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author 王世秀
 * @Date 2023/2/27
 * @Description
 */

@Slf4j
@Component
public class ApplicationInitialize {
    /**
     * 使用自定义线程池异步方法  AsyncConfig类中的要相同
     *   // 文章阅读量+1
     * @return
     **/
    @Async("customExecutor")
    public void updateReadCount(String id) {
        // TODO 模拟耗时操作
        try {
            Thread.sleep(3000);
            log.info("图书【{}】的阅读量+1",id);

        } catch (InterruptedException e) {
           log.error("异步缓存字典数据失败");
        }
        log.info("更新任务线程:{}",Thread.currentThread().getName());
    }
}
