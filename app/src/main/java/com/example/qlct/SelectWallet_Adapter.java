package com.example.qlct;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class SelectWallet_Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Wallet_hdp> walletList;

    public SelectWallet_Adapter(Context context, int layout, ArrayList<Wallet_hdp> walletList) {
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

        Wallet_hdp wallet = walletList.get(i);
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
        String walletAmount = wallet.getAmountMoney();
        if(walletAmount.equals("")){
            money.setText(walletAmount);
        }
        else{
            String formattedAmount = formatCurrency(Double.parseDouble(walletAmount), wallet.getCurrency());
            String amount = formattedAmount + " " + currencySymbol;
            money.setText(amount);
        }

        // Tạo một đối tượng LayoutParams mới với chiều rộng và chiều cao mong muốn

        if(wallet.getWalletName().equals("Total")){
            walletname.setTextColor(context.getResources().getColor(R.color.xanhnen));
            image.setImageResource(R.drawable.global);
        }
        else{
            image.setImageResource(R.drawable.wallet);
        }


        return view;
    }

    public static String formatCurrency(double amount, String currency) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        // Set the thousands separator depending on the currency
        if ("VND".equals(currency)) {
            symbols.setGroupingSeparator('.');
        } else {
            symbols.setGroupingSeparator(',');
        }

        // Always use a dot for the decimal separator
        symbols.setDecimalSeparator('.');

        // Create a DecimalFormat with the desired symbols and format the amount
        DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);
        return formatter.format(amount);
    }
}
