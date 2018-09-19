package com.pachong.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuartzService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron="0 55 23 * * ?")
    public void timerToNow(){

    }
}
