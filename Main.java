import callback.LoginCallback;
import view.LoginView;
import view.ProductView;
import controller.CartController;
import model.User;

public class Main {
    public static void main(String[] args) {
        new LoginView(new LoginCallback() {
            @Override
            public void onLoginSuccess(User user) {
                CartController cartController = new CartController();
                new ProductView(cartController, user);
            }
        });        
    }
}
