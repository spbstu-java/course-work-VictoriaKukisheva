module lab4 {
    requires javafx.controls;
    requires javafx.fxml;

    opens lab4 to javafx.fxml;
    exports lab4;

    // Add these if you need to access other packages
    opens lab1 to javafx.fxml;
    opens lab2 to javafx.fxml;
    opens lab3 to javafx.fxml;

    exports lab1;
    exports lab2;
    exports lab3;
}