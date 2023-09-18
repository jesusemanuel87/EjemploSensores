package com.example.ejemplosensores;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> lecturaMutableProximity;
    private MutableLiveData<String> lecturaMutableAccelerometer;
    private MutableLiveData<String> lecturaMutableGyroscope;
    private SensorManager sm;
    private EscuchaSensorProximity esProximity;
    private EscuchaSensorAccelerometer esAccelerometer;
    private EscuchaSensorGyroscope esGyroscope;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getLecturaMutableProximity() {
        if (lecturaMutableProximity == null) {
            lecturaMutableProximity = new MutableLiveData<>();
        }
        return lecturaMutableProximity;
    }

    public LiveData<String> getLecturaMutableAccelerometer() {
        if (lecturaMutableAccelerometer == null) {
            lecturaMutableAccelerometer = new MutableLiveData<>();
        }
        return lecturaMutableAccelerometer;
    }

    public LiveData<String> getLecturaMutableGyroscope() {
        if (lecturaMutableGyroscope == null) {
            lecturaMutableGyroscope = new MutableLiveData<>();
        }
        return lecturaMutableGyroscope;
    }

    public void obtenerSensores() {
        sm = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> proximitySensors = sm.getSensorList(Sensor.TYPE_PROXIMITY);
        List<Sensor> accelerometerSensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        List<Sensor> gyroscopeSensors = sm.getSensorList(Sensor.TYPE_GYROSCOPE);

        if (!proximitySensors.isEmpty()) {
            Sensor proximitySensor = proximitySensors.get(0);
            esProximity = new EscuchaSensorProximity();
            sm.registerListener(esProximity, proximitySensor, SensorManager.SENSOR_DELAY_UI);
        }

        if (!accelerometerSensors.isEmpty()) {
            Sensor accelerometerSensor = accelerometerSensors.get(0);
            esAccelerometer = new EscuchaSensorAccelerometer();
            sm.registerListener(esAccelerometer, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }

        if (!gyroscopeSensors.isEmpty()) {
            Sensor gyroscopeSensor = gyroscopeSensors.get(0);
            esGyroscope = new EscuchaSensorGyroscope();
            sm.registerListener(esGyroscope, gyroscopeSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void paraLecturas() {
        if (esProximity != null) {
            sm.unregisterListener(esProximity);
        }
        if (esAccelerometer != null) {
            sm.unregisterListener(esAccelerometer);
        }
        if (esGyroscope != null) {
            sm.unregisterListener(esGyroscope);
        }
    }

    private class EscuchaSensorProximity implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float value = sensorEvent.values[0];
            String resultado = "Sensor de Proximidad: " + value;
            lecturaMutableProximity.postValue(resultado);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

    private class EscuchaSensorAccelerometer implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            String resultado = "Acelerómetro - X: " + x + " Y: " + y + " Z: " + z;
            lecturaMutableAccelerometer.postValue(resultado);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

    private class EscuchaSensorGyroscope implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            String resultado = "Giroscopio - X: " + x + " Y: " + y + " Z: " + z;
            lecturaMutableGyroscope.postValue(resultado);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }
}
