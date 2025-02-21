package ru.yandex.practicum.filmorate.exception;

public class ExceptionMessages {
    public static final String USER_NOT_FOUND = "Пользователь с id = %d не найден";
    public static final String USER_ALREADY_EXISTS = "Такой пользователь уже существует";
    public static final String USER_EMAIL_ALREADY_EXISTS = "Пользователь с таким email уже существует";
    public static final String USER_LOGIN_ALREADY_EXISTS = "Пользователь с таким логином уже существует";
    public static final String USER_SPACES_IN_LOGIN = "Логин не может содержать пробелы";
    public static final String USER_ID_NOT_FOUND = "ID пользователя не указан";
    public static final String FILM_NOT_FOUND = "Фильм с id = %d не найден";
    public static final String INVALID_RELEASE_DATE = "Дата релиза не может быть раньше %s";
    public static final String MPA_RATING_NOT_FOUND = "Рейтинг с id = %d не найден";
    public static final String GENRE_NOT_FOUND = "Фильм содержит отсутствующие в базе id жанров %s";
    public static final String CLEAN_DB_TABLE_NOT_SUPPORTED = "Очистка таблицы в БД не поддерживается";
    public static final String GENRE_NOT_FOUND_MESSAGE = "Не найден жанр с id = %s";
    public static final String MPARATING_NOT_FOUND_MESSAGE = "Не найден рейтинг с id = %s";
}
