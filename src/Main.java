import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Main extends Application{
    public static void main(String[] args) {launch(args);}
    public void start(Stage stage1){
        final String[] info = new String[6];
        stage1.setHeight(600);
        stage1.setWidth(350);
        stage1.setTitle("Laboratoire 5");

        Label utilisateur2 = new Label("Entrez votre nom d'utilisateur: ");
        utilisateur2.setTranslateY(100);
        TextField utilisateur = new TextField();
        utilisateur.setPromptText("Utilisateur");
        utilisateur.setTranslateY(120);

        Label logUtilisateur2 = new Label("Entrez votre nom d'utilisateur: ");
        logUtilisateur2.setTranslateY(100);
        TextField logUtilisateur = new TextField();
        logUtilisateur.setPromptText("Utilisateur");
        logUtilisateur.setTranslateY(120);

        Label motsDePasse2 = new Label("Entrez votre mot de passe: ");
        motsDePasse2.setTranslateY(150);
        PasswordField motsDePase = new PasswordField();
        motsDePase.setPromptText("Mots de passe");
        motsDePase.setTranslateY(170);

        Label logMotsDePasse2 = new Label("Entrez votre mot de passe: ");
        logMotsDePasse2.setTranslateY(150);
        PasswordField logMotsDePase = new PasswordField();
        logMotsDePase.setPromptText("Mots de passe");
        logMotsDePase.setTranslateY(170);

        Button connection = new Button("Connection");
        connection.setTranslateY(200);

        Button inscription = new Button("S'inscrire");
        inscription.setTranslateY(400);

        Button back = new Button("Retour");
        back.setTranslateY(400);
        back.setTranslateX(72);

        Button effacer = new Button("Effacer");
        effacer.setTranslateY(400);
        effacer.setTranslateX(135);

        Button logInscription = new Button("S'inscrire");
        logInscription.setTranslateY(200);
        logInscription.setTranslateX(85);

        Label nom = new Label("Entrez votre nom de famille:");
        nom.setTranslateY(50);
        TextField nom2 = new TextField();
        nom2.setPromptText("Nom de famille");
        nom2.setTranslateY(70);

        Label prenom = new Label("Entrez votre prenom:");
        prenom.setTranslateY(0);
        TextField prenom2 = new TextField();
        prenom2.setPromptText("Prenom");
        prenom2.setTranslateY(20);

        Label confirmation = new Label("Confirmez le mot de passe:");
        confirmation.setTranslateY(200);
        PasswordField confirmation2 = new PasswordField();
        confirmation2.setPromptText("Confirmation");
        confirmation2.setTranslateY(220);

        Label genre = new Label("Entrez votre genre:");
        genre.setTranslateY(250);
        RadioButton homme = new RadioButton("Homme");
        homme.setTranslateY(275);
        RadioButton femme = new RadioButton("Femme");
        femme.setTranslateY(275);
        femme.setTranslateX(70);
        RadioButton autre = new RadioButton("Autre");
        autre.setTranslateY(275);
        autre.setTranslateX(140);
        ToggleGroup sexe = new ToggleGroup();
        homme.setToggleGroup(sexe);
        femme.setToggleGroup(sexe);
        autre.setToggleGroup(sexe);



        Label age = new Label("Entrez votre âge");
        age.setTranslateY(300);
        Spinner age2 = new Spinner(18,150,18);
        age2.setTranslateY(320);

        CheckBox contrat = new CheckBox("Accepter les conditions");
        contrat.setTranslateY(365);

        ProgressIndicator progress1 = new ProgressIndicator();
        Label chargement = new Label("Chargement de la page");
        progress1.setTranslateY(150);
        progress1.setTranslateX(135);
        chargement.setTranslateY(210);
        chargement.setTranslateX(100);

        Label erreur = new Label("");
        erreur.setTranslateY(450);
        erreur.setTextFill(Color.RED);
        Label erreur2 = new Label("");
        erreur2.setTranslateY(250);
        erreur2.setTextFill(Color.RED);

        Group page1 = new Group(logUtilisateur,logUtilisateur2,logMotsDePase,logMotsDePasse2,logInscription,connection,erreur2);
        page1.setTranslateX(75);
        Group page2 = new Group(prenom,prenom2,nom,nom2,utilisateur,utilisateur2,motsDePase,motsDePasse2,confirmation,confirmation2,inscription,genre,age,back,effacer,age2,contrat,homme,femme,autre,erreur);
        page2.setTranslateX(75);
        Group page3 = new Group(progress1,chargement);

        Scene login = new Scene(page1);
        Scene subscribe = new Scene(page2);
        Scene loading = new Scene(page3);
        stage1.setScene(login);
        stage1.show();

        logInscription.setOnAction(event -> stage1.setScene(subscribe));
        back.setOnAction(event -> stage1.setScene(login));
        effacer.setOnAction(event -> {
            prenom2.setText(null);
            nom2.setText(null);
            utilisateur.setText(null);
            motsDePase.setText(null);
            confirmation2.setText(null);
            homme.setSelected(false);
            femme.setSelected(false);
            autre.setSelected(false);
            age2.getValueFactory().setValue(0);
            contrat.setSelected(false);
        });
        connection.setOnAction(event -> {
            try{
                boolean utilOk = false;
                BufferedReader entree = new BufferedReader(
                        new FileReader("Utilisateurs.csv"));
                String ligne = "a";
                do{
                    ligne = entree.readLine();
                    System.out.println(ligne);
                    String list[] = ligne.split(",");
                    for(int i = 0;i<6;i++){
                        System.out.println(list[i]);
                    }
                    if (list[2].equals(logUtilisateur.getText())){
                        utilOk = true;
                        if(list[3].equals(bytesToHex(MotDePasse(logMotsDePase.getText())))){
                            stage1.setScene(loading);
                        }else{
                            System.out.println("mauvais mot de passe");
                            erreur2.setText("Mot de passe incorecte");
                        }
                    }else{
                        if (utilOk){
                            erreur2.setText("Mot de passe incorecte");
                        }else{
                            erreur2.setText("Utilisateur incorecte");
                        }
                        System.out.println("mauvais Utilisateur");

                    }
                }while(ligne != null);
                entree.close();
            }catch (Exception exception){System.out.println("chargement echoue");}


        });
        inscription.setOnAction((ActionEvent event) -> {
            if(contrat.isSelected()){
                if(femme.isSelected()||homme.isSelected()||autre.isSelected()){
                    if(motsDePase.getText().equals(confirmation2.getText())){
                        if(!prenom2.getText().equals("")){
                            if(!nom2.getText().equals("")){
                                if(!utilisateur.getText().equals("")){
                                    if(!motsDePase.getText().equals("")){
                                        List<String> données = new ArrayList<>();
                                        données.add(prenom2.getText());
                                        données.add(nom2.getText());
                                        données.add(utilisateur.getText());
                                        données.add(bytesToHex(MotDePasse(motsDePase.getText())));
                                        if (homme.isSelected()) {
                                            données.add("H");
                                        } else if (femme.isSelected()) {
                                            données.add("F");
                                        } else if (autre.isSelected()) {
                                            données.add("A");
                                        }
                                        données.add(age2.getValue().toString());
                                        String enregistrement = données.stream()
                                                .collect(Collectors.joining(",", "", ""));
                                        System.out.println(enregistrement);
                                        try {
                                            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Utilisateurs.csv", true)));
                                            out.println(enregistrement);
                                            out.close();
                                            erreur2.setText("*Vous pouvez maintenant vous connecté(es)*");
                                            stage1.setScene(login);
                                        } catch (Exception exeption) {
                                            System.out.println("creation impossible");
                                        }
                                    }else{
                                        System.out.println("mot de passe incorecte");
                                        erreur.setText("*mot de passe incorecte*");
                                        stage1.setScene(subscribe);
                                    }

                                }else{
                                    System.out.println("Utilisateur incorecte");
                                    erreur.setText("*Utilisateur incorecte*");
                                    stage1.setScene(subscribe);
                                }

                            }else{
                                System.out.println("Nom incorecte");
                                erreur.setText("*Nom incorecte*");
                                stage1.setScene(subscribe);
                            }

                        }else{
                            System.out.println("Prenom incorecte");
                            erreur.setText("*Prenom incorecte*");
                            stage1.setScene(subscribe);
                        }

                    }else{
                        System.out.println("Confirmation du mot de passe incorrecte");
                        erreur.setText("*Confirmation du mot de passe incorecte*");
                        stage1.setScene(subscribe);
                    }

                }else{
                    System.out.println("vous devez choisir un genre");
                    erreur.setText("*Vous  devez choisir un genre*");
                    stage1.setScene(subscribe);
                }

            }else{
                System.out.println("vous devez accepter les conditions");
                erreur.setText("*Vous devez accepter les conditions*");
                stage1.setScene(subscribe);
            }
        });

    }
    public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    };
    private static byte[] MotDePasse(String motDePasse){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    motDePasse.getBytes(StandardCharsets.UTF_8));
            return encodedhash;
        }
        catch (Exception exeption1){}
        return  null;
    };
}
