import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // 1.设置时间格式
        /*
         * yyyy-MM-dd   :   年-月-日  2023-03-25
         * yyyy.MM.dd   :   年.月.日  2023.03.25
         * dd.MM.yyyy   :   日.月.年  25.03.2023
         * yyyy-MM-dd HH:mm:ss   :   年-月-日- 时:分:秒  2023-03-25 16:57:35
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 2.获取时间示例
        // 示例1
        Date date = new Date();
        System.out.println("example 1: " + dateFormat.format(new Date()));  //2023-03-25 17:27:20

        // 示例2
        Date date1 = new Date();
        String year = String.format("%tY", date1);       //  %tY - 2023  ；  %ty - 23
        String month = String.format("%tb", date1);      //  %tb - 三月  ；  %tB - 三月
        String day = String.format("%te", date1);        //  %te - 25
        System.out.println("example 1: " + year + "." + month + "." + day); //2023.三月.25

        // 示例3
        Calendar instance = Calendar.getInstance();
        int year3 = instance.get(Calendar.YEAR);
        int month3 = instance.get(Calendar.MONTH);
        int date3 = instance.get(Calendar.DATE);
        int hour3 = instance.get(Calendar.HOUR_OF_DAY);
        int minute3 = instance.get(Calendar.MINUTE);
        int second3 = instance.get(Calendar.SECOND);
        System.out.println("example 3: " + year3 + "." + month3 + "." + date3 + " " + hour3 + ":" + minute3 + ":" + second3);//2023.2.25 17:27:20

        // 示例4
        System.out.println("example 2: " + dateFormat.format(System.currentTimeMillis()));//2023-03-25 17:27:20
    }
}