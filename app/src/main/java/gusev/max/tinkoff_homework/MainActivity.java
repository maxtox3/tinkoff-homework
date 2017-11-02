package gusev.max.tinkoff_homework;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Map;
import java.util.TreeMap;
import static android.R.layout.simple_spinner_item;

public class MainActivity extends AppCompatActivity {

    private float scaleX, scaleY, absMaxX, absMaxY, size;
    private Map<Float, Float> points;
    private final Integer[] step = {5, 10};
    private final String[] colors = {"red", "green", "blue", "black"};
    private int lineColor, dotColor, steps;
    private boolean isDrawn;
    private GraphView graphView;
    private EditText editX, editY;

    //Protected methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        steps = 5;
        dotColor = Color.RED;
        lineColor = Color.RED;
        absMaxX = 0;
        absMaxY = 0;

        points = new TreeMap<>();

        isDrawn = false;

        graphView = (GraphView) findViewById(R.id.graphview);
        editX = (EditText) findViewById(R.id.editX);
        editY = (EditText) findViewById(R.id.editY);

        size = 300 * 5 / 6;
        scaleX = 0;
        scaleY = 0;

        Button drawChartButton = (Button) findViewById(R.id.draw_chart_button);
        drawChartButton.setOnClickListener(initDrawChartButtonClickListener());

        Button addBtn = (Button) findViewById(R.id.add_button);
        addBtn.setOnClickListener(initAddButtonClickListener());

        //Спиннер для выбора количества шагов
        Spinner spinnerSteps = (Spinner) findViewById(R.id.steps_spinner);
        ArrayAdapter<Integer> adapterSteps = new ArrayAdapter<>(getApplicationContext(), simple_spinner_item, step);
        adapterSteps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSteps.setAdapter(adapterSteps);

        AdapterView.OnItemSelectedListener stepsItemSelectedListener = initStepsItemSelectListener(graphView);
        spinnerSteps.setOnItemSelectedListener(stepsItemSelectedListener);

        //Спиннер для выбора цвета линии
        Spinner colorSpinner = (Spinner) findViewById(R.id.chart_colors_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), simple_spinner_item, colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener colorItemSelectedListener = initColorItemSelectedListener();
        colorSpinner.setOnItemSelectedListener(colorItemSelectedListener);
    }

    //Private methods

    private View.OnClickListener initDrawChartButtonClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphView.chart(points, scaleX, scaleY, getApplicationContext(), lineColor);
                isDrawn = true;
            }
        };
    }

    private View.OnClickListener initAddButtonClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (editX.getText().toString().length() == 0 || editY.getText().toString().length() == 0) {
                        throw new NumberFormatException();
                    }

                    float x = Float.parseFloat(editX.getText().toString());
                    float y = Float.parseFloat(editY.getText().toString());

                    for (Float key : points.keySet()) {
                        if (x == key) {
                            graphView.addPoint(x, points.get(key), scaleX, scaleY, getApplicationContext(), Color.WHITE);
                        }
                    }
                    points.put(x, y);

                    if (x > absMaxX) {
                        absMaxX = x;
                        scaleX = size / absMaxX;
                        refreshPointsAndAxises(x, y, graphView);
                    }

                    if (y > absMaxY) {
                        absMaxY = y;
                        scaleY = size / absMaxY;
                        refreshPointsAndAxises(x, y, graphView);
                    }

                    graphView.addPoint(x, y, scaleX, scaleY, getApplicationContext(), dotColor);
                    if (isDrawn) {
                        refreshPointsAndAxises(0, 0,graphView);
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private AdapterView.OnItemSelectedListener initStepsItemSelectListener(final GraphView graphView){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer item = (Integer) parent.getItemAtPosition(position);
                switch (item) {
                    case 5:
                        steps = 5;
                        refreshPointsAndAxises(0, 0, graphView);
                        break;
                    case 10:
                        steps = 10;
                        refreshPointsAndAxises(0, 0, graphView);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
    }

    private AdapterView.OnItemSelectedListener initColorItemSelectedListener(){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getItemAtPosition(position);
                switch (item) {
                    case "blue":
                        lineColor = Color.BLUE;
                        break;
                    case "black":
                        lineColor = Color.BLACK;
                        break;
                    case "red":
                        lineColor = Color.RED;
                        break;
                    case "green":
                        lineColor = Color.GREEN;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
    }

    private void refreshPointsAndAxises(float x, float y, GraphView graphView) {
        graphView.refresh();

        for (Map.Entry point : points.entrySet()) {
            graphView.addPoint((Float) point.getKey(), (Float) point.getValue(), scaleX, scaleY, getApplicationContext(), dotColor);
        }

        if(x != 0 && y != 0){
            graphView.drawXScale(x, steps);
            graphView.drawYScale(y, steps);
        } else {
            graphView.drawXScale(absMaxX, steps);
            graphView.drawYScale(absMaxY, steps);
        }

        if (isDrawn) {
            graphView.chart(points, scaleX, scaleY, getApplicationContext(), lineColor);
        }
    }
}

