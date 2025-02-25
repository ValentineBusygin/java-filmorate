package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaRatingController {
    private final MpaRatingService mparService;

    @GetMapping
    public List<MpaRating> findAll() {
        log.info("Запрос на получение всех рейтингов");
        return mparService.findAll();
    }

    @GetMapping("/{id}")
    public MpaRating findById(@PathVariable Long id) {
        log.info("Запрос на получение рейтинга с id = {}", id);
        return mparService.findById(id);
    }
}
