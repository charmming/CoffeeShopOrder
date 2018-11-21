package com.royalcoast.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // The number of Coffees
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     * This method is called when the order button is pressed
     */

    public void submitOrder(View view){
        EditText editText = findViewById(R.id.name_text_view);
        String name = editText.getText().toString();

        CheckBox whippedCreamCheckedBox = findViewById(R.id.whipped_cream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckedBox.isChecked();

        CheckBox chocolateCreamCheckedBox = findViewById(R.id.chocolate_cream_checkBox);
        boolean hasChocolateCream = chocolateCreamCheckedBox.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolateCream);
        String priceMessage = submitOrderSummary(price, hasWhippedCream, hasChocolateCream, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }


    /**
     * Creating the submit order summary
     * returning a string value
     */
    public String submitOrderSummary(int price, boolean addedWhippedCream, boolean addedChocolateCream, String name){
        String priceMessage = getString(R.string.order_summary_name) + name;
        priceMessage += "\n" + getString(R.string.added_whipped_cream) + " " + addedWhippedCream;
        priceMessage += "\n" +getString(R.string.added_chocolate_cream) + " " + addedWhippedCream;
        priceMessage += "\n" + getString(R.string.quantity_message) + quantity;
        priceMessage += "\n" + getString(R.string.total)+ price;
        priceMessage += "\n" + getString(R.string.Thank_you);
        return priceMessage;
    }

    /**
     * This method is for calculating the price method
     * returning an integer value
     */

    private int calculatePrice(boolean addedWhippedCream, boolean addedChocolateCream){
        int basePrice = 5;

        if(addedWhippedCream){
            basePrice += 1;
        }

        if(addedChocolateCream){
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    /**
     *
     * This method displays quantity value on the screen
     */

    public void displayQuantity(int number){
        TextView quantity_text_view = findViewById(R.id.quantity_text_view);
        quantity_text_view.setText("" + number);
    }

    /**
     * This method displays texts on the screen
     */

    public void displayMessage(String message){
        TextView orderSummeryTextView = findViewById(R.id.order_summary_text_view);
        orderSummeryTextView .setText(message);
    }


    /**
     * This method is for the increment of quantity
     */

    public void increment(View view){
        if(quantity == 100) {
            Toast.makeText(this, "You can't have more than 100 cups of Coffee", Toast.LENGTH_LONG).show();
            return;
        }
        displayQuantity(quantity +=1);
    }

    /**
     * This method is for the increment of quantity
     */

    public void decrement(View view){
        if(quantity == 1){
            Toast.makeText(this, "You can't have less than 1 cup of Coffee", Toast.LENGTH_LONG).show();
            return;
        }
        displayQuantity(quantity -= 1);
    }
}
