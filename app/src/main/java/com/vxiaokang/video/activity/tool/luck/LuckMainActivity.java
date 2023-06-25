package com.vxiaokang.video.activity.tool.luck;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.vxiaokang.video.R;

import java.util.Random;

public class LuckMainActivity extends AppCompatActivity {


    private LuckyMonkeyPanelView lucky_panel;
    private Button btn_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_main);

        lucky_panel = findViewById(R.id.lucky_panel);
        btn_action =  findViewById(R.id.btn_action);

        btn_action.setOnClickListener(v -> {
            if (!lucky_panel.isGameRunning()) {
                lucky_panel.startGame();
            } else {
                int stayIndex = new Random().nextInt(8);
                Log.e("LuckyMonkeyPanelView", "====stay===" + stayIndex);
                lucky_panel.tryToStop(stayIndex);
            }
        });
    }
}
