package com.nowy.android.basiclib.app;


import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.app.ActivityManager.RunningServiceInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nowy on 2015/12/31.
 */
public class AppServices {
    private final static List<Service> services = new ArrayList<Service>(); // 服务 栈



    public static void addService(Service service) {
        services.add(service);
    }

    public static void removeService(Service service) {
        services.remove(service);
    }




    /**
     * 判断服务是否运行
     *
     * @param context
     * @param clazz
     *            要判断的服务的class
     * @return
     */
    public static boolean isServiceRunning(Context context,
                                           Class<? extends Service> clazz) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningServiceInfo> services = manager.getRunningServices(100);
        for (int i = 0; i < services.size(); i++) {
            String className = services.get(i).service.getClassName();
            if (className.equals(clazz.getName())) {
                return true;
            }
        }
        return false;
    }
}
