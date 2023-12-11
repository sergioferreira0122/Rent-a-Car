package aluguer.veiculos.aluguerveiculos.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordHashTest {

    @Test
    void hashPassword() {
        var inputPassword = "admin";
        var passwordHashed = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";

        assertEquals(passwordHashed, PasswordHash.hashPassword(inputPassword));
    }
}