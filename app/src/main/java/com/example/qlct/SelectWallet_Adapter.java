package com.example.qlct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectWallet_Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Wallet> walletList;

    public SelectWallet_Adapter(Context context, int layout, ArrayList<Wallet> walletList) {
        this.context = context;
        this.layout = layout;
        this.walletList = walletList;
    }

    @Override
    public int getCount() {
        return walletList.size();
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
        view = inflater.inflate(layout, null);

        TextView walletname = view.findViewById(R.id.select_wallet_txtview);
        TextView money = view.findViewById(R.id.select_wallet_money);
        ImageView image = view.findViewById(R.id.select_wallet_icon);

        Wallet wallet = walletList.get(i);
        walletname.setText(wallet.getWalletName());
        String currencySymbol = "";
        switch (wallet.getCurrency()){
            case "USD":
                currencySymbol = "$";
                break;
            case "VND":
                currencySymbol = "₫";
                break;
            case "EUR":
                currencySymbol = "€";
                break;
            case "CNY":
                currencySymbol = "¥";
                break;
        }
        String amount = wallet.getAmountMoney() + " " + currencySymbol;
        money.setText(amount);
        image.setImageResource(wallet.getImage());

        return view;
    }
}
