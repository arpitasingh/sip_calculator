package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentSecondBinding;

import java.util.List;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private DBHelper myDBHelper;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDBHelper = new DBHelper(getContext());
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        List<FinanceGoal> goals = myDBHelper.getAllGoals();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.goalList.setLayoutManager(mLayoutManager);
        if(goals != null && goals.size() > 0) {
            GoalsListAdapter goalsListAdapter = new GoalsListAdapter(getActivity(), goals);
            binding.goalList.setAdapter(goalsListAdapter);
        } else {
            Toast.makeText(getContext(), "You have not added any goals",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}