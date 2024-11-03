package com.example.demo1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HistogramService {

    public byte[] generatePerformanceHistogram(List<Theatre> performances) throws IOException {

        Map<String, Integer> performanceCountByDate = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        for (Theatre performance : performances) {
            try {

                Date date = dateFormat.parse(performance.getDtim());
                String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);

                // Увеличиваем счетчик для этой даты
                performanceCountByDate.put(dateString, performanceCountByDate.getOrDefault(dateString, 0) + 1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Создаем набор данных для гистограммы
        HistogramDataset dataset = new HistogramDataset();

        // Добавляем данные в набор
        for (Map.Entry<String, Integer> entry : performanceCountByDate.entrySet()) {
            dataset.addSeries(entry.getKey(), new double[]{entry.getValue()}, 1); // Используем 1 бин для каждой даты
        }


        JFreeChart histogram = ChartFactory.createHistogram(
                "Гистограмма количества спектаклей по дням", // Заголовок
                "Дата", // Ось X
                "Количество спектаклей", // Ось Y
                dataset
        );


        File tempFile = File.createTempFile("performance_histogram", ".png");
        ChartUtils.saveChartAsPNG(tempFile, histogram, 600, 400); // Ширина и высота в пикселях

        // Читаем файл в массив байтов
        byte[] imageBytes = java.nio.file.Files.readAllBytes(tempFile.toPath());

        // Удаляем временный файл
        tempFile.delete();

        return imageBytes;
    }
}
