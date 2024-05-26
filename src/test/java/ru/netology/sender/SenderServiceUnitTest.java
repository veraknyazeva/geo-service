package ru.netology.sender;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static ru.netology.geo.GeoServiceImpl.MOSCOW_IP;
import static ru.netology.geo.GeoServiceImpl.NEW_YORK_IP;

@ExtendWith(MockitoExtension.class)
public class SenderServiceUnitTest {

    public static final String EXPECTED_RU_MESSAGE = "Добро пожаловать";
    public static final String EXPECTED_US_MESSAGE = "Welcome";

    private static GeoService geoService;
    private static LocalizationService localizationService;
    private static MessageSender sender;

    @BeforeAll
    public static void set_up() {
        geoService = spy(GeoServiceImpl.class);
        localizationService = spy(LocalizationServiceImpl.class);
        sender = new MessageSenderImpl(geoService, localizationService);
    }

    @ParameterizedTest
    @ValueSource(strings = {MOSCOW_IP, "172.1.0.0", "172.0.0.1", "172.0.1.0"})
    public void test_send_only_ru_locale_messages(String xRealIp) {

        MessageSender sender = new MessageSenderImpl(geoService, localizationService);

        String actualMessage = sender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, xRealIp));

        assertThat(actualMessage).isEqualTo(EXPECTED_RU_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {NEW_YORK_IP, "96.0.0.1", "96.0.1.0", "96.1.0.0"})
    public void test_send_only_us_messages(String xRealIp) {
        MessageSender sender = new MessageSenderImpl(geoService, localizationService);

        String actualMessage = sender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, xRealIp));

        assertThat(actualMessage).isEqualTo(EXPECTED_US_MESSAGE);
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void test_send_only_us_message_if_x_real_ip_not_present(String xRealIp) {

        String actualMessage = sender.send(new HashMap<>());

        assertThat(actualMessage).isEqualTo(EXPECTED_US_MESSAGE);
    }
}
