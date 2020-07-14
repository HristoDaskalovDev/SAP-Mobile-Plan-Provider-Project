package com.hristodaskalov.mobileplanprovider.scheduler;

import com.hristodaskalov.mobileplanprovider.service.PhonePlanService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
public class ScheduledConfiguration implements SchedulingConfigurer {

    private final PhonePlanService phonePlanService;

    public ScheduledConfiguration(PhonePlanService phonePlanService) {
        this.phonePlanService = phonePlanService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix("scheduler-thread");
        threadPoolTaskScheduler.initialize();
        deactivateOverduePhonePlanJob(threadPoolTaskScheduler);
        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

    private void deactivateOverduePhonePlanJob(TaskScheduler scheduler) {
        scheduler.schedule(phonePlanService::deactivateOverduePlans, triggerContext -> {
            //String cronExp = "0/5 * * * * ?"; // every half a second
            String cronExp = "0 0 0 * * *";
            return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
        });
    }
}
