package com.example.administrator.noburialsite;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/25.
 */

public class SecondFragment extends Fragment {
    View view;
    ArrayList<View> allView=new ArrayList<>();
    Rect rect=new Rect();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getActivity()).inflate(R.layout.fragment_b , null);
        allView=((MainActivity)getActivity()).getView((ViewGroup) view);

        ((MainActivity)getActivity()).setDispatchTouchEventable(new DispatchTouchEventable() {
            @Override
            public void point(int x, int y) {
               /* for(View  currentview:allView){
                    if(currentview == null) continue;
                    currentview.getGlobalVisibleRect(rect);
                    if(rect.contains(x ,y )){
                        Log.v("out" , "埋点id:"+currentview.getId());
                        String name=getContext().getResources().getResourceEntryName(currentview.getId());
                        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });
        return view;
    }
}
