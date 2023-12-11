module aluguer.veiculos.aluguerveiculos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.mail;
    requires com.google.gson;

    opens aluguer.veiculos.aluguerveiculos.Preferences;
    opens aluguer.veiculos.aluguerveiculos.Models to javafx.base;
    opens aluguer.veiculos.aluguerveiculos to javafx.fxml;
    exports aluguer.veiculos.aluguerveiculos;
    exports aluguer.veiculos.aluguerveiculos.Controllers;
    opens aluguer.veiculos.aluguerveiculos.Controllers to javafx.fxml;
}