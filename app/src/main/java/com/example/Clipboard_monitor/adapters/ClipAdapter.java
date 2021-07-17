package com.example.Clipboard_monitor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Clipboard_monitor.Clip;
import com.example.Clipboard_monitor.R;
import com.example.Clipboard_monitor.databinding.LayoutBinding;

import java.util.List;

public class ClipAdapter  extends RecyclerView.Adapter<ClipAdapter.ViewHolder>{

    private List<Clip> clips;
    private Context context;

    public ClipAdapter(List<Clip> clips, Context context) {
        this.clips = clips;
        this.context = context;
    }

    @NonNull
    @Override
    public ClipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.layout,
                        parent,
                        false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClipAdapter.ViewHolder holder, int position) {
        Clip clip = clips.get(position);
        holder.layout_binding.setClip(clip);
    }

    @Override
    public int getItemCount() {
        return clips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Binding Variables
        public LayoutBinding layout_binding;

        // Create a constructor to do view lookups for each subview
        public ViewHolder(LayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            layout_binding = layoutBinding;
        }
    }


}
