package com.timboat.coolapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends android.support.v4.app.Fragment {
    boolean isOpen = false;
    TextView textView;
    ListView listView;
    TextView wishListTitle;
    FloatingActionButton bubbleStart, bubble1, bubble2, bubble3;
    Animation bubbleOpen, bubbleClose, rotateClockwise, rotateCounterClockwise;

    public PageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_fragment_layout,container,false);
        Bundle bundle = getArguments();
        textView =(TextView) view.findViewById(R.id.text_view);
        listView = (ListView) view.findViewById(R.id.item_list);
        wishListTitle = (TextView) view.findViewById(R.id.wish_list_title);
        bubbleStart = (FloatingActionButton) view.findViewById(R.id.bubble_open_start);
        bubble1 = (FloatingActionButton) view.findViewById(R.id.bubble_element1);
        bubble2 = (FloatingActionButton) view.findViewById(R.id.bubble_element2);
        bubble3 = (FloatingActionButton) view.findViewById(R.id.bubble_element3);

        bubbleOpen = AnimationUtils.loadAnimation(getActivity(),R.anim.bubble_open);
        bubbleClose = AnimationUtils.loadAnimation(getActivity(),R.anim.bubble_close);
        rotateClockwise = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_clockwise);
        rotateCounterClockwise = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_counter_clockwise);

        if(bundle.getInt("count") == 1)
        {
            textView.setVisibility(View.VISIBLE);
            String message = Integer.toString(bundle.getInt("count"));
            textView.setText("This is the " + message +"Swipe View..");
        }
        else if(bundle.getInt("count") == 2)
        {
            bubbleStart.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            wishListTitle.setVisibility(View.VISIBLE);

            bubbleStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isOpen){
                        bubble1.startAnimation(bubbleClose);
                        bubble2.startAnimation(bubbleClose);
                        bubble3.startAnimation(bubbleClose);
                        bubbleStart.startAnimation(rotateCounterClockwise);
                        bubble1.setClickable(false);
                        bubble2.setClickable(false);
                        bubble3.setClickable(false);
                        isOpen = false;
                    }
                    else{
                        bubble1.startAnimation(bubbleOpen);
                        bubble2.startAnimation(bubbleOpen);
                        bubble3.startAnimation(bubbleOpen);
                        bubbleStart.startAnimation(rotateClockwise);
                        bubble1.setClickable(true);
                        bubble2.setClickable(true);
                        bubble3.setClickable(true);
                        isOpen=true;
                    }
                }
            });
        }
        return view;
   }
}
