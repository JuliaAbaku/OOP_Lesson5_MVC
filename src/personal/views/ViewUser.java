package personal.views;

import personal.controllers.UserController;
import personal.model.User;
import personal.views.validator.NameValidator;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ViewUser {

    private UserController userController;

    public ViewUser(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com = Commands.NONE;

        while (true) {
            String command = prompt("Введите команду: ");
            try {
                com = Commands.valueOf(command.toUpperCase()); // привели ввод с консоли к одному регистру
                if (com == Commands.EXIT) return;
                switch (com) {
                    case CREATE:
                        createUser(); // Создала метод отдельный
                        
                        break;
                    case READ:
                        readUser(); // Создала метод отдельный

                        break;
                    case LIST:
                        readList(); // Создали метод, который булет выводить весь список контактов
                        break;
                    case UPDATE:
                        updateUser();
                        break;
                    case DELETE:
                        deleteUser();
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void deleteUser() throws Exception {
        readList();                             // получили список юзеров
        User user = getUser();                  // выбрали нужного юзера
        User deletedUser = userController.deleteUser(user);
        System.out.println("Контакт удалён");
    }

    private void updateUser() throws Exception { // Создали метод изменения информации о юзере: получили список юзеров
        readList();                             // получили список юзеров
        User user = getUser();                  // выбрали нужного юзера
        User updatedUser = getNewUser();        // создали нового юзера
        updatedUser.setId(user.getId());        // присвоили новому юзеру ID ранее выбранного юзера
        User savedUser = userController.updateUser(updatedUser); // сохранили измененного юзера
        System.out.println(savedUser);

    }

    private void readList() {
        List<User> listUsers = userController.readAllUsers();
        for (User user:listUsers) {
            System.out.println(user);
        }
    }

    private void readUser() throws Exception { // Выделили часть строк в новый метод getUser()
        User user = getUser();
        System.out.println(user);
    }

    private User getUser() throws Exception {
        String id = prompt("Идентификатор пользователя: ");
        User user = userController.readUser(id);
        return user;
    }

    private void createUser() throws Exception { // Выделили часть строк в новый метод getNewUser()
        User user = getNewUser();
        userController.saveUser(user);
    }

    private User getNewUser() throws Exception {
        String firstName = prompt("Имя: ");
        new NameValidator(firstName).validate();
        String lastName = prompt("Фамилия: ");
        new NameValidator(lastName).validate();
        String phone = prompt("Номер телефона: ");
        User user = new User(firstName, lastName, phone);
        return user;
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
