package mi.data.mail;

import com.google.common.collect.Maps;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 #job相关的builder
 import static org.quartz.JobBuilder.*;
 #trigger相关的builder
 import static org.quartz.TriggerBuilder.*;
 import static org.quartz.SimpleScheduleBuilder.*;
 import static org.quartz.CronScheduleBuilder.*;
 import static org.quartz.DailyTimeIntervalScheduleBuilder.*;
 import static org.quartz.CalendarIntervalScheduleBuilder.*;
 #日期相关的builder
 import static org.quartz.DateBuilder.*;
 */
public class MailQuartzJobs {

    private static final Logger logger = LoggerFactory.getLogger(MailQuartzJobs.class);
    //
    private static String JOB_GROUP_NAME = "job_group_mail";
    private static String TRIGGER_GROUP_NAME = "trigger_group_mail";
    private static SchedulerFactory sf = new StdSchedulerFactory();

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * jobName结合info/item/link+id命名，
     */
    public static void addJob(String jobName, Map<String, Object> mapData, Class<? extends Job> jobClass, String cronExpression) throws SchedulerException, ParseException {
        addJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, mapData, jobClass, cronExpression);
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass 任务
     * @param cronExpression 时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Map<String, Object> mapData, Class<? extends Job> jobClass, String cronExpression)
            throws SchedulerException, ParseException {
        Scheduler scheduler = sf.getScheduler();
        JobDataMap dataMap = new JobDataMap(mapData);
        // 定义一个JobDetail
        JobDetail jobDetail = newJob(jobClass).withIdentity(jobName, jobGroupName).usingJobData(dataMap).build();
        // 定义一个Trigger, 触发器名,触发器组,cron表达式
        CronScheduleBuilder cronScheduleBuilder = cronSchedule(cronExpression);
        CronTrigger cronTrigger = newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronScheduleBuilder).build();
        //
        checkJob(scheduler, jobDetail);
        checkTrigger(scheduler, cronTrigger);
        scheduler.scheduleJob(jobDetail, cronTrigger);
        // start
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    public static void delJob(String jobName, Class<? extends Job> jobClass, String cronExpression) throws SchedulerException, ParseException {
        delJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, jobClass, cronExpression);
    }

    public static void delJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass, String cronExpression)
            throws SchedulerException, ParseException {
        Scheduler scheduler = sf.getScheduler();
        JobDetail jobDetail = newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        CronScheduleBuilder cronScheduleBuilder = cronSchedule(cronExpression);
        CronTrigger cronTrigger = newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronScheduleBuilder).build();
        checkJob(scheduler, jobDetail);
        checkTrigger(scheduler, cronTrigger);
    }

    private static void checkJob(Scheduler scheduler, JobDetail jobDetail) {
        try {
            JobKey jobKey = jobDetail.getKey();
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        } catch (Exception e) {
            logger.error("check job error:{}", e.getMessage());
        }
    }

    private static void checkTrigger(Scheduler scheduler, CronTrigger cronTrigger) {
        try {
            TriggerKey triggerKey = cronTrigger.getKey();
            if (scheduler.checkExists(triggerKey)) {
                scheduler.pauseTrigger(triggerKey);
            }
        } catch (Exception e) {
            logger.error("check trigger error:{}", e.getMessage());
        }
    }

    /* ----------------------------- debug ----------------------------- */

    public static void main(String[] args) throws Exception {
        String jobName = "job1";
        String cron1 = "0/3 * * ? * TUE *";
        Map<String, Object> mapData = Maps.newHashMap();
        mapData.put("id", 1000);
        mapData.put("name", "world");
        addJob(jobName, mapData, MyQuartzTask.class, cron1);
        //
        Thread.sleep(   12000);
        String cron2 = "0 * * ? * TUE *";
        addJob(jobName, mapData, MyQuartzTask.class, cron2);
    }
}
