package com.example.demo1;

import java.awt.*;
import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.time.LocalDate;
import java.util.*;

import org.jfree.data.statistics.HistogramDataset;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheatreService {
    @Autowired private TheatreRepository repo;

    public List<Theatre> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public void save(Theatre theatre) {

        repo.save(theatre);
    }

    public Theatre get(Long id) {

        return repo.findById(id).get();
    }

    public void delete(Long id) {

        repo.deleteById(id);
    }

    public List<Theatre> sortDate() {

        return repo.sort();
    }

    public byte[] generatePerformanceHistogram(List<Theatre> performances) throws IOException {
        // Словарь для хранения количества спектаклей по дням
        Map<String, Integer> performanceCountByDate = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); // Убедитесь, что формат соответствует вашему dtim

        for (Theatre performance : performances) {
            try {
                // Преобразуем строку даты в объект Date
                Date date = dateFormat.parse(performance.getDtim());
                String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);

                // Увеличиваем счетчик для этой даты
                performanceCountByDate.put(dateString, performanceCountByDate.getOrDefault(dateString, 0) + 1);
            } catch (ParseException e) {
                e.printStackTrace(); // Обработка ошибки парсинга даты
            }
        }

        // Создаем набор данных для гистограммы
        HistogramDataset dataset = new HistogramDataset();

        // Добавляем данные в набор
        for (Map.Entry<String, Integer> entry : performanceCountByDate.entrySet()) {
            dataset.addSeries(entry.getKey(), new double[]{entry.getValue()}, 1); // Используем 1 бин для каждой даты
        }

        // Создаем гистограмму
        JFreeChart histogram = ChartFactory.createHistogram(
                "Гистограмма количества спектаклей по дням", // Заголовок
                "Дата", // Ось X
                "Количество спектаклей", // Ось Y
                dataset
        );

        // Сохраняем гистограмму в массив байтов
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(baos, histogram, 600, 400);
        return baos.toByteArray(); // Возвращаем массив байтов
    }

    public List<Object[]> tableTheatre () {
        return repo.forTable();
    }
}
