package com.example.android.dailynews;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NewsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, Drawable>> data2;
    private static LayoutInflater inflater = null;
    private HashMap<String, String> localhash;
    private HashMap<String, Drawable> localhash2;
    private HashMap<String, String> mData = new HashMap();
    private static String[] mKeys;

    public NewsAdapter(Context c, ArrayList<HashMap<String, String>> d, ArrayList<HashMap<String, Drawable>> d2) {
        context = c;
        data = d;
        data2 = d2;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mKeys = mData.keySet().toArray(new String[data.size()]);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * This override of the getView method verifies that there is an inflated list item view to work on,
     * extracts the object of the current NewsItem of the array to display,
     * and places its title, section and date in the appropriate TextViews.
     *
     * @param position    - the position in the newsItemsList array to be displayed
     * @param convertView - the view to be recycled (can be null)
     * @param parent      - the parent into which the view should be inserted to
     * @return - the updated list item view with the NewsItem info
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null)

            v = inflater.inflate(R.layout.news_list_item, null);

        TextView titleText = v.findViewById(R.id.text_news_title);
        TextView newsDate = v.findViewById(R.id.text_news_date);
        TextView sectionNews = v.findViewById(R.id.text_news_section);
        TextView authorNews = v.findViewById(R.id.text_news_author);
        ImageView image = v.findViewById(R.id.thumbnail);

        try {
            localhash = data.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        Resources res = context.getResources();

        // Update the title
        titleText.setText(localhash.get(res.getString(R.string.title)));

        // Update the webPublicationDate
        String date = localhash.get(res.getString(R.string.webPublicationDate));
        String d = date.replace(res.getString(R.string.T), " ");
        String resultDate = d.replace(res.getString(R.string.Z), "");
        String formattedDate = parseDateToddMMyyyy(resultDate);
        newsDate.setText(formattedDate);
        if (formattedDate != null && !formattedDate.isEmpty()) {

            newsDate.setVisibility(View.VISIBLE);
        }
        // Update the section
        String section = localhash.get(res.getString(R.string.sectionName));
        sectionNews.setText(section);

        try {
            localhash2 = data2.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        image.setImageDrawable(localhash2.get(res.getString(R.string.thumbnail)));

        String author = localhash.get(res.getString(R.string.author));
        authorNews.setText(String.format("%s %s", res.getString(R.string.writtenBy), author));

        if (author != null && !author.isEmpty()) {

            authorNews.setVisibility(View.VISIBLE);
        }
        return v;
    }
        // Parsing data from String to Date
        private String parseDateToddMMyyyy(String date) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date mDate;
        String mStr = null;

        try {
            mDate = inputFormat.parse(date);
            mStr = outputFormat.format(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mStr;
    }
}

