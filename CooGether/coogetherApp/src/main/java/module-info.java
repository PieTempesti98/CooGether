module it.unipi.lmmsdb.coogether.coogetherapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unipi.lmmsdb.coogether.coogetherapp to javafx.fxml;
    exports it.unipi.lmmsdb.coogether.coogetherapp;
}