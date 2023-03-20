package com.example.yp_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class AdapterQuotes extends BaseAdapter {

    private Context nContext;
    List<QueteMaska> maskList;

    public AdapterQuotes(Context nContext, List<QueteMaska> maskList) {
        this.nContext = nContext;
        this.maskList = maskList;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(nContext, R.layout.maska_block_mood,null);

        TextView title = v.findViewById(R.id.name_block);
        ImageView Image = v.findViewById(R.id.Imgpfoto);
        TextView description = v.findViewById(R.id.kratko_block);

        QueteMaska maskQuote = maskList.get(position);
        title.setText(maskQuote.getTitle());


        Image.setImageURI(Uri.parse(maskQuote.getImage()));
        if(maskQuote.getImage().toString().equals("null"))
        {
            Image.setImageResource(R.drawable.picture);
        }
        else
        {
            new DownloadImageTask((ImageView) Image).execute(maskQuote.getImage());

        }
        description.setText(maskQuote.getDescription());
        return v;
    }
}
