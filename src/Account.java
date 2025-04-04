public class Account {

    private String name;
    private String password;

    public Account(String name, String password) {

        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return name + ";" + password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
