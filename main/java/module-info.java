module com.daviddang.quiz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.daviddang.quiz to javafx.fxml;
    exports com.daviddang.quiz;
}