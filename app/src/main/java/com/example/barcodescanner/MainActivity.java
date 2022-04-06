package com.example.barcodescanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button2);
        button.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        scancode();
    }

    private void scancode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(Captureart.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult (int requestcode, int resultcode, Intent data){

        IntentResult result = IntentIntegrator.parseActivityResult(requestcode,resultcode,data);
        if (result !=null){
            if (result.getContents()!=null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scanner Result");
                builder.setPositiveButton("Scan again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scancode();
                    }
                }).setNegativeButton("Finish ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            else {
                Toast.makeText(this, " no result ", Toast.LENGTH_SHORT).show();
            }
        }else{
        super.onActivityResult(requestcode, resultcode,data);
        }
    }
}