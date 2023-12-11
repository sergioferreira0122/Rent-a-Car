package aluguer.veiculos.aluguerveiculos.Preferences;

public class Preferences {
    //private String tema;
    private boolean guardar_credenciais;
    private String email;
    private String password;

    public Preferences(boolean guardar_credenciais, String email, String password) {
        this.guardar_credenciais = guardar_credenciais;
        if (guardar_credenciais) {
            this.email = email;
            this.password = password;
        }
    }

    public boolean isGuardar_credenciais() {
        return guardar_credenciais;
    }

    public void setGuardar_credenciais(boolean guardar_credenciais) {
        this.guardar_credenciais = guardar_credenciais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
