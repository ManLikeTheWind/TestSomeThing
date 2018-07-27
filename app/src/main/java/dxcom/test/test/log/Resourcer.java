package dxcom.test.test.log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import dalvik.system.DexFile;
import dxcom.test.test.OSSApplication;

/** 资源 */
class Resourcer{
    /**资源序号*/
    private int number=0;
    /**资源标记*/
    private boolean flag=false;

    private String logContent;

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public int getNumber() {
        return number;
    }
    private static  String LOG_PATH=null;

    public Resourcer() {
        if (LOG_PATH==null){
            File getCacheDir= OSSApplication.get().getCacheDir();
            File getExternalCacheDir= OSSApplication.get().getExternalCacheDir();
            File fileT=getExternalCacheDir.exists()?getExternalCacheDir:getCacheDir;
            LOG_PATH= fileT.getAbsolutePath();

            LOG_PATH+=LOG_PATH.endsWith(File.separator)?"":File.separator;
            LOG_PATH+="DXLOG_Trace";
            File file = new File(LOG_PATH);
            boolean flag=file.exists()?false:file.mkdirs();
            LOG_PATH+=File.separator+"dxlog_txt.txt";
        }
    }

    /**生产资源*/
    public synchronized void create(String logContent){
        while (flag) {//先判断标记是否已经产生了，如果生产了，等待消费；
            try {
//				System.out.println("生产者  处于等待的线程 "+Thread.currentThread().getName());
                wait();//让生产线等待；
//				System.out.println("生产者  *****被唤醒*****的线程 "+Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        setLogContent(logContent);
        number++;//生产一个
        System.out.println("Resource "+Thread.currentThread().getName()+" 生产者-生产了一个 number = "+number+";logContent = "+logContent);
        flag=true;//将资源标记为已经生产；
        notifyAll();//唤醒在等待操作资源的线程（队列）
    }

    /**消费资源---这是放到子线程里面的*/
    public synchronized void destroy(){
        while (!flag) {
            try {
                System.out.println("消费者  处于等待的线程 "+Thread.currentThread().getName());
                wait();
                System.out.println("消费者  *****被唤醒*****的线程 "+Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        doSomething();

        System.out.println(Thread.currentThread().getName() + " 消费者-消费了一个number = " + number);
        flag=false;
        notifyAll();
    }


    private void doSomething(){
        try {
            Date currentTime = new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = sdf.format(currentTime);
            method3(LOG_PATH,dateString+"  "+getLogContent()+"\n");
            System.out.println("Consumer "+Thread.currentThread().getName()+" 生产者-生产了一个 number = "+getNumber()+";logContent = "+getLogContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param fileName
     *            文件名
     * @param content
     *            追加的内容
     */
    public static void method3(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write(content.getBytes("utf-8"));
//            randomFile.writeBytes(content);//乱码
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}