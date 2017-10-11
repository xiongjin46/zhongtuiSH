package com.push.PushMerchant.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
    private static SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取当前时间，返回yyyy-MM-dd HH:mm:ss格式
     *
     * @return
     */
    public static String getNowTime() {
        return getDateandSecondFromMillisecond(System.currentTimeMillis());
    }

    /**
     * 获取当前时间，返回yyyy-MM-dd HH:mm:ss.SSS格式
     *
     * @return
     */
    public static String getNowTimeMills() {
        try {
            return getDateandMillisecondFromMillisecond(System.currentTimeMillis());
        } catch (Exception ex) {
            return "";
        }
    }

    // public static long getNowTimeLong() {
    // try {
    // return System.currentTimeMillis() * 1000;
    // } catch (Exception ex) {
    // return 0;
    // }
    // }

    /**
     * 将毫秒换为年月日 时分秒
     *
     * @param time
     * @return
     */
    public static String getDateandSecondFromMillisecond(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyy_MM_dd_HH_mm_ssTimeFormat.get();
        return df.format(time);
    }

    /**
     * 将毫秒换为年月日 时分秒
     *
     * @param time
     * @return
     */
    public static String getDateandMillisecondFromMillisecond(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyy_MM_dd_HH_mm_ssSSSTimeFormat.get();
        return df.format(time);
    }

    /*
     * 将毫秒换算为年月日
     */
    public static String getDateFromMillisecond(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyy_MM_ddTimeFormat.get();
        return df.format(time);
    }

    /**
     * get current day formate is yyyyMMdd
     *
     * @return
     */
    public static String getCurrentDay(Long time) {
        if (null == time) {
            return null;
        }
        final SimpleDateFormat df = yyyyMMddTimeFormat.get();
        return df.format(time);
    }

    //
    // public static String getDateFromMillisecondMD(Long time) {
    // if (null == time) {
    // return null;
    // }
    // final SimpleDateFormat df = MM_ddTimeFormat.get();
    // return df.format(time);
    // }

    /**
     * 判断改时间是否在当天
     *
     * @param time
     * @return
     */
    // public static boolean isInThisDay(long time) {
    // return getDayFromCurrent(time) == 0;
    // }
    //
    // /**
    // * 判断该时间是否在这个星期内
    // *
    // * @param time
    // * @return
    // */
    // public static boolean isInThisWeek(long time) {
    // if (getDayFromCurrent(time) < 7) {
    // return true;
    // }
    // return false;
    // }

    /**
     * 判断该时间是否在制定的周期内
     *
     * @param time
     * @param period
     * @return
     */
    // public static boolean isInThePeriod(long time, int period) {
    // if (getDayFromCurrent(time) < period) {
    // return true;
    // }
    // return false;
    // }
    //
    // /**
    // * 判断是否在一个月以前
    // *
    // * @param time
    // * @return
    // */
    // public static boolean beforeAMonth(long time) {
    // if (getDayFromCurrent(time) > 30) {
    // return true;
    // }
    // return false;
    // }
    //
    // /**
    // * 得到与time距离的时间，因为getTruncateTimeToday()比较耗时，为大量调用这个方法的函数做优化 0为今天以内，n 大于 0
    // * 即表示n天前
    // *
    // * @param time
    // * @param todayTime
    // * @return
    // */
    // public static int getDayFromCurrent(long time, long todayTime) {
    // if (time - todayTime > 0) {
    // return 0;
    // }
    //
    // long dayTime = 1000 * 60 * 60 * 24;
    // long deltaTime = todayTime - time;
    // return (int) (deltaTime / (float) dayTime) + 1;
    // }

    /**
     * 得到与time距离的时间 0为今天以内，n 大于 0 即表示n天前
     *
     * @param time
     * @return
     */
    // public static int getDayFromCurrent(long time) {
    // long todayTime = getTruncateTimeToday();
    // if (time - todayTime > 0) {
    // return 0;
    // }
    //
    // long dayTime = 1000 * 60 * 60 * 24;
    // long deltaTime = todayTime - time;
    // return (int) (deltaTime / (float) dayTime) + 1;
    // }


    // public static String getCurrentDayLogTest() {
    // return "launche_time_log_" + TimeUtil.getCurrentDay() + ".txt";
    // }
    //
    // /**
    // * 得到今天0点0时0分0秒0毫秒的时间值
    // *
    // * @return
    // */
    // public static long getTruncateTimeToday() {
    // GregorianCalendar cal = new GregorianCalendar();
    // cal.set(Calendar.HOUR_OF_DAY, 0);
    // cal.set(Calendar.MINUTE, 0);
    // cal.set(Calendar.SECOND, 0);
    // cal.set(Calendar.MILLISECOND, 0);
    // return cal.getTime().getTime();
    // }
    //
    // public static long getTimeBeforeDays(int days) {
    // long currentTime = System.currentTimeMillis();
    // long resultTime = currentTime - 1000l * 60 * 60 * 24 * days;
    // if (resultTime < 0) {
    // return 0;
    // }
    // return resultTime;
    // }

    private static ThreadLocal<SimpleDateFormat> currentYearTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM月dd日");
        }
    };

    private static ThreadLocal<SimpleDateFormat> otherYearTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yy年MM月dd日");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyyMMddTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_ddTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static ThreadLocal<SimpleDateFormat> MM_ddTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm_ssTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm_ssSSSTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
    };
    private static ThreadLocal<SimpleDateFormat> HH_mm_TimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };
    private static ThreadLocal<SimpleDateFormat> MM_dd_HH_mm_Timeformat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM月dd日 HH:mm");
        }
    };
    private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HH_mm_Timeformat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        }
    };

    private static ThreadLocal<SimpleDateFormat> yyyyTimeformat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy");
        }
    };

    /**
     * 下载完成时间显示
     *
     * @param downloadEndTime
     * @return
     */
    public static String getDownloadFinishDisplay(long downloadEndTime) {
        if (downloadEndTime == 0) {
            return "";
        }
        long now = System.currentTimeMillis();
        long timeInterval = now - downloadEndTime;
        // 时间显示规则：
        // 1分钟以内：刚刚
        // 1小时内：XX分钟前
        // 24小时内： XX小时前
        // 一年内：X月X日
        // 大于一年：XX年X月X日
        if (timeInterval < 60 * 1000) {
            return "刚刚";
        } else if (timeInterval < 60 * 60 * 1000) {
            return (timeInterval / (60 * 1000)) + "分钟前";
        }

        if (timeInterval < 24 * 60 * 60 * 1000) {
            return (timeInterval / (60 * 60 * 1000)) + "小时前";
        }

        Date date = new Date(now);
        Date saveDate = new Date(downloadEndTime);

        // if(date.getYear()==saveDate.getYear() &&
        // date.getMonth()==saveDate.getMonth() &&
        // date.getDate()==saveDate.getDate())
        // {
        // return todayTimeFormat.get().format(saveDate);
        // }
        if (date.getYear() == saveDate.getYear()) {
            return currentYearTimeFormat.get().format(saveDate);
        }
        return otherYearTimeFormat.get().format(saveDate);
    }

    /**
     * 获取time与当前时间相隔的天数
     *
     * @return
     */
    // public static long getDifferDay(long time) {
    // long currentTime = System.currentTimeMillis();
    // long differ = currentTime - time;
    // return differ / (1000 * 60 * 60 * 24);
    // }
    public static void main(String[] args) {
        System.out.println(getDownloadFinishDisplay(System.currentTimeMillis() - 3 * 1000));
        System.out.println(getDownloadFinishDisplay(System.currentTimeMillis() - 70 * 1000));
        System.out.println(getDownloadFinishDisplay(System.currentTimeMillis() - 3600 * 1000 * 15));
        System.out.println(getDownloadFinishDisplay(System.currentTimeMillis() - 3600l * 1000 * 24 * 35));
        System.out.println(getDownloadFinishDisplay(System.currentTimeMillis() - 3600l * 1000 * 24 * 456));
    }

    /**
     * 将日期转换为毫秒值
     *
     * @param date
     * @return
     */
    public static long getMs(String date) {
        long time = 0;
        try {
            time = sdfAll.parse(date).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 毫秒转年月日
     *
     * @param ms
     * @return
     */
    public static String getYYMMDD(long ms) {
        return sdfAll.format(ms);
    }


    public static String getChineseStyleTimeFormat(long time) {
        GregorianCalendar now = new GregorianCalendar();
        int seconds_of_day = 24 * 60 * 60 * 1000;
        String dateFormat = "";
        // today HH:mm
        GregorianCalendar yesterday = new GregorianCalendar(now.get(GregorianCalendar.YEAR), now.get(GregorianCalendar.MONTH), now.get(GregorianCalendar.DAY_OF_MONTH) - 1);
        long in24h = time - yesterday.getTimeInMillis();
        if (in24h > seconds_of_day && in24h <= 2 * seconds_of_day) {
            dateFormat = " " + HH_mm_TimeFormat.get().format(time) + " ";
        } else if (in24h > 0 && in24h <= seconds_of_day) {
            dateFormat = " 昨天 " + HH_mm_TimeFormat.get().format(time) + " ";
        } else {
            GregorianCalendar target = new GregorianCalendar();
            target.setTimeInMillis(time);
            // MM-dd HH:mm
            if (now.get(GregorianCalendar.YEAR) == target.get(GregorianCalendar.YEAR)) {
                dateFormat = MM_dd_HH_mm_Timeformat.get().format(time);
            } else {
                // 年月日 HH:mm
                dateFormat = " " + yyyy_MM_dd_HH_mm_Timeformat.get().format(time) + " ";
            }
        }
        return dateFormat;
    }

    public static String getYYYYMMddDate(long timeStamp) {
        return yyyy_MM_ddTimeFormat.get().format(timeStamp * 1000);
    }

    public static String getMMddDate(long timeStamp) {
        return MM_ddTimeFormat.get().format(timeStamp * 1000);
    }

    public static String getBirthday(long birthday) {
        Integer birday = Integer.valueOf(yyyyTimeformat.get().format(birthday));
        long now = System.currentTimeMillis();
        Integer newday = Integer.valueOf(yyyyTimeformat.get().format(now));
        return newday - birday + "";
    }
}