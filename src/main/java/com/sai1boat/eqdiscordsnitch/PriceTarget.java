package com.sai1boat.eqdiscordsnitch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceTarget {

    protected String _itemName;
    protected String _itemNameLowerCase;
    protected Integer _desiredPrice;
    public PriceTarget(String itemName) {
        this._itemName = itemName;
        this._desiredPrice = null;
        init();
    }

    protected Pattern ppPattern;
    protected Pattern kpPattern;
    protected Pattern sellerNamePattern;
    protected Pattern timestampPattern;

    public PriceTarget(String itemName, int plat) {
        this._itemName = itemName;
        this._desiredPrice = plat;
        init();
    }

    private void init() {
        this._itemNameLowerCase = _itemName.toLowerCase();
    }

    public SellerMatchInfo searchForPlatStartingAtPosition(String line, int position) {
        if(ppPattern == null)
            ppPattern = Pattern.compile("[0-9]{1,}");

        if(kpPattern == null)
            kpPattern = Pattern.compile("[0-9|/.]{1,}(?=[kK])");

        if(sellerNamePattern == null)
            sellerNamePattern = Pattern.compile("[a-zA-Z]+(?=\\s)");

        //[Tue Feb 28 17:47:56 2023]
        if(timestampPattern == null)
            timestampPattern = Pattern.compile("");

        Matcher m0 = kpPattern.matcher(line.substring(position));
        Matcher m1 = ppPattern.matcher(line.substring(position));
        Matcher sellerNameMatcher = sellerNamePattern.matcher(line.substring(26));

        String sellerName = "Not Found";
        if (sellerNameMatcher.find()) {
            sellerName = sellerNameMatcher.group();
        }


        if (m0.find()) {
            String kp= m0.group();
            Integer pkFloat = (int)(Float.parseFloat(kp)*1000);
            Boolean targetMet = false;
            if(this._desiredPrice >= pkFloat) {
                targetMet = true;
                return new SellerMatchInfo(sellerName, pkFloat.toString(), targetMet,this);
            }

        }
        else if (m1.find()) {
            String pp = m1.group();
            Integer pkInt = Integer.parseInt(pp);
            Boolean targetMet = false;
            if(this._desiredPrice >= pkInt) {
                targetMet = true;
                return new SellerMatchInfo(sellerName, pp, targetMet,this);
            }

        }
        else if (this._desiredPrice==null){
            return new SellerMatchInfo(sellerName, null, true, this);
        }
        return null;
    }

    public SellerMatchInfo search(String line) {

        int index;
        if((index = line.toLowerCase().indexOf(_itemNameLowerCase))>=0) {
            int moneyStartIndex = index + _itemName.length();
            return searchForPlatStartingAtPosition(line, moneyStartIndex);
         }
        return null;
    }

    public boolean equals (Object o2) {
        if (o2 == null)
            return false;
        else if (o2 instanceof PriceTarget == false)
            return false;
        else {
            return ((PriceTarget) o2)._itemName.equals(this._itemName);
        }
    }

    @Override
    public int hashCode() {
        return this._itemName.hashCode();
    }
}
