module com.telusmikolaj.calc.calculatorfx {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.telusmikolaj.calc.calculatorfx to javafx.fxml;
    exports com.telusmikolaj.calc.calculatorfx;
}