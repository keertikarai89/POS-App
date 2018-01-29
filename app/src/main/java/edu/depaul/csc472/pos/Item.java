package edu.depaul.csc472.pos;

public class Item {

        public String Name;
        public int Quantity;
        public double UnitPrice;


        public Item(String itemName,int qty, double unitPrice){
            this.Name = itemName;
            this.Quantity = qty;
            this.UnitPrice = unitPrice;
        }

    }

