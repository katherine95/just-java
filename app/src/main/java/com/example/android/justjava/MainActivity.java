/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 *
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            //show an error message as a toast
            Toast.makeText(this, "No more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            //exit this method early because there is nothing left to do
            return;
        }
        quantity = quantity + 1 ;
        display(quantity);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            //show an error message as toast
            Toast.makeText(this, "No less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
            //exit this method early because there is nothing left to do
            return;
        }
        quantity = quantity - 1 ;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.v("Mainactivity ", "YourName: " + name);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("Mainactivity ", "Has whipped cream: " + hasWhipppedCream);
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage =createOrderSummary(price,name,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this;
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        if (addChocolate){
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     * @param price of the order
     * @param name of the customer
     * @param addWhippedCream whether or not to add the topping
     * @param addChocolate whether or not to add the topping
     * @return text summary
     */
    private String createOrderSummary(int price,String name, boolean addWhippedCream, boolean addChocolate){
            String priceMessage = "Name: " + name;
            priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
            priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
            priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
            priceMessage += "\n" + getString(R.string.order_summary_price,
                    NumberFormat.getCurrencyInstance().format(price));
            priceMessage += "\n" + getString(R.string.thank_you);
            return priceMessage;
        }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}