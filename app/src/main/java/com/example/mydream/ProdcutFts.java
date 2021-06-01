package com.example.mydream;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4(contentEntity = Product.class)
@Entity(tableName = "ProdcutFts")
public class ProdcutFts {

    @PrimaryKey(autoGenerate = true) @NonNull
    @ColumnInfo(name = "rowid")
    public int itemId;

    @ColumnInfo(name = "itemName")
    public String itemName;

    @ColumnInfo(name = "itemSimilarName")
    public String itemSimilarName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSimilarName() {
        return itemSimilarName;
    }

    public void setItemSimilarName(String itemSimilarName) {
        this.itemSimilarName = itemSimilarName;
    }
}
