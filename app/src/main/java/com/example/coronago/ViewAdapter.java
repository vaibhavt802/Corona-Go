package com.example.coronago;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import pl.droidsonroids.gif.GifImageView;

class ViewAdapter extends PagerAdapter {


    static Resources res = null;



    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.curveflat,
                                R.drawable.google,
                                R.drawable.mask,
                                R.drawable.rest,
                                R.drawable.safedistance,
                                R.drawable.touch,
                                R.drawable.wash};

    String wear_mask= "Wear a mask. The patient should wear a facemask around you and others.\n" +
            " If they can’t (for example, because it causes trouble breathing), \n" +
            "you should wear a mask when you are in the same room as the patient. \n" +
            "Also wear a disposable facemask and gloves when you have contact with the patient’s body fluids,\n" +
            " such as saliva, sputum, nasal mucus, vomit, urine, blood and stool. Throw out disposable facemasks and gloves after using them.";
    String take_rest = "Help the patient to rest at home. Help with basic needs in the \n" +
            "home, and provide support for getting groceries, prescriptions, and other personal needs.";
    String dont_touch = "Avoid touching your eyes, nose, and mouth with unwashed hands.";
    String wash_hands = "TWash your hands often. Use soap and water for at least 20 seconds.\n" +
            " Or use an alcohol-based hand sanitizer that contains at least 60% alcohol.\n" +
            " For visible soiling, soap and water are best.";
    String safe_distance = "Stay physically away from the patient as much as possible. \n" +
            "Household members should stay in another room or be separated from the patient as much as possible, \n" +
            "keep the door to the patient’s room closed, and use a separate bedroom and bathroom, if available.\n" +
            " Visitors should not be allowed and patients should stay away from pets.";
    String flat_curve = "The flattenning of curve will improve the current condition \n" +
            "and can help in focusing the health of poor people.";
    String google = "To prevent the spread of COVID-19:\n" +
            "• Clean your hands often. Use soap and water, or an alcohol-based hand rub.\n" +
            "• Maintain a safe distance from anyone who is coughing or sneezing.\n" +
            "• Don’t touch your eyes, nose or mouth.\n" +
            "• Cover your nose and mouth with your bent elbow or a tissue when you cough or sneeze.\n" +
            "• Stay home if you feel unwell.\n" +
            "• If you have a fever, cough and difficulty breathing, seek medical attention. Call in advance.\n" +
            "• Follow the directions of your local health authority.";


    private static final String[] title_new =new String[]{
            "Flat Curve","Google","Wear Mask","Take Rest","Safe Distance","Don't Touch","Wash Hands"
    };

    public String[] description = new String[]{
            flat_curve,google,wear_mask,take_rest,safe_distance,dont_touch,wash_hands
    };



    ViewAdapter(Context context){
        this.context = context;

    }





    @Override
    public int getCount() {
        return images.length;
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {



        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);




        View view = layoutInflater.inflate(R.layout.item,null);
       GifImageView imageView = view.findViewById(R.id.image_view);
        TextView title,desc;
        title = view.findViewById(R.id.title);
        title.setText(title_new[position]);
        desc = view.findViewById(R.id.desc);
        desc.setText(description[position]);
        imageView.setImageResource(images[position]);
        ViewPager viewPager = (ViewPager)container;
        viewPager.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }


}
