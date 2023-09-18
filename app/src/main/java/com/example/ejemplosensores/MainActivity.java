package com.example.ejemplosensores;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.ejemplosensores.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mv;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainActivityViewModel.class);

        mv.getLecturaMutableProximity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvProximity.setText(s);
            }
        });

        mv.getLecturaMutableAccelerometer().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvAccelerometer.setText(s);
            }
        });

        mv.getLecturaMutableGyroscope().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvGyroscope.setText(s);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv.obtenerSensores();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv.paraLecturas();
    }
}
