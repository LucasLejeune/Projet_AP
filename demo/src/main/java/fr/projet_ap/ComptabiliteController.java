package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComptabiliteController {

    @FXML
    private TextField id;

    @FXML
    private PasswordField mdp;

    @FXML
    private Label pasBon;

    @FXML
    private TextField Matricule;

    @FXML
    private TextField nom;

    @FXML
    private Label QuantiteKilometrage;

    @FXML
    private Label QuantiteNuitee;

    @FXML
    private Label QuantiteRepas;

    @FXML
    private void switchToClient() throws IOException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT Matricule FROM users WHERE Matricule = " + id.getText() + " AND Mot_de_passe = "
                    + mdp.getText() + "; ";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            String ident = result.getString(1);

            if (id.getText().equals(ident) || id.getText().equals("admin") && mdp.getText().equals("admin")) {
                App.setRoot("ClientC");
            } else {
                pasBon.setText("Identifiant ou mot de passe invalide");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void switchToAcceuil() throws IOException {
        App.setRoot("Acceuil");
    }

    private void validation() throws IOException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT Matricule, nom, prenom FROM users;";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String ident = result.getString(1);
                String Nom = result.getString(2);
                String prenom = result.getString(3);

                String Nom_prenom = Nom + " " + prenom;

                if (Matricule.getText().equals(ident) && nom.getText().equals(Nom_prenom)) {
                    String ficheSql = "SELECT QuantiteNuitee, QuantiteRepas, QuantiteKilometrage FROM fiches JOIN users ON VisiteurMatricule = Matricule WHERE Matricule = "
                            + ident + ";";

                    statement = conn.createStatement();
                    result = statement.executeQuery(ficheSql);

                    String QNuitee = result.getString(1);
                    String QRepas = result.getString(2);
                    String QKilometrage = result.getString(3);

                    QuantiteNuitee.setText(QNuitee);
                    QuantiteRepas.setText(QRepas);
                    QuantiteKilometrage.setText(QKilometrage);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}