package ru.yandex.practicum.filmorate.controller.mparating;

import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.MpaRatingController;
import ru.yandex.practicum.filmorate.service.MpaRatingService;
import ru.yandex.practicum.filmorate.storage.mparating.InMemoryMpaRatingStorage;

@SpringBootTest(classes = {MpaRatingController.class, MpaRatingService.class, InMemoryMpaRatingStorage.class})
public class InMemoryMpaRatingControllerTest extends MpaRatingControllerTest {
}
