package umaikaze.duke.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Collections;

// Solution adapted from https://github.com/nus-cs2103-AY1920S2/duke/blob/master/tutorials/javaFxTutorialPart4.md
public class DialogBox extends VBox {
    @FXML
    private Text dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private HBox hBox;

    public DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        dialog.setWrappingWidth(360);
        dialog.setTextAlignment(TextAlignment.RIGHT);
        displayPicture.setImage(img);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.hBox.setStyle("-fx-background-color: #5F758E; -fx-background-insets: 5; -fx-background-radius: 20;");
        return db;
    }

    public static DialogBox getDukeDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    public void bindWidthProperty(ObservableValue<? extends Number> v) {
        hBox.prefWidthProperty().bind(v);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(hBox.getChildren());
        Collections.reverse(tmp);
        hBox.getChildren().setAll(tmp);
        hBox.setAlignment(Pos.TOP_LEFT);
        dialog.setTextAlignment(TextAlignment.LEFT);
    }
}
