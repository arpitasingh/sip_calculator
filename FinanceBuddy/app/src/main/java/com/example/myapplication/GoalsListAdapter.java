package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.ItemAccountListBinding;

import java.io.File;
import java.util.List;

public class GoalsListAdapter extends RecyclerView.Adapter<GoalsListAdapter.MyViewHolder> {

    private Context mContext;
    private List<FinanceGoal> goalsList;
    protected ItemAccountListBinding binding;

    public GoalsListAdapter(Context context, List<FinanceGoal> goals) {
        mContext = context;
        goalsList = goals;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = ItemAccountListBinding.inflate(layoutInflater);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(goalsList.get(position));
    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ItemAccountListBinding binding;

        public MyViewHolder(ItemAccountListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onClick(View view) {

        }

        public void bind(FinanceGoal goalsList) {
            if (!TextUtils.isEmpty(goalsList.filePath)) {
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), goalsList.getFilePath());
                Uri uri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName().toString() + ".provider", filelocation);
//                binding.goalImage.setImageURI(uri);
                Glide.with(mContext)
                        .load(uri)
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .into(binding.goalImage);
            }
            binding.goalName.setText(goalsList.name);
            binding.textMonthly.setText(goalsList.monthlyVal);
            binding.textQuarterly.setText(goalsList.quarterlyVal);
            binding.textAnnually.setText(goalsList.annuallyVal);
            switch (goalsList.option) {
                case "1":
                    binding.textOptions.setText("EQUITY");
                case "2":
                    binding.textOptions.setText("Hybrid Fund, EQUITY");
                case "3":
                    binding.textOptions.setText("Debt Fund, Hybrid Fund, EQUITY");
                case "4":
                    binding.textOptions.setText("Liquid Fund, Debt Fund, Hybrid Fund, EQUITY");
                case "5":
                    binding.textOptions.setText("FD, Liquid Fund, Debt Fund, Hybrid Fund, EQUITY");
            }
        }
    }
}
