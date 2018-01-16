package mi.data.mail;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailQuartzJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(MailQuartzJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap dataMap = jobDetail.getJobDataMap();
        sendMailData(dataMap);
    }

    private void sendMailData(JobDataMap dataMap) {
        int id = dataMap.getIntValue(MailConsts.ID);
        String mark = dataMap.getString(MailConsts.MARK);
        String mail_id = String.format("%s, %d", mark, id);
        logger.info("send mail:({})", mail_id);
        Runnable task = (Runnable) dataMap.get(MailConsts.TASK);
        try {
            task.run();
        } catch (Exception e) {
            logger.error("quartz send mail:({}) error:{}", mail_id, e.getMessage());
            e.printStackTrace();
        }
    }
}
