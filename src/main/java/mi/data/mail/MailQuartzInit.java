package mi.data.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("quartzInit")
public class MailQuartzInit {

    private final static Logger logger = LoggerFactory.getLogger(MailQuartzInit.class);


    public void initMailAllQuartz() {
        println("==========Quartz Init==========");
        try {
            initMailInfoQuartz();
            initMailItemQuartz();
        } catch (Exception e) {
            e.printStackTrace();
        }
        println("==========Quartz Over==========");
    }

    public void initMailInfoQuartz() {
        println("##########Quartz Info##########");
        println("##########Quartz Over##########");
    }

    public void initMailItemQuartz() {
        println("##########Quartz Item##########");
        println("##########Quartz Over##########");
    }

    private static void println(String line) {
        System.out.println(line);
        logger.info(line);
    }
}
