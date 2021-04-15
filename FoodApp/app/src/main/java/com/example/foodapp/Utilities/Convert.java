package com.example.foodapp.Utilities;

import java.text.DecimalFormat;

public class Convert {
    public String longToVNDFormat(long number){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(number) + " VNĐ";
    }

    public long VNDFormatToLong(String vndFormat){
        return Long.parseLong(
                vndFormat
                        .substring(0, vndFormat.length() - 4)
                        .replaceAll("[$,]", "")
        );
    }
}
