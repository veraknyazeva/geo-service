package ru.netology.i18n;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LocalizationServiceUnitTest {

    public static final String EXPECTED_RU_MESSAGE = "Добро пожаловать";
    public static final String EXPECTED_US_MESSAGE = "Welcome";

    @ParameterizedTest
    @MethodSource("provideCountriesAndExpectedMessages")
    public void test_get_locale_by_country(Country country, String expectedMessage) {
        LocalizationService localizationService = new LocalizationServiceImpl();

        String actualMessage = localizationService.locale(country);

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    static Stream<Arguments> provideCountriesAndExpectedMessages() {
        return Stream.of(
                Arguments.arguments(Country.RUSSIA, EXPECTED_RU_MESSAGE),
                Arguments.arguments(Country.USA, EXPECTED_US_MESSAGE),
                Arguments.arguments(Country.BRAZIL, EXPECTED_US_MESSAGE),
                Arguments.arguments(Country.GERMANY, EXPECTED_US_MESSAGE)
        );
    }
}
