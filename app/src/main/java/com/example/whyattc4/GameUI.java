package com.example.whyattc4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.graphics.Point;  //holds 2 coordinates provides methods to use points on a screen
import android.view.View;
import android.widget.Button; // Provides an interface to interact with user
import android.widget.GridLayout; //Provides for creating the Grid Layout
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GameUI extends AppCompatActivity {
    private Button[][] buttons;
    private TextView status;
    private C4Game  instance;
    private int topRow = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = new C4Game();
        buildGuiByCode();
    }


    public void buildGuiByCode(){
        //Get the width of the screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w=size.x/instance.COLUMN;

        GridLayout gridLayout=new GridLayout(this);
        gridLayout.setColumnCount(instance.COLUMN);
        gridLayout.setRowCount(instance.ROW + 1);

        //Create the buttons and add them to the gridLayout
        buttons = new Button[instance.ROW][instance.COLUMN];
        ButtonHandler bh = new ButtonHandler();
        for(int row=0; row< instance.ROW; row++) {
            for (int col = 0; col < instance.COLUMN; col++) {
                buttons[row][col]= new Button(this);
                buttons[row][col].setTextSize((int)(w*.2));
                buttons[row][col].setOnClickListener(bh);
                gridLayout.addView(buttons[row][col],w,w);
            }
        }
        //Set gridlayout as the view fort his activity
        setContentView(gridLayout);

        status= new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(instance.ROW, 1);
        GridLayout.Spec columnSpec = GridLayout.spec(0, instance.COLUMN);
        GridLayout.LayoutParams lpStatus = new GridLayout.LayoutParams(rowSpec,columnSpec);
        status.setLayoutParams(lpStatus);
        setContentView(gridLayout);

        //set up status' characteristics
        status.setWidth(instance.COLUMN * w);
        status.setHeight(w);
        status.setGravity(Gravity.CENTER);
        status.setBackgroundColor(Color.GREEN);
        status.setTextSize((int)(w*.15));
        status.setText(instance.result());

        gridLayout.addView(status);
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {

            for (int row = 0; row < instance.ROW; row++) {
                for (int column = 0; column < instance.COLUMN; column++) {
                    if (v == buttons[row][column]) {
                        update(row, column);
                    }
                }
            }
        }
    }
    public void update(int row, int col) {
        int play = instance.play(row,col);

        SimulateMovement(row, col);

        if(play == 1){
            buttons[row][col].setText("O");
            buttons[row][col].setTextColor(Color.parseColor("#FF0000"));
        }else if(play == 2){
            buttons[row][col].setText("O");
            buttons[row][col].setTextColor(Color.parseColor("#FFFF00"));
        }else if(play == 3){
            buttons[row][col].setText("O");
            buttons[row][col].setTextColor(Color.parseColor("#FFFF00"));
        }
        if(instance.isGameOver()){
            status.setBackgroundColor(Color.RED);
            enableButtons(false);
            status.setText(instance.result());
            showNewGameDialog();
        }
    }

    public void SimulateMovement(int row, int col){
            final Handler handler = new Handler(Looper.getMainLooper());
            int rPos = 5;
            int play = instance.play(row,col);
             while (rPos > row) {
                 handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int rPos = row;
                        if(play == 1){
                            buttons[rPos][col].setText("O");
                            buttons[rPos][col].setTextColor(Color.parseColor("#FF0000"));
                        }else if(play == 2){
                            buttons[rPos][col].setText("O");
                            buttons[rPos][col].setTextColor(Color.parseColor("#FFFF00"));
                        }else if(play == 3){
                            buttons[rPos][col].setText("O");
                            buttons[rPos][col].setTextColor(Color.parseColor("#FFFF00"));
                        }
                    }
                }, 10000);
                rPos--;
            }

    }
    public void enableButtons(boolean enabled){
        for(int row = 0; row < instance.ROW; row++){
            for(int col = 0; col < instance.COLUMN; col++){
                buttons[row][col].setEnabled(enabled);
            }
        }
    }

    public void showNewGameDialog(){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setMessage("Play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }

    private class PlayDialog implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int id){
            if(id == -1){ //yes button
                instance.resetGame();
                enableButtons(true);
                resetButtons();
                status.setBackgroundColor(Color.GREEN);
                status.setText(instance.result());
            }else if(id ==-2){
                GameUI.this.finish();
            }
        }
    }

    public void resetButtons(){
        for(int row = 0; row < instance.ROW; row++){
            for(int col = 0; col < instance.COLUMN; col++){
                buttons[row][col].setText("");
            }
        }
    }
}
