package com.liumeng.mybmob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mMain_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(this, "e079281269d58e414f2fb8f01e195f12");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);

        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.addDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person p2 = new Person();
                p2.setName("lucky");
                p2.setAddress("北京海淀");
                p2.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId,BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        findViewById(R.id.queryDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobQuery query =new BmobQuery("Person");
//                query.addWhereEqualTo("age", 25);
                query.setLimit(2);
                query.order("createdAt");
                //v3.5.0版本提供`findObjectsByTable`方法查询自定义表名的数据
                query.findObjectsByTable(new QueryListener<JSONArray>() {
                    @Override
                    public void done(JSONArray ary, BmobException e) {
                        if(e==null){
                            Log.i("bmob","查询成功："+ary.toString());
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
            }
        });
        findViewById(R.id.updateDate).setOnClickListener(this);
        findViewById(R.id.deleteDate).setOnClickListener(this);
        mMain_iv = (ImageView) findViewById(R.id.main_iv);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateDate:
                update();
                break;
            case R.id.deleteDate:
                detele();
                break;
        }
    }

    private void update() {
//        Person person3 = new Person();
//        person3.setName("liumeng");
//        person3.setAddress("上海");
//        person3.update("be1d2d16c1", new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    Log.e("bmob","更新成功");
//                } else {
//                    Log.e("bmob","更新失败");
//                }
//            }
//        });
        BmobQuery query =new BmobQuery("Image");
        query.addWhereEqualTo("objectId", "F75l2228");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {

            }
        });
    }

    private void detele() {
        Person person4 = new Person();
        person4.setObjectId("be1d2d16c1");
        person4.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","成功");
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
