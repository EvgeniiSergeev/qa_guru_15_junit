package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import guru.qa.data.Sport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class HomeWork {

    //ТЕСТОВЫЕ ДАННЫЕ: ["Футбол", "Фигурное катание"]
    @ValueSource(strings = {"Футбол", "Фигурное катание"})
    @ParameterizedTest(name = "Проверка числа результатов поиска в Google для запроса {0}")
    // [test_data] == (String testData)
    void sport24SearchCommonTest(String testData) {

        open("https://sport24.ru/");
        $(".Yx61xc [href='/search']").click();
        $("input[type='text']").setValue(testData).pressEnter();
        $$(".OAn8bo")
                .shouldHave(CollectionCondition.size(10));
    }

    static Stream<Arguments> sport24SiteButtonsTextData() {
        return Stream.of(
                Arguments.of(List.of("РПЛ", "Первая лига", "Кубок России", "АПЛ", "Ла Лига", "Серия А",
                        "Бундеслига", "Лига 1", "Лига чемпионов", "Лига Европы", "Лига конференций", "Чемпионат мира", "Квалификация ЧМ - Европа", "Лига наций", "Чемпионат Европы"), Sport.Футбол),
        Arguments.of(List.of("НХЛ", "КХЛ",  "ЧМ по хоккею", "Еврохоккейтур", "ЧМ по хоккею U20", "ЧМ по хоккею U18", "Матч звезд КХЛ", "Матч звезд НХЛ"), Sport.Хоккей)
        );

    }

    @MethodSource("sport24SiteButtonsTextData")
    @ParameterizedTest(name = "Проверка отображения названия кнопок: {1}")
    void sport24SiteButtonsText(List<String> buttonsTexts, Sport sport) {
        open("https://sport24.ru/");
        $$(".ccPW0r fgoFD_").find(text(sport.name())).click();
        $$(".bjFtbP").filter(visible)
                .shouldHave(CollectionCondition.texts(buttonsTexts));
    }

    @EnumSource(Sport.class)
    @ParameterizedTest
    void checkLocaleTest(Sport sport) {
        open("https://sport24.ru/");
        $$(".ccPW0r fgoFD_").find(text(sport.name())).shouldBe(visible);
    }
}