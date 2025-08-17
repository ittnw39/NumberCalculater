package org.example.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.model.CalculationResult;
import java.util.List;

public class ResultDisplayComponent {
    
    private final VBox resultContainer;
    private final ScrollPane scrollPane;
    private final LengthVisualizationComponent lengthVisualizer;
    
    public ResultDisplayComponent() {
        this.resultContainer = new VBox(10);
        this.resultContainer.setPadding(new Insets(10));
        this.resultContainer.setFocusTraversable(false); // 포커스 비활성화 추가
        this.lengthVisualizer = new LengthVisualizationComponent();
        
        this.scrollPane = new ScrollPane();
        this.scrollPane.setContent(resultContainer);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setPrefHeight(400);
        this.scrollPane.setFocusTraversable(false); // 포커스 비활성화
    }
    
    public ScrollPane getScrollPane() {
        return scrollPane;
    }
    
    public void clearResults() {
        resultContainer.getChildren().clear();
    }
    
    public void showMessage(String message) {
        clearResults();
        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Arial", 14));
        messageLabel.setStyle("-fx-text-fill: #D32F2F;");
        resultContainer.getChildren().add(messageLabel);
    }
    
    public void displayTotalLength(int totalLength) {
        clearResults();
        VBox totalLengthBar = lengthVisualizer.createTotalLengthBar(totalLength);
        resultContainer.getChildren().add(totalLengthBar);
    }
    
    public void displayRemainderResults(List<CalculationResult> results) {
        clearResults();
        
        Label titleLabel = new Label("나머지 계산 결과");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resultContainer.getChildren().add(titleLabel);
        
        for (CalculationResult result : results) {
            VBox resultBox = createResultBox(
                result.getDividend() + " % " + result.getDivisor() + " = " + result.getRemainder(),
                "나머지: " + result.getRemainder()
            );
            resultContainer.getChildren().add(resultBox);
        }
    }
    
    public void displayLengthDivisionResults(List<CalculationResult> results) {
        clearResults();
        
        Label titleLabel = new Label("분할 결과");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resultContainer.getChildren().add(titleLabel);
        
        for (CalculationResult result : results) {
            // 길이 분할 시각화 컴포넌트 추가
            VBox lengthVisualization = lengthVisualizer.createLengthVisualization(result);
            resultContainer.getChildren().add(lengthVisualization);
        }
    }
    
    public void displayDistributionResults(List<CalculationResult> results) {
        clearResults();
        
        Label titleLabel = new Label("나머지 분배 결과");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resultContainer.getChildren().add(titleLabel);
        
        for (CalculationResult result : results) {
            // 나머지 분배 시각화 컴포넌트 추가
            VBox distributionVisualization = lengthVisualizer.createDistributionVisualization(result);
            resultContainer.getChildren().add(distributionVisualization);
        }
    }
    
    public void displayCustomDivisionResult(CalculationResult result) {
        clearResults();
        
        // 길이 분할과 분배를 모두 표시
        VBox lengthVisualization = lengthVisualizer.createLengthVisualization(result);
        resultContainer.getChildren().add(lengthVisualization);
        
        if (result.hasRemainder()) {
            VBox distributionVisualization = lengthVisualizer.createDistributionVisualization(result);
            resultContainer.getChildren().add(distributionVisualization);
        }
    }
    
    private VBox createResultBox(String title, String content) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Label contentLabel = new Label(content);
        contentLabel.setFont(Font.font("Arial", 12));
        
        box.getChildren().addAll(titleLabel, contentLabel);
        return box;
    }
} 