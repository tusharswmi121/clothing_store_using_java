package callback;

import model.User;

public interface LoginCallback {
    void onLoginSuccess(User user);  // Called when login is successful
}
