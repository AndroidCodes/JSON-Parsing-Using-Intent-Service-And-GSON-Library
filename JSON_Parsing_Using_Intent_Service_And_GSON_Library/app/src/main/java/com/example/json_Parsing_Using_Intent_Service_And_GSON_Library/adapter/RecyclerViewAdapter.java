package com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.R;
import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.gson_modal.Parameters;
import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.utility.CommonDataUtility;
import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.utility.Font;

import java.util.ArrayList;

/**
 * Created by peacock on 3/9/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Activity activity;

    private ArrayList<Parameters.ResultBean> listData;

    public RecyclerViewAdapter(Activity activity, ArrayList<Parameters.ResultBean> listData) {

        this.activity = activity;

        this.listData = listData;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View customView = LayoutInflater.from(activity).inflate(R.layout.list_item_layout, parent,
                false);

        ViewHolder holder = new ViewHolder(customView);

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvStoneID.setText(Html.fromHtml(listData.get(position).getPId()));

        String S_CODE = listData.get(position).getS_Code();
        if (S_CODE != null) {

            holder.tvSortName.setText(listData.get(position).getS_Code());
        }

        String CARAT = listData.get(position).getCarat();
        if (CARAT != null) {
            holder.tvCarat.setText("CT. " +
                    CommonDataUtility.toTwoPrecision(listData.get(position).getCarat()));
        }

        String Q_NAME = listData.get(position).getQ_Name();
        String C_NAME = listData.get(position).getC_Name();
        if (Q_NAME == null) {
            Q_NAME = "";
        }
        if (C_NAME == null) {
            C_NAME = "";
        }

        holder.tvQName.setText("" + Q_NAME);
        holder.tvCName.setText("" + C_NAME);

        String ct_sort = listData.get(position).getCTS_Name();

        if (ct_sort == null) {
            ct_sort = "-";

        }

        String pl_sort = listData.get(position).getPLS_Name();
        if (pl_sort == null) {
            pl_sort = "-";

        }

        String sy_sort = listData.get(position).getSYMS_Name();
        if (sy_sort == null) {
            sy_sort = "-";

        }

        holder.tvCtPlSy.setText("" + ct_sort + "-" + pl_sort + "-" + sy_sort);

        holder.tvFlo.setText(listData.get(position).getFS_Name());

        String Disc = listData.get(position).getDisc();

        if (Disc != null) {

            holder.tvPPER.setText(Disc);

        }

        String RAP = listData.get(position).getRAP();
        if (RAP != null) {
            holder.tvGrap.setText(CommonDataUtility.toTwoPrecision(RAP));
        }

        String RapAmt = listData.get(position).getRapAmt();
        if (RapAmt != null) {

            holder.tvAmount.setText(CommonDataUtility.toTwoPrecision(RapAmt));

        }

        holder.tvImagepath.setText("G");
        Font.setAppIconFonts(holder.tvImagepath, activity);

        holder.tvMoviepath.setText("F");
        Font.setAppIconFonts(holder.tvMoviepath, activity);
    }

    @Override
    public int getItemCount() {

        return listData.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvChekbox, tvStoneID, tvSortName, tvCarat, tvQName, tvCName, tvCtPlSy, tvFlo,
                tvPPER, tvGrap, tvAmount, tvImagepath, tvMoviepath, tvGIApath;

        public ViewHolder(View convertView) {
            super(convertView);

            tvChekbox = (TextView) convertView.findViewById(R.id.tvChekbox);
            Font.setIconFonts(activity, tvChekbox);

            tvStoneID = (TextView) convertView.findViewById(R.id.tvStoneID);

            tvSortName = (TextView) convertView.findViewById(R.id.tvSortName);

            tvCarat = (TextView) convertView.findViewById(R.id.tvCarat);

            tvQName = (TextView) convertView.findViewById(R.id.tvQName);

            tvCName = (TextView) convertView.findViewById(R.id.tvCName);

            tvCtPlSy = (TextView) convertView.findViewById(R.id.tvCtPlSy);

            tvFlo = (TextView) convertView.findViewById(R.id.tvFlo);

            tvPPER = (TextView) convertView.findViewById(R.id.tvPPER);

            tvGrap = (TextView) convertView.findViewById(R.id.tvGrap);

            tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);

            tvImagepath = (TextView) convertView.findViewById(R.id.tvImagepath);

            tvMoviepath = (TextView) convertView.findViewById(R.id.tvMoviepath);

            tvGIApath = (TextView) convertView.findViewById(R.id.tvGIApath);

        }
    }
}
