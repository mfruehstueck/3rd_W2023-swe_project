package at.onlyquiz.util;

import at.onlyquiz.util.jsonParser.JSON_Parser;
import at.onlyquiz.util.jsonParser.models.User;

import java.util.List;
import java.util.Objects;

public class UserManagement {
  private static List<User> users;
  private static User currentUser;

  public static void init() {
    users = JSON_Parser.read(Configuration.USER_FILE, User.class);
    System.out.println(users.toString());
  }

  public static List<User> get_users() { return users; }
  public static void create_user(User user) {
    JSON_Parser.write(Configuration.USER_FILE, user);
    users.add(user);
  }
  public static User get_currentUser() { return currentUser; }
  public static void set_currentUser(User user) { currentUser = user; }
  public static User get_user_byUsername(String username) {
    for (User u : users) {
      if (Objects.equals(u.getUserName(), username)) return u;
    }
    return null;
  }
}
