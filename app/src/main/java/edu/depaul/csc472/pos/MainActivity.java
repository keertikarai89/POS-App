package edu.depaul.csc472.pos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends Activity {

    Order order;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText unitPrice = findViewById(R.id.text3);
        final EditText quantity = findViewById(R.id.text2);
        final TextView tv1 = findViewById(R.id.text4);
        final TextView tv2 = findViewById(R.id.text5);

        Button b1 = findViewById(R.id.button1);
        Button b2 = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.button3);

        //AutoCompleteText View
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Fooditems);

        final AutoCompleteTextView itemName = findViewById(R.id.text1);
        itemName.setAdapter(adapter);

        //Fooditem and Price mapping
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("pizza", "4.00");
        hashMap.put("burger", "3.00");
        hashMap.put("fries", "2.50");
        hashMap.put("coke", "1.50");
        hashMap.put("falafal", "6.00");

        //Keyboard Action button to fill the unit price of the item
        itemName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getAdapter().getItem(position);
                if (Arrays.asList(Fooditems).contains(selectedItem.toLowerCase())) {

                    unitPrice.setText(hashMap.get(selectedItem.toLowerCase()));
                } else {
                    unitPrice.setText("0.00");
                }

                InputMethodManager input = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

        });


        //New Order button
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemName.setText("");
                quantity.setText("1");
                unitPrice.setText("$0.00");
                tv1.setText("");
                tv2.setText("");


            }
        });

        //New Item button
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                order = AddNewItem(order);
                itemName.setText("");
                quantity.setText("1");
                unitPrice.setText("$0.00");

            }


        });

        //Total Button
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double total = 0.0;
                String Summary = "";
                if (order != null && order.TotalItems.size() != 0) {
                    order = AddNewItem(order);
                    for (Item it1 : order.TotalItems) {
                        total = total + (it1.UnitPrice * it1.Quantity);
                        tv1.setText("$" + Double.toString(total));
                        Summary = Summary + it1.Name + "x" + Integer.toString(it1.Quantity) + "\r\n";
                        tv2.setText(Summary);

                    }
                }
                else {
                    String ItemName1 = itemName.getText().toString();
                    Integer Quantity1 = Integer.parseInt(quantity.getText().toString());
                    Double UnitPrice1 = Double.parseDouble(unitPrice.getText().toString());
                    if ((ItemName1 != null && ItemName1 != "") && Quantity1 > 0 && UnitPrice1 > 0) {
                        total = total + (Quantity1 * UnitPrice1);
                        tv1.setText("$" + Double.toString(total));
                        Summary = Summary + ItemName1 + "x" + Integer.toString(Quantity1) + "\r\n";
                        tv2.setText(Summary);
                    }


                }
            }
        });
    }


    protected Order AddNewItem(Order order){

        AutoCompleteTextView itemName = findViewById(R.id.text1);
        EditText unitPrice = findViewById(R.id.text3);
        EditText quantity = findViewById(R.id.text2);

        String ItemName1 = itemName.getText().toString();
        Integer Quantity1 = Integer.parseInt(quantity.getText().toString());
        Double UnitPrice1 = Double.parseDouble(unitPrice.getText().toString());
        if ((ItemName1 != null && ItemName1 != "") && Quantity1 > 0 && UnitPrice1 > 0) {
            item = new Item(ItemName1, Quantity1, UnitPrice1);
            if (order == null) {
                order = new Order();
            }
            order.TotalItems.add(item);
        }
        return order;
    }



    static final String[] Fooditems = new String[]{
                "burger", "pizza", "fries", "coke", "falafal"


        };


}
