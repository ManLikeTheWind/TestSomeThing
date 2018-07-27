package dxcom.test.test;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static  final String AGE="AGE";
    public static  final String NAME="NAME";


    public void addition_isCorrect() throws Exception {
        HashMap<String,String >map=new HashMap<>();
        map.put("a","a");
        map.put("b","b");
        map.put("c","c");
        map.put("d","d");
        System.out.println("mapmapmap   "+map);

        Map<String,String > map0=new HashMap<>();
        map0.put("a","a");
        map0.put("b","b");
        map0.put("c","c");
        map0.put("d","d");
        System.out.println("map0map0map0   "+map0);


        TreeSet<HashMap<String ,String>> treeSet=new TreeSet<>(new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> left, HashMap<String, String> right) {
                int result=Integer.valueOf(left.get(AGE))-Integer.valueOf(right.get(AGE));
                System.out.println(result);

//                result=  result==0? 1: result;
                return result;//正则-不换，-相等,不去重，负则-想换；//left-right--升序； -（left-right）--降序
            }
        });
        HashMap<String ,String> one=new HashMap<>();
        one.put(AGE,"1");
        one.put(NAME,"1_Name");

        HashMap<String ,String> two=new HashMap<>();
        two.put(AGE,"2");
        two.put(NAME,"2_Name");

        HashMap<String ,String> thre=new HashMap<>();
        thre.put(AGE,"3");
        thre.put(NAME,"3_Name");

        HashMap<String ,String> four=new HashMap<>();
        four.put(AGE,"4");
        four.put(NAME,"4_Name");

        HashMap<String ,String> five=new HashMap<>();
        five.put(AGE,"5");
        five.put(NAME,"5_Name");

        treeSet.add(two);
        treeSet.add(one);
        treeSet.add(thre);
        treeSet.add(five);
        treeSet.add(five);
        treeSet.add(four);
        treeSet.add(four);
        System.out.println("treeSet 1 = "+treeSet);

        ArrayList<HashMap<String,String>> resultArrayList=new ArrayList<>(treeSet);

        Iterator<HashMap<String, String>> iterator = treeSet.iterator();

        while (iterator.hasNext()){
            HashMap<String, String> next = iterator.next();
            System.out.println("next = "+next);
        }
        System.out.println("=======================================================");
        System.out.println("resultArrayList = "+resultArrayList);
        System.out.println("==========================22222222222222=============================");

        resultArrayList.get(0).put(AGE,"88888");
        System.out.println("treeSet 2 = "+treeSet);


    }

    static int  COUNT=100;
    @Test
    public void t2(){
//        List<String> l1=new ArrayList<>();
//        Collections.synchronizedList(l1);
//        Map<String,String> m1=new HashMap<>();
//        m1.put("key1","dongxiang");
//        m1.put("key2","wangzhaojun");
//        m1.put("key3","hezhendong");
//        ArrayList<Map.Entry<String, String>> entries = new ArrayList<>(m1.entrySet());
//        Collections.sort(entries,(o1,o2)->o1.getValue().compareTo(o2.getValue()));
        final Counter counter = new Counter();
        for(int i=0;i<COUNT;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.inc();
                }
            }).start();
        }

        while (true){
            try {
                Thread.sleep(100);

                if( COUNT<5 ){
                    System.out.println("counter = "+counter+";COUNT = "+COUNT);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

static class Counter {
    private volatile int count = 0;
    public void inc(){
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        COUNT--;
        System.out.println(Thread.currentThread()+" counter = "+this+";COUNT = "+COUNT);

    }
    @Override
    public String toString() {
        return "[count=" + count + "]";
    }
}



}