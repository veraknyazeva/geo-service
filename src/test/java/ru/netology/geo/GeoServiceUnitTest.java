package ru.netology.geo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.netology.geo.GeoServiceImpl.*;

@ExtendWith(MockitoExtension.class)
public class GeoServiceUnitTest {

    public static final String OTHER_COUNTRY_IP = "100.100.100.100";

    @ParameterizedTest
    @MethodSource("provideTestAndExpectedSources")
    public void test_get_location_by_ip(String ip, Country expectedCountry) {
        GeoService geoService = new GeoServiceImpl();

        Location location = geoService.byIp(ip);

        if (!ip.equals(OTHER_COUNTRY_IP)) {
            assertThat(location.getCountry()).isEqualTo(expectedCountry);
        } else {
            assertThat(location).isNull();
        }

    }

    static Stream<Arguments> provideTestAndExpectedSources() {
        return Stream.of(
                Arguments.arguments(NEW_YORK_IP, Country.USA),
                Arguments.arguments(MOSCOW_IP, Country.RUSSIA),
                Arguments.arguments(LOCALHOST, null),
                Arguments.arguments(OTHER_COUNTRY_IP, null),
                Arguments.arguments("172.0.0.1", Country.RUSSIA),
                Arguments.arguments("96.0.0.1", Country.USA)
        );
    }
}
