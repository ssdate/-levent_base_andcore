package lib.snail.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DoubleFormatUtil {

    /**
     * @param number 需要保留两位的数
     * @return
     */
    public double doubleFormat1(double number) {
        // 将double类型转为BigDecimal
        BigDecimal bigDecimal = new BigDecimal(number);
        // 保留两位小数,并且四舍五入
        return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param number 需要保留两位的数
     * @return
     */
    public static double doubleFormat2(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return Double.valueOf(decimalFormat.format(number));
    }

    /**
     * @param number 需要保留两位的数
     * @return
     */
    public static double doubleFormat3(double number) {
        String result = String.format("%.2f", number);
        return Double.valueOf(result);
    }

    /**
     * @param number 需要保留两位的数
     * @return
     */
    public double doubleFormat4(double number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        // 保留两位小数
        numberFormat.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf(numberFormat.format(number));
    }

    /**
     * @param number 需要保留两位的数
     * @return
     */
    public double doubleFormat5(double number) {
        return (double)Math.round(number * 100) / 100;
    }

    // main
    public static void main(String[] args) {
        DoubleFormatUtil dfu = new DoubleFormatUtil();

        System.out.println(dfu.doubleFormat1(12.45645));
        System.out.println(dfu.doubleFormat2(12.45645));
        System.out.println(dfu.doubleFormat3(12.45645));
        System.out.println(dfu.doubleFormat4(12.45645));
        System.out.println(dfu.doubleFormat5(12.45645));
    }

}
