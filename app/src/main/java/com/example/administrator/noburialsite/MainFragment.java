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
 * Created by Administrator on 2017/4/24.
 */

public class MainFragment extends Fragment {
    View view;
    ArrayList<Integer> allViewId=new ArrayList<>();
    Rect rect=new Rect();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getActivity()).inflate(R.layout.fragment_a , null);
        allViewId=((MainActivity)getActivity()).idInt;


        ((MainActivity)getActivity()).setDispatchTouchEventable(new DispatchTouchEventable() {
            @Override
            public void point(int x, int y) {
                for(int  currentview : allViewId){
                    View view=getActivity().findViewById(currentview);
                    if(view == null) continue;
                    view.getGlobalVisibleRect(rect);
                    if(rect.contains(x ,y )){
                        Log.v("out" , "埋点id:"+currentview);
                        String name=getContext().getResources().getResourceEntryName(currentview);
                        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}
