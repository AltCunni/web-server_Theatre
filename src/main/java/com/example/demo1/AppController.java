package com.example.demo1;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
    @Autowired
    private TheatreService service;

    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Theatre> listTheatre = service.listAll(keyword);
        model.addAttribute("listTheatre", listTheatre);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewTheatreForm(Model model){
        Theatre theatre = new Theatre();
        model.addAttribute("theatre", theatre);
        return "new_theatre";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTheatre(@ModelAttribute("theatre") Theatre theatre){
        service.save(theatre);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditTheatreFrom(@PathVariable(name="id")Long id){
        ModelAndView mav = new ModelAndView("edit_theatre");
        Theatre theatre = service.get(id);
        mav.addObject("theatre", theatre);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteTheatre(@PathVariable(name="id") Long id){
        service.delete(id);
        return "redirect:/";
    }
    @RequestMapping("/sort")
    public String sortTheatre(Model model) {
        List<Theatre> sortedList = service.sortDate();
        model.addAttribute("listTheatre", sortedList);
        return "index";
    }
    @RequestMapping("/hist")
    public ResponseEntity<byte[]> histogram() throws IOException {
        List<Theatre> performances = service.listAll(null); // Получите список спектаклей
        byte[] imageBytes = service.generatePerformanceHistogram(performances); // Используйте метод для генерации гистограммы
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @RequestMapping("/table")
    public String showTable(Model model) {
        List<Object[]> table = service.tableTheatre();
        model.addAttribute("listTheatre", table);
        return "table_theatre";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleNotFoundException(NoSuchElementException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "Запрашиваемый ресурс не найден.");
        return mav;
    }


}

