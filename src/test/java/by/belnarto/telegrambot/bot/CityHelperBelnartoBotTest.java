package by.belnarto.telegrambot.bot;

import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.ArgumentMatchers.any;
import static java.util.Arrays.asList;

public class CityHelperBelnartoBotTest {

    @Test
    public void onUpdateReceived() {
        CityHelperBelnartoBot bot = Mockito.mock(CityHelperBelnartoBot.class);
        Mockito.doCallRealMethod().when(bot).onUpdatesReceived(any());
        Update update1 = new Update();
        Update update2 = new Update();
        bot.onUpdatesReceived(asList(update1, update2));
        Mockito.verify(bot).onUpdateReceived(update1);
        Mockito.verify(bot).onUpdateReceived(update2);
    }

}