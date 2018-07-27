package dxcom.test.test.systemManager;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.Closeable;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import dxcom.test.test.R;

public class DownloadManagerActivity extends AppCompatActivity {
    public static final String TAG=DownloadManagerActivity.class.getSimpleName();

    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        downloadManager=(DownloadManager) DownloadManagerActivity.this.getApplication().getSystemService(Context.DOWNLOAD_SERVICE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                final long l = doDownload_enqueue();
                                v.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        int i = doDownload_delete(l);
                                        Log.e(TAG, "run: postDelayed doDownload_delete i = "+i );
                                    }
                                },3000);

                                doDownload_getRecommendedMaxBytesOverMobile();

                                doDownload_query();

                            }
                        }).show();
            }
        });
    }

    /**
     * downloadManager.enqueue(dmRequest);
     */
    private long doDownload_enqueue(){
        long downloadEqueueId=-1;
        DownloadManager.Request dmRequest=null;
        Uri.Builder uriBuild=new Uri.Builder();
        File appDownloadFileDir=getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File pubDownloadFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] files1 = pubDownloadFileDir.listFiles();
        File[] files = pubDownloadFileDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Log.e(TAG, "accept:  dir = " + dir.getAbsolutePath() + "; name = " + name);
                boolean isYao = false;
                if (name.equals("FOA-v7-2018-4-27-01.apk")) {
                    isYao = true;
                }
                return isYao;
            }
        });
        if (files.length>0){
            Uri uri2=Uri.fromFile(files[0]);
        }
        Uri uri=Uri.parse("http://fanyi.baidu.com/gettts?lan=en&text=You%20have%20a%20new%20message&spd=3&source=web");
        Uri.Builder builder = uri.buildUpon();



//        Uri uri=Uri.parse("https://www.baidu.com");
        dmRequest=new DownloadManager.Request(uri);
//        dmRequest.addRequestHeader();
        dmRequest.allowScanningByMediaScanner();
        dmRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            dmRequest.setAllowedOverMetered(true);
        }
        dmRequest.setAllowedOverRoaming(true);
        dmRequest.setDescription("描述描述描述描述描述描述描述描述描述描述");
        dmRequest.setDestinationInExternalFilesDir(
                DownloadManagerActivity.this.getApplicationContext(),
                Environment.DIRECTORY_DOWNLOADS,
                "baidu.apk");


        dmRequest.setMimeType("apk");
        dmRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        dmRequest.setTitle("BaiDu.apk");
        dmRequest.setVisibleInDownloadsUi(true);

        try{
             downloadEqueueId=downloadManager.enqueue(dmRequest);
            Log.e(TAG, "doDownload_enqueue: downloadEqueueId = "+downloadEqueueId );
        }catch(Exception e){
            Log.e(TAG, "doDownload_enqueue:Exception   ",e );
        }
        return downloadEqueueId;

    }

    /**
     *  downloadManager.remove();
     *
     *  Cancel downloads and remove them from the download manager.
     *  Each download will be stopped if it was running, and it will no longer be accessible through the download manager.
     *  If there is a downloaded file, partial or complete, it is deleted.
     */
    private int doDownload_delete(long...downloadID){
        int remove = downloadManager.remove(downloadID);
        return remove;
    }

    /**
     * 获得推荐的 缓存字节大小；在进行流写入文件的时候的每次读写大小：byteTemp 即len
     */
    private void doDownload_getRecommendedMaxBytesOverMobile(){
        Long recommendedMaxBytesOverMobile = downloadManager.getRecommendedMaxBytesOverMobile(this);
        getFileDataSize(recommendedMaxBytesOverMobile);

    }
    public static String getFileDataSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String str;
        String temp=null;

        int kb_1=1024;
        int mb_1=1024*1024;
        int gb_1=1024*1024*1024;

        if (size < kb_1) {
            temp=df.format((double) size);//不同的手机返回值不一样：有的是".",有的是“,”--魅族;所以不能使用：Double.valueOf(temp);
            str = temp + "B";
        } else if (size < mb_1) {
            temp=df.format((double) size / kb_1);
            str = temp + "KB";
        } else if(size < gb_1){
            temp=df.format((double) size / (mb_1));
            str = temp + "MB";
        }else {
            temp= (df.format((double) size / gb_1));
            str = temp + "GB";
         }
        System.out.println("Utils.jva getFileDataSize str = "+str+";size = "+size);

        return str;
    }

    private void doDownload_query(){
        DownloadManager.Query queryD=new DownloadManager.Query();//DownloadManager.STATUS_SUCCESSFUL
//        queryD.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
        Cursor queryCursor = downloadManager.query(queryD);
        Log.e(TAG, "doDownload_query: queryCursor.count = " +queryCursor.getCount() );

        String[] columnNames = queryCursor.getColumnNames();
        int[] columnNamesIndex=new int[columnNames.length];
        for (int i=0;i<columnNames.length;i++){
            columnNamesIndex[i]=queryCursor.getColumnIndex(columnNames[i]);
        }
        HashMap<String,String> temp;
        ArrayList<HashMap<String ,String>> result=new ArrayList<>();
        while (queryCursor.moveToNext()){
            temp=new HashMap<>();
            for (int i=0;i<columnNamesIndex.length;i++){
                temp.put(columnNames[i],queryCursor.getString(columnNamesIndex[i]));
            }
            result.add(temp);
        }
        Log.e(TAG, "doDownload_query: result "+result );

    }



    private void doHttpUrlConnection(){
        HttpURLConnection connection=null;
        URL url=null;
        InputStream is=null;
        RandomAccessFile raf=null;
        String httpUrl="https://www.baidu.com";
        try {
            url=new URL(httpUrl);
            connection= (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15*100);
            connection.connect();
            if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                is=connection.getInputStream();
                is.skip(1024);//跳过多少字节，已经下载过了；
                raf=new RandomAccessFile("fileAbsolutePath","rw");
                raf.skipBytes(1024);

                byte[]tempB=new byte[10*1024];
                int len;
                while ((len=is.read(tempB))>0){
                    raf.write(tempB,0,len);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(is);
            close(raf);
            close(is);
            if (connection!=null){
                connection.disconnect();
            }
        }
    }
    private void close(Closeable closeAble){
        if (closeAble!=null){
            try{
                closeAble.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
