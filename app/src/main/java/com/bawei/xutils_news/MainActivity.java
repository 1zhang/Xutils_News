package com.bawei.xutils_news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.xutils_news.uri.Uri;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
@ViewInject(R.id.lv_news) ListView lv;
    private List<Bean> li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
       // setContentView(R.layout.activity_main);
        //http://v.juhe.cn/toutiao/index?key=22a108244dbb8d1f49967cd74a0c144d
        li =  new ArrayList<>();
        RequestParams rq = new RequestParams("http://v.juhe.cn/toutiao/index?key=22a108244dbb8d1f49967cd74a0c144d");
       // rq.addQueryStringParameter("key",Uri.KEY);
        x.http().get(rq, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                try {
                    JSONObject js = new JSONObject(result);
                    JSONObject result1 = js.getJSONObject("result");
                    JSONArray data = result1.getJSONArray("data");
                    for (int i = 0; i <data.length() ; i++) {
                        JSONObject o = (JSONObject) data.get(i);
                        Bean b = new Bean();
                        String title = o.optString("title");
                        String img = o.optString("thumbnail_pic_s");
                        li.add(new Bean(title,img));
                    }






                    Myadapter my = new Myadapter();
                    lv.setAdapter(my);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return li.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             convertView= View.inflate(MainActivity.this,R.layout.item,null);
            TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            ImageView iv=(ImageView) convertView.findViewById(R.id.iv_news);
            tv_title.setText(li.get(position).getTitle());
            DisplayImageOptions options = new DisplayImageOptions.Builder().build();
            ImageLoaderConfiguration con = new ImageLoaderConfiguration.Builder(MainActivity.this)
                    .defaultDisplayImageOptions(options)
                    .build();
            ImageLoader.getInstance().init(con);
            ImageLoader.getInstance().displayImage(li.get(position).getImg(),iv );
            return convertView;
        }
    }
}
