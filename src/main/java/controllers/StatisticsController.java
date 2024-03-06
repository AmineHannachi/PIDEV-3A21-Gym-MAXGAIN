package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import models.Offres;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import services.OffresService;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    @FXML
    private PieChart statisticsPieChart;

    private final OffresService offresService = new OffresService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Get data for statistics
            List<Offres> offresList = offresService.recuperer();

            // Populate the PieChart
            populatePieChart(offresList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }
    }

    private void populatePieChart(List<Offres> offresList) {
        // Create data for the PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Offres offre : offresList) {
            pieChartData.add(new PieChart.Data(offre.getDescription(), offre.getPrix()));
        }

        // Set the data to the PieChart
        statisticsPieChart.setData(pieChartData);
    }

    public void generatePDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Load your TrueType font file
            PDType0Font font = PDType0Font.load(document, new File("C:\\Users\\amine\\Downloads\\radiant-kingdom-font\\RadiantKingdom-mL5eV.ttf"));

            float startY = 700; // Initial Y coordinate
            float lineHeight = 14; // Adjust this value based on your font size

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(50, startY);

            // Add a title
            contentStream.showText("Statistics from PieChart:");
            contentStream.newLine();

            // Extract data from PieChart
            ObservableList<PieChart.Data> pieChartData = statisticsPieChart.getData();
            for (PieChart.Data data : pieChartData) {
                String entry = String.format("%s: %.2f", data.getName(), data.getPieValue());
                contentStream.showText(entry);
                contentStream.newLineAtOffset(0, -lineHeight); // Move to the next line
            }

            contentStream.endText();
            contentStream.close();

            // Append timestamp to the file name to make it unique
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            String fileName = "generated_" + timestamp + ".pdf";

            document.save(fileName);
            document.close();

            System.out.println("PDF generated successfully: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error during PDF generation: " + e.getMessage());
        }
    }



}


