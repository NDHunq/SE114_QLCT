package com.example.qlct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Home_The_Share_Wallet_Apapter extends BaseAdapter {
private Context context;
private int layout;
private ArrayList<Home_The_Share_Wallet> theShareWalletList;

    public Home_The_Share_Wallet_Apapter(Context context, int layout, ArrayList<Home_The_Share_Wallet> theShareWalletList) {
        this.context = context;
        this.layout = layout;
        this.theShareWalletList = theShareWalletList;
    }

    @Override
    public int getCount() {
        return theShareWalletList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(layout,null);
        EditText email =(EditText) view.findViewById(R.id.email);
        EditText note =(EditText) view.findViewById(R.id.note);
        Home_The_Share_Wallet theShareWallet = theShareWalletList.get(i);
        email.setText(theShareWallet.getEmail());
        note.setText(theShareWallet.getMessage());
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        ImageView edit = (ImageView) view.findViewById(R.id.edit);
        edit.setVisibility(View.GONE);
        ImageView save = (ImageView) view.findViewById(R.id.save_edit);
        save.setVisibility(View.GONE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setEnabled(true);
                note.setEnabled(true);
                cancel.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setEnabled(false);
                note.setEnabled(false);
                edit.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the email and note EditTexts

                theShareWalletList.remove(i);
                notifyDataSetChanged();

            }
        });
        ImageView add = (ImageView) view.findViewById(R.id.addnew);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String noteText = note.getText().toString();

                // Update the item at the top of the list with the input from the EditTexts
                Home_The_Share_Wallet topItem = theShareWalletList.get(theShareWalletList.size()-1);
                topItem.setEmail(emailText);
                topItem.setMessage(noteText);

                // Refresh the ListView
                notifyDataSetChanged();

                theShareWalletList.add(new Home_The_Share_Wallet("",""));



            }
        });
        // Set properties based on conditions
        if (i != theShareWalletList.size() - 1)
        {
            add.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            email.setEnabled(false);
            note.setEnabled(false);


        } else {
            add.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);

            email.setEnabled(true);
            note.setEnabled(true);

        }

        return view;


    }
}
