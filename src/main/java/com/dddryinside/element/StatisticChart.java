package com.dddryinside.element;

import com.dddryinside.model.Training;
import com.dddryinside.service.DataBaseAccess;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.util.List;

public class StatisticChart extends VBox {
    public StatisticChart() {
        this.setMaxWidth(800);
        this.setMaxHeight(300);


        List<Training> trainingHistory = DataBaseAccess.getTrainingHistory();
        if (trainingHistory.size() == 0) {
            Training training = new Training();
            training.setAmount(0);
            training.setDate("Сегодня");

            trainingHistory.add(training);
        }

        this.getChildren().add(getChart(trainingHistory));
    }

    public LineChart<String, Number> getChart(List<Training> trainingHistory) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLowerBound(0);
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(getMaximumInTrainingHistory(trainingHistory));

        if (trainingHistory.size() == 0) {
            yAxis.setUpperBound(1);
        }

        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);


        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("История тренировок за неделю");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Количество тренировок");

        for (int i = 0; i < trainingHistory.size(); i++) {
            series.getData().add(new XYChart.Data<>(trainingHistory.get(i).getDate(),
                    trainingHistory.get(i).getAmount()));
        }
        lineChart.getData().add(series);

        return lineChart;
    }

    private static int getMaximumInTrainingHistory(List<Training> trainingHistory) {
        int maximum = 0;

        for (Training training : trainingHistory) {
            if (training.getAmount() > maximum) {
                maximum = training.getAmount();
            }
        }

        return maximum;
    }
}
