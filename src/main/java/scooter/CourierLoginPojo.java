package scooter;

public class CourierLoginPojo {
    private String login;
    private String password;

    public CourierLoginPojo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierLoginPojo() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static CourierLoginPojo getCredentials(CourierRegisterPojo courierRegisterPojo){
        return new CourierLoginPojo(courierRegisterPojo.getLogin(),courierRegisterPojo.getPassword());
    }
}
