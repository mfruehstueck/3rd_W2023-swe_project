package at.onlyquiz.controller;

import at.debugtools.DebugTools;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.util.Configuration;
import at.onlyquiz.util.userManagement.UserManagement;
import at.onlyquiz.util.jsonParser.models.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

enum LoginType {
  GUEST(true),
  USER(false);
  private final boolean loginType;
  LoginType(boolean loginType) { this.loginType = loginType; }
  public Boolean get_loginType() { return this.loginType; }
}

public class LoginController extends BaseController {
  public RadioButton ui_loginGuest_radioButton;
  public TextField ui_loginGuest_nickname;
  public Label ui_loginGuest_info;
  public RadioButton ui_loginUser_radioButton;
  public TextField ui_loginUser_username;
  public PasswordField ui_loginUser_password;
  public Button ui_login_button;
  public GridPane ui_container;
  public ToggleGroup ui_loginGroup;
  private LoginType current_loginType;
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);

    UserManagement.init();

    current_loginType = LoginType.USER;

    ui_loginGuest_nickname.textProperty().addListener((observable, oldValue, newValue) -> {
      System.out.println(DebugTools.debugLine(new Throwable()) + "nickname changed to " + newValue);
      ui_loginGuest_info.setVisible(newValue.isEmpty());
    });

    change_loginType(current_loginType);
    ui_loginGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      switch (((RadioButton) ui_loginGroup.getSelectedToggle()).getId()) {
        case "ui_loginGuest_radioButton" -> change_loginType(LoginType.GUEST);
        case "ui_loginUser_radioButton" -> change_loginType(LoginType.USER);
        default -> {
          System.out.println(DebugTools.debugLine(new Throwable()) + "loginTypeChange failed, radioButton not found");
          throw new RuntimeException();
        }
      }
    });
  }
  public void onClick_login_button(ActionEvent actionEvent) {
    User current_user;

    switch (current_loginType) {
      case GUEST -> current_user = handle_guestLogin();
      case USER -> {
        current_user = handle_userLogin();
        if (current_user == null) return;
      }
      default -> {
        System.out.println(DebugTools.debugLine(new Throwable()) + "login failed, radioButton not found");
        throw new RuntimeException();
      }
    }

    UserManagement.set_currentUser(current_user);

    set_view(get_stage(ui_container), View.MENU_VIEW);
  }

  private void change_loginType(LoginType loginType) {
    current_loginType = loginType;
    ui_loginGuest_nickname.setVisible(loginType.get_loginType());
    ui_loginGuest_info.setVisible(loginType.get_loginType());

    ui_loginUser_username.setVisible(!loginType.get_loginType());
    ui_loginUser_password.setVisible(!loginType.get_loginType());
  }

  private User handle_guestLogin() {
    String nickname = ui_loginGuest_nickname.getText();

    if (nickname.isEmpty()) return new User(Configuration.DEFAULT_GUEST);
    else return new User(nickname);
  }
  private User handle_userLogin() {
    String username = ui_loginUser_username.getText();
    String password = ui_loginUser_password.getText();

    User user = UserManagement.get_user_byUsername(username);
    if (user != null) {
      if (!user.checkPassword(password)) {
        user = null;
        showAlert("login info", "authentication failed", Alert.AlertType.WARNING);
      }
    } else {
      if (password.isEmpty() || username.isEmpty()) {
        showAlert("login info", "please provide username and password", Alert.AlertType.INFORMATION);
        return user;
      }
      Optional<ButtonType> result = showAlert("creating User", "username: " + username + "\n\n comfirm creation?", Alert.AlertType.CONFIRMATION);
      if (result.isPresent() && result.get() == ButtonType.YES) {
        user = new User(username, password);
        UserManagement.create_user(user);
      }
    }

    return user;
  }
}
