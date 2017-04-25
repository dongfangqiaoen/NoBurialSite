package com.example.administrator.noburialsite;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String LOG_TAG="MainActivity";
    ArrayList<View> allView,allView2;
    View rootView;
    ArrayList<String> viewPath=new ArrayList<>();
    ArrayList<Integer> viewId=new ArrayList<>();
    String mClassName=getClass().getName();
    Context mContext;
    Rect rect=new Rect();
    DispatchTouchEventable dispatchTouchEventable;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        viewId.add(R.id.login);
        viewId.add(0x7f0b0059);
        String packageName=getPackageName();
        int imageResId = getResources().getIdentifier("activity_main_user", "id", packageName);
        viewId.add(imageResId);
        viewId.add(R.id.fragment_a_tv_login);
        viewId.add(R.id.fragment_a_tv_register);
        show= (Button) findViewById(R.id.bt);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        showFragment();

    }

    private void showFragment() {
        MainFragment fragment=new MainFragment();
        android.support.v4.app.Fragment temp=getSupportFragmentManager().findFragmentByTag("Main");
        if(temp!=null){
            getSupportFragmentManager().beginTransaction().remove(temp).commit();
        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment,"Main").commit();
        }

    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        rootView = ((ViewGroup) (getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
        Log.d(LOG_TAG,rootView.toString());
        if(allView != null)
            allView.clear();
        allView = getView((ViewGroup) rootView);
        for(int i=0;i<allView.size();i++) {
            Log.v("out", allView.get(i).toString());
            Log.v("out","left ："+allView.get(i).getX()+"+right:"+allView.get(i).getY());
            viewPath.add(mClassName+"."+allView.get(i).toString().split("\\{")[0]);
        }
        Log.v("out" , allView.size()+"");
        Rect outRect = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        allView2 = allView;
    }


    /*获取所有View和没有子View的ViewGroup*/
    public ArrayList<View> getView(ViewGroup viewGroup){
        ArrayList<View> views = null;
        if(views == null)
            views = new ArrayList<View>();
        if(viewGroup == null) return null;
        //views.add(viewGroup);
        int count = viewGroup.getChildCount();
        Log.d(LOG_TAG,count+"个子view");
        for(int i=0;i<count;i++){
            if(!(viewGroup.getChildAt(i) instanceof ViewGroup)){
                views.add(viewGroup.getChildAt(i));
                Log.v(LOG_TAG,viewGroup.getChildAt(i).getY()+"");
            }else this.getView((ViewGroup) viewGroup.getChildAt(i));
        }
        return views;
    }

    /*重写dispatchTouchEvent*/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v("out" , "dispatchTouchEvent()");
        if(ev.getAction() == MotionEvent.ACTION_UP){
            /*获取当前点击位置，遍历布局，获取当前点击位置对应的view，根据view映射路径，与json文件中的对比*/
            int  x = (int) ev.getRawX();
            int  y = (int) ev.getRawY();
            Log.v("out" , "touch point:"+x+","+y);
            if(allView2 != null)
                allView2.clear();
            allView2 = getView((ViewGroup) rootView);
            for (int  id:viewId) {
                View view = findViewById(id);
                if(view == null) continue;
                view.getGlobalVisibleRect(rect);
                if(rect.contains(x ,y )){
                    Log.v("out" , "埋点id:"+id);
                    String name=mContext.getResources().getResourceEntryName(id);
                    Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                    return super.dispatchTouchEvent(ev);
                }
            }
            /*如果运行到这了，说明上面的遍历没有找到，那么当前的view是在fragment中*/
            dispatchTouchEventable.point(x , y);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setDispatchTouchEventable(DispatchTouchEventable dispatchTouchEventable){
        this.dispatchTouchEventable = dispatchTouchEventable;
    }
}
