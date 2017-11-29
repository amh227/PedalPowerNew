package com.pedalpower.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.FileNotFoundException;
public class MainActivity extends AppCompatActivity {
static boolean testing=true;

public static void main(String[] args) throws FileNotFoundException {
    //main variables

//Variables-----------------------------------------------------------------------------------
    data d=new data();
    int lastSecond=0;       //used to know when to update UI time
    int lastNano=0;         //used to know when to update UI power :: starting with every 10th of a second
    double totalPower=0;    //used for average power
    double totalTimeInms=0;  //used for average power

    //initialize-----------------------------------------------------------------------------------
    //get information via blluetooth
    if (!testing){
        d.getData();
    }
    //newData.getData.myListener();
    if (testing){
        d.getData();
    }
//printout all data after filtering and cleaning algorithms
    System.out.println("Total Points: "+d.totalPoints);
    System.out.println("Total errors: "+d.errorCount);
    System.out.println("New Data: ");
    int i;
    for (i=0;i<(d.totalPoints-1);i++){
        System.out.println(" "+d.myPoints.get(i) );
    }
}






protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
}

public void historyDisplay(View view) {
    Intent intent = new Intent(MainActivity.this, history.class);
    startActivity(intent);
}

public void achievementsDisplay(View view) {
    Intent intent = new Intent(MainActivity.this, achievements.class);
    startActivity(intent);
}

public void startDisplay(View view) {
    Intent intent = new Intent(MainActivity.this, start.class);
    startActivity(intent);
}

}
