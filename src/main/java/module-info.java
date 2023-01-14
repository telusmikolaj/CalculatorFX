module com.telusmikolaj.calc.calculatorfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;


    opens com.telusmikolaj.calc.calculatorfx to javafx.fxml;
    exports com.telusmikolaj.calc.calculatorfx;
}