package com.example.nikhilr129.forgetitnot.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nikhilr129.forgetitnot.R;
import com.google.android.gms.vision.text.Text;

/**
 * Created by sugreev on 23/4/17.
 */

public class AboutUs extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        ListView listView = (ListView)findViewById(R.id.profile_about_us);
       // listView.setDivider(null);
        CustomAdapter imageAdapter = new CustomAdapter();
        listView.setAdapter(imageAdapter);
    }

    class CustomAdapter extends BaseAdapter {
        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.about_us_custom,null);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.profile_image);
            TextView name = (TextView) convertView.findViewById(R.id.about_us_name);
            ImageView gmail_id = (ImageView)convertView.findViewById(R.id.profile_image1);
            ImageView facebook_id= (ImageView)convertView.findViewById(R.id.profile_image2);
            ImageView github_id = (ImageView)convertView.findViewById(R.id.profile_image3);
            imageView.setImageResource(mThumbIds[position]);
            name.setText(name_id[position]);
            github_id.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(git_id[position]));
                    startActivity(intent);
                }
            });
            gmail_id.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, gm_id[position]);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "");

                    startActivity(Intent.createChooser(intent, "Send Email"));
                }
            });
            facebook_id.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(fb_id[position]));
                    startActivity(intent);
                }
            });
             return convertView;

        }

        // Keep all Images in array

    }
    Integer[] mThumbIds = {
            R.drawable.nikhil,R.drawable.akanshu,R.drawable.shiv,R.drawable.sugreev
    };
    String[] name_id = {"Nikhil Ranjan","Akanshu Gupta", "Shiv Singh", "Sugreev Prasad"};
    String[] fb_id = {"https://www.facebook.com/NikhilR129","https://www.facebook.com/akanshu.gupta.7","https://www.facebook.com/shiv.singh.3781","https://www.facebook.com/profile.php?id=100005135449195"};
    String[] git_id = {"http://github.com/nikhilr129","https://github.com/akanshugupta9", "http://github.com/myashok", "https://github.com/desta1"};
    String[] gm_id = {"iit2014129@iiita.ac.in","iwm2014501@iiita.ac.in","iit2014121@iiita.ac.in","iit2014050@iiita.ac.in"};

}
