module com.librarytask {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;


    opens com.librarytask to javafx.fxml, org.junit.platform.commons;
    exports com.librarytask;
}