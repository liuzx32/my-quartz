package mi.data.task;

import mi.data.mail.MailQuartzInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MailReportInitTask {

    private final static Logger logger = LoggerFactory.getLogger(MailReportInitTask.class);

    @Autowired
    private MailQuartzInit quartzInit;

    public void execute() {
        quartzInit.initMailAllQuartz();
    }

}