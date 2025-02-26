package com.boost.TaskActions;

import static com.boost.Main.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class failed {
    public static void run(String reason) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(Red + "BUILD FAILED!" + Reset);
        System.out.println(Red + "* What went wrong:");
        System.out.println(reason + Reset);
        System.out.println("Finished at: "+ dateFormat.format(date) + "\n");
    }
}