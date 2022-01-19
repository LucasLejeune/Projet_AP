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
    private void switchToClient() throws IOException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";
        Boolean bonId = false;
        
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT identifiant, Mot_de_passe, role FROM users";
 
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            
            while (result.next()){
                String ident = result.getString(1);
                String pass = result.getString(2);
                String role = result.getString(3);
                
                if (id.getText().equals(ident) && mdp.getText().equals(pass) && role.equals("Compta")){
                    bonId = true;
                    App.setRoot("ClientC");
                }               
            }
            if (bonId == false){
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
}