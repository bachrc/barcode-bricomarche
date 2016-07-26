package ovh.dessert.brico.stockfiller;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RayonStock extends AppCompatActivity {

    public static ArrayList<String> rayon;
    public boolean add = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rayon_stock);
        rayon = new ArrayList<>();
    }

    public void scanRayon(View v) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        add = true;
        startActivityForResult(intent, 0);
    }

    public void scanStock(View v) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        add = false;
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                String contents = intent.getStringExtra("SCAN_RESULT");
                if(add) {
                    if (!rayon.contains(contents)) {
                        rayon.add(contents);
                        TextView t = (TextView) findViewById(R.id.liste_codes);
                        t.append(contents + "\n");
                    }
                } else {
                    if (rayon.contains(contents)) {
                        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(300);
                        Toast.makeText(RayonStock.this, "Produit à re-stocker !", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RayonStock.this, "Pas besoin de re-stocker.", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else if(resultCode == RESULT_CANCELED){ // Handle cancel
                Log.i("xZing", "Cancelled");
            }
        }
    }
}
