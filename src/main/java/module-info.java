module com.ou.oubusmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires AnimateFX;
    requires java.sql;
    requires java.base;
    requires de.jensd.fx.glyphs.fontawesome;
    
    opens com.ou.pojo to javafx.base;
    opens com.ou.oubusmanager to javafx.fxml;
    exports com.ou.oubusmanager;
}
