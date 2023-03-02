package com.sai1boat.eqdiscordsnitch;

public class SellerMatchInfo {

    protected PriceTarget _target;
    protected String _sellerName;
    protected String _sellerPrice;

    public SellerMatchInfo (String sellerName, String sellerPrice, PriceTarget target) {
        this._sellerName = sellerName;
        this._sellerPrice = sellerPrice;
        this._target = target;
    }



    public String toString() {

        if (this._sellerPrice != null){
            return _sellerName +" is auctioning a "+_target._itemName+" for "+_sellerPrice+"p";
        }
        else {
            return _sellerName +" is auctioning a "+_target._itemName+".";
        }

    }
}
