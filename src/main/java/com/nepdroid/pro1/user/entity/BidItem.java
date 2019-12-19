package com.nepdroid.pro1.user.entity;


import javax.persistence.*;

@Entity
@Table(name = "bid_item")
public class BidItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bid_id")
    private int bidId;

    @Column(name = "fk_item_Id")
    private int itemId;

    @Column(name = "bid_amount")
    private double amount;

    @Column(name = "fk_user_id")
    private int appUserId;

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }
}
