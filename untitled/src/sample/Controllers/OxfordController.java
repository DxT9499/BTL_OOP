package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class OxfordController
{
    @FXML
    private WebView webView;
    public void initialize() {
        WebEngine web = webView.getEngine();
        web.load("https://www.oxfordlearnersdictionaries.com/definition/english/h_1?q=h");
    }
}
