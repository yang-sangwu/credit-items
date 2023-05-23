import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Java8 内置的四大核心函数式接口
 * <p>
 * Consumer<T> : 消费型接口
 * void accept(T t);
 * <p>
 * Supplier<T> : 供给型接口
 * T get();
 * <p>
 * Function<T,R> : 函数型接口
 * R apply(T t);
 * <p>
 * Predicate<T> : 断言型接口
 * boolean test(T t);
 */
public class TestLambda {

    //Consumer<T> 消费型接口 :
    @Test
    public void test1() {
        //使用Lambda表达式,调用Consumer接口,实现抽象方法accept().
        happy(10000, (money) -> System.out.println("用户每次消费" + money + "元"));
        //用户每次消费10000.0元
    }

    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }

    //Supplier<T> 供给型接口 :
    @Test
    public void test2() {
        //随机生成100以内的10个整数
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));      //使用Lambda表达式实现接口方法
        System.out.println(numList.toString());
        //[40, 46, 15, 3, 67, 53, 62, 9, 98, 93]
    }

    //需求:产生指定个数的整数,并嵌入集合中.
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    //Function<T,R> 函数型接口 :
    @Test
    public void test3() {
        //去除字符串str首尾空格
        String newStr = strHandler("\t\t\t Java8内置的四大核心函数式接口 \t\t", (str) -> str.trim());
        System.out.println(newStr);       //Java8内置的四大核心函数式接口
        String subStr = strHandler(newStr, s -> s.substring(8, 12));
        System.out.println(subStr);       //四大核心
    }

    //需求:用于处理字符串
    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    //Predicate<T> 断言型接口 :

    @Test
    public void test4() {
        List<String> list = Arrays.asList("Hello", "Java", "Lambda", "Day");
        List<String> res = filterStr(list, s -> s.length() > 3);        //筛选所有长度大于3的字符串
        System.out.println(res.toString());         //[Hello, Java, Lambda]
    }

    //需求: 将满足要求的字符串放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> strList = new ArrayList<>();
        for (String str : list) {
            if (pre.test(str)) {
                strList.add(str);
            }
        }

        return strList;
    }

}
