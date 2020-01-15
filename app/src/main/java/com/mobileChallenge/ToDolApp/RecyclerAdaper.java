package com.mobileChallenge.ToDolApp;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdaper extends RecyclerView.Adapter<RecyclerAdaper.RecyclerViewHolder> {
    ArrayList<CustomCard> customCardList;
    TextView tview;
    private Context context;
    OnItemClickListener itemClickListener;



    public  class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        TextView taskName;
        TextView taskDescription;
        ImageView taskImage;


        public RecyclerViewHolder(View view){
            super(view);
            taskName =view.findViewById(R.id.name);
            taskDescription=view.findViewById(R.id.subTask);
            taskImage=view.findViewById(R.id.taskImage);


            view.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener!= null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem doWhatever= contextMenu.add(Menu.NONE,1,1,"Do whatever");
            MenuItem delete= contextMenu.add(Menu.NONE,2,2,"Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (itemClickListener!= null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (menuItem.getItemId()){
                        case 1: itemClickListener.onWhateverClick(position);
                                return true;
                        case 2: itemClickListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }


        public RecyclerAdaper(Context context, ArrayList<CustomCard> customCardArrayList){
            this.context=context;
            customCardList=customCardArrayList;
        }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener=listener;
    }


//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_layout,parent,false);
//        RecyclerViewHolder recyclerViewHolder =new RecyclerViewHolder(v);
//        return recyclerViewHolder;
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        CustomCard currentCard = customCardList.get(position);
//
//    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_layout,parent,false);
        View v = LayoutInflater.from(context).inflate(R.layout.custom_card_layout,parent,false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
        CustomCard currItem= customCardList.get(position);
        holder.taskName.setText(currItem.getTaskName());   //
        holder.taskDescription.setText(currItem.getTaskDescription());

        StorageReference mImageRef =
                FirebaseStorage.getInstance().getReference("Uploads").child(currItem.getPhotouri());
        mImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                System.out.println("dbasdnaskjdbkasjdkasdkas: "+uri.toString());
                Picasso.with(context).load(uri.toString()).into(holder.taskImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        String u=currItem.getPhotouri();

        System.out.println(u);
        if (currItem.getPhotouri()!=null){
            Picasso.with(context)
                    .load(u)
                    .placeholder(R.drawable.me_time)
                    .into(holder.taskImage);
        }

    }

    @Override
    public int getItemCount() {

        return customCardList.size();
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
        void  onWhateverClick (int position);
        void onDeleteClick(int position);

    }


}
