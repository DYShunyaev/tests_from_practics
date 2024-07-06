import config.GUIBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.MainPage;


public class MainPageTests extends GUIBase {
    MainPage mainPage = new MainPage();
    @Test
    public void test() {
        String tittle = driver.getTitle();
        Assertions.assertTrue(tittle.contains("QualIT"));
    }

    @Test
    public void testGoodsPage(){
        mainPage.goToGoodsPage();
        Assertions.assertTrue(driver.getCurrentUrl().contains("food"));
    }
}
