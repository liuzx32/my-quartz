package mi.data.mail;

public class MyQuartzHelper {

    // job_name
    public static String jobInfoName(int id) {
        return getJobName(MailConsts.INFO, id);
    }

    // job_name
    public static String jobItemName(int id) {
        return getJobName(MailConsts.ITEM, id);
    }

    private static String getJobName(String type, int id) {
        return String.format("JOB_%s_%d", type, id);
    }

    /* -------------------- debug -------------------- */

    public static void main(String[] args) {
        int id = 10;
        String job1 = jobInfoName(id);
        String job2 = jobItemName(id);
        System.out.println(job1);
        System.out.println(job2);
    }
}
