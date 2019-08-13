package by.belnarto.telegrambot.bot;

import by.belnarto.telegrambot.model.City;
import by.belnarto.telegrambot.model.CityDto;
import by.belnarto.telegrambot.service.CityService;
import by.belnarto.telegrambot.service.mapper.CityMapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CityHelperBelnartoBot extends TelegramLongPollingBot {

    private TelegramBotsApi telegramBotsApi;
    private CityService cityService;
    private CityMapper cityMapper;

    public CityHelperBelnartoBot(final TelegramBotsApi telegramBotsApi,
                                 final CityService cityService,
                                 final CityMapper cityMapper) {
        this.telegramBotsApi = telegramBotsApi;
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String inMessage;
        String chatId;

        if (update.hasEditedMessage()) {
            inMessage = update.getEditedMessage().getText();
            chatId = update.getEditedMessage().getChatId().toString();
        } else {
            inMessage = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();
        }

        switch (inMessage) {

            case "/start":
                sendMsg(chatId, "Добро пожаловать. Кнопки управления представлены ниже.");
                break;

            case "Доступные города":
                cityService.findAll().stream()
                        .map(cityMapper::toDto)
                        .map(CityDto::getCityName)
                        .forEachOrdered(s -> sendMsg(chatId, s));
                break;

            case "Помощь":
                sendMsg(chatId, "Пишите письма на belnarto@gmail.com");
                break;

            default:
                Optional<City> cityOptional = cityService.findByCityName(inMessage);
                if (cityOptional.isPresent()) {
                    sendMsg(chatId, cityMapper.toDto(cityOptional.get()).toString());
                } else {
                    sendMsg(chatId, "У меня нет информации о городе: " + inMessage);
                }
                break;
        }
    }

    private void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        setButtons(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            //log.log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    @Override
    public String getBotUsername() {
        return "cityHelperBelnartoBot";
    }

    @Override
    public String getBotToken() {
        return "978393601:AAGAdOSAbnsQYyVHA7pi-dZ9UZGHTvj46Sc";
    }

    private void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Доступные города"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Помощь"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    @PostConstruct
    public void registryBot() {
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}
