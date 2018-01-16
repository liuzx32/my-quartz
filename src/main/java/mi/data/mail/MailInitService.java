package mi.data.mail;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailInitService implements InitializingBean {

    @Autowired
    private MailQuartzInit quartzInit;

    public void afterPropertiesSet() throws Exception {
        quartzInit.initMailItemQuartz();
    }
}