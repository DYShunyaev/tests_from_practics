import config.GUIBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.MainPage;

@Tag("GUI")
@DisplayName("Проверка Главной страницы")
public class MainPageTests extends GUIBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Базовый тест, проверка отображения Главной страницы")
    public void test() {
        Assertions.assertTrue(mainPage.getTitle().contains("QualIT"));
    }

    @Test
    @DisplayName("Проверка перехода на главную страницу")
    public void testGoodsPage() {
        Assertions.assertTrue(mainPage.goToGoodsPage().getCurrentUrl().contains("food"));
    }
}
