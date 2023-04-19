package com.gbsys.informe;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Month;
import java.util.Properties;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Configuration Model.
 *
 * @author Herman Barrantes
 */
public record ConfigurationModel(
        StringProperty nameProperty,
        ObjectProperty<Month> monthProperty,
        StringProperty reportProperty,
        StringProperty observationProperty,
        Properties properties) {

    private static final String FILE_NAME = System.getProperty("user.home") + File.separator + ".informe.properties";

    public ConfigurationModel() {
        this(
                new SimpleStringProperty(),
                new SimpleObjectProperty<>(),
                new SimpleStringProperty(),
                new SimpleStringProperty(),
                new Properties()
        );
    }

    public void load() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists() || !file.isFile()) {
            file.createNewFile();
        }
        properties.load(new FileReader(file));
        setName(properties.getProperty("name", ""));
        setMonth(Month.valueOf(properties.getProperty("month", Month.JANUARY.name())));
        setReport(properties.getProperty("report", "SISA"));
        setObservation(properties.getProperty("observation", ""));
    }

    public void save() throws IOException {
        properties.put("name", getName());
        properties.put("month", getMonth().name());
        properties.put("report", getReport());
        properties.put("observation", getObservation());
        properties.store(new FileWriter(FILE_NAME), null);
    }

    public String getName() {
        return nameProperty.get();
    }

    public void setName(String name) {
        this.nameProperty.set(name);
    }

    public Month getMonth() {
        return monthProperty.get();
    }

    public void setMonth(Month month) {
        this.monthProperty.set(month);
    }

    public String getReport() {
        return reportProperty.get();
    }

    public void setReport(String report) {
        this.reportProperty.set(report);
    }

    public String getObservation() {
        return observationProperty.get();
    }

    public void setObservation(String observation) {
        this.observationProperty.set(observation);
    }

    @Override
    public Properties properties() {
        return new Properties(properties);
    }

}
