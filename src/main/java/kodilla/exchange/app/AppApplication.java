package kodilla.exchange.app;

public class AppApplication {

    public static void main(String[] args) {
        try {
            new Menu().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}