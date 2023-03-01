package com.sai1boat.eqdiscordsnitch;

import org.junit.Assert;

class PriceTargetTest {

    String line1 = "Undertree auctions, 'WTS Chipped Velium Amulet 150 | Stave of Shielding 2.5k'";
    String line2 = "Undertree auctions, 'WTS Chipped Velium Amulet 150 | Stave of Shielding 2.5K'";
    String line3 = "Undertree auctions, 'WTS Chipped Velium Amulet 150 | Stave of Shielding 2.5kp'";
    String line4 = "Undertree auctions, 'WTS Chipped Velium Amulet 150 | Stave of Shielding 2.5KP'";
    String line5 = "Undertree auctions, 'WTS Chipped Velium Amulet 150 | Stave of Shielding 2500'";
    String line6 = "Undertree auctions, 'WTS Chipped Velium Amulet 150 | Stave of Shielding 2500p'";
    String line7 = "Undertree auctions, 'WTS Chipped Velium Amulet 150 | Stave of Shielding 2500P'";
    //this mistakenly says "Polyrain is selling a Fungus Covered Scale Tunic for 2p"
    String line8 = "Polyrain auctions, 'WTS Fungus Covered Scale Tunic, Flawless Diamond, Pristine Emerald x2'";
    //this line doesn't trip the "Fungi Covered Great Staff" 8000 price target. Should it? Maybe
    // user has option to choose what happens when price not specified?
    String line9 = "Potvendor auctions, 'WTS Tantor's Tusk, Fungi Covered Great Staff'";

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void searchForPlatStartingAtPosition() {
        PriceTarget t = new PriceTarget("Stave of Shielding");
        SellerMatchInfo plat1 = t.searchForPlatStartingAtPosition(line1, line1.indexOf(t._itemName));
        SellerMatchInfo plat2 = t.searchForPlatStartingAtPosition(line2, line2.indexOf(t._itemName));
        SellerMatchInfo plat3 = t.searchForPlatStartingAtPosition(line3, line3.indexOf(t._itemName));
        SellerMatchInfo plat4 = t.searchForPlatStartingAtPosition(line4, line4.indexOf(t._itemName));
        SellerMatchInfo plat5 = t.searchForPlatStartingAtPosition(line5, line5.indexOf(t._itemName));
        SellerMatchInfo plat6 = t.searchForPlatStartingAtPosition(line6, line6.indexOf(t._itemName));
        SellerMatchInfo plat7 = t.searchForPlatStartingAtPosition(line7, line7.indexOf(t._itemName));
        Assert.assertEquals("2500", plat1._sellerPrice);
        Assert.assertEquals("2500", plat2._sellerPrice);
        Assert.assertEquals("2500", plat3._sellerPrice);
        Assert.assertEquals("2500", plat4._sellerPrice);
        Assert.assertEquals("2500", plat5._sellerPrice);
        Assert.assertEquals("2500", plat6._sellerPrice);
        Assert.assertEquals("2500", plat7._sellerPrice);




    }

    @org.junit.jupiter.api.Test
    void searchForPlatStartingAtPosition_numberAppearingElsewhereInLineCausingFalsePositiveAmount(){
        PriceTarget f = new PriceTarget("Fungus Covered Scale Tunic", 40000);
        SellerMatchInfo plat8 = f.searchForPlatStartingAtPosition(line8, line8.indexOf(f._itemName));
        Assert.assertEquals(null, plat8._sellerPrice);
    }


}