package mi.data.mail;

import org.quartz.*;

import java.util.Date;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyQuartzTask implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap dataMap = jobDetail.getJobDataMap();
        //
        String name = dataMap.getString("name");
        int number = dataMap.getIntValue("id");
        String line = String.format("Hello to %s for %d at ", name, number);
        System.out.println(line + new Date());
    }
}
