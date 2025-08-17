package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.example.controller.CalculatorController;
import javafx.geometry.Pos;

public class NumberCalculatorApp extends Application {

    @Override
    public void start(Stage stage) {
        // 컨트롤러 초기화
        CalculatorController controller = new CalculatorController();
        
        // 입력 섹션 (왼쪽 열)
        VBox inputSection = createInputSection();
        
        // 버튼 섹션 (오른쪽 열)
        VBox buttonSection = createButtonSection(controller);
        
        // 상단 2열 레이아웃
        HBox topLayout = new HBox(20);
        topLayout.setPadding(new Insets(20, 20, 10, 20));
        
        // 왼쪽 열: 입력 공간
        VBox leftColumn = new VBox(15);
        leftColumn.setMinWidth(180); // 최소 너비 더 줄임
        leftColumn.setPrefWidth(280); // 선호 너비 조정
        leftColumn.setMaxWidth(Double.MAX_VALUE);
        leftColumn.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9; -fx-padding: 15;");
        leftColumn.getChildren().addAll(inputSection, controller.getNumberSelection().createSelectionPanel());
        
        // 오른쪽 열: 버튼들
        VBox rightColumn = new VBox(15);
        rightColumn.setMinWidth(250); // 최소 너비 더 줄임
        rightColumn.setPrefWidth(350); // 선호 너비 조정
        rightColumn.setMaxWidth(Double.MAX_VALUE);
        rightColumn.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9; -fx-padding: 15;");
        rightColumn.getChildren().add(buttonSection);
        
        topLayout.getChildren().addAll(leftColumn, rightColumn);
        
        // HBox에서 각 열이 반응형으로 동작하도록 설정
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        HBox.setHgrow(rightColumn, Priority.ALWAYS);
        
        // 하단 결과 영역
        VBox resultSection = new VBox(10);
        resultSection.setPadding(new Insets(10, 20, 20, 20));
        resultSection.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9; -fx-padding: 15;");
        VBox.setVgrow(resultSection, Priority.ALWAYS);
        
        Label resultLabel = new Label("분할 결과:");
        resultLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        resultSection.getChildren().addAll(resultLabel, controller.getResultDisplay().getScrollPane());
        
        // 전체 레이아웃 (상단 2열 + 하단 결과)
        VBox mainLayout = new VBox(0);
        mainLayout.getChildren().addAll(topLayout, resultSection);
        
        // 컨트롤러 설정
        TextField inputField = (TextField) inputSection.lookup("#inputField"); // ID로 안전하게 접근
        controller.setInputField(inputField);
        
        // 입력 필드 리스너 추가
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    controller.getResultDisplay().displayTotalLength(value);
                } catch (NumberFormatException e) {
                    // 숫자가 아닌 경우 무시
                }
            }
        });
        
        Scene scene = new Scene(mainLayout, 1200, 700); // 기본 크기 증가
        stage.setTitle("길이 분할 계산기");
        stage.setMinWidth(1150); // 최소 너비 증가
        stage.setMinHeight(600); // 최소 높이 설정
        stage.setScene(scene);
        stage.show();
    }
    
    private VBox createInputSection() {
        VBox section = new VBox(5);
        
        Label inputLabel = new Label("나눌 숫자를 입력하세요:");
        TextField inputField = new TextField();
        inputField.setId("inputField"); // ID 추가로 안전한 접근
        
        section.getChildren().addAll(inputLabel, inputField);
        
        return section;
    }
    
    private VBox createButtonSection(CalculatorController controller) {
        VBox section = new VBox(0); // 간격을 0으로 설정하여 버튼들이 붙어있도록 함
        section.setMinWidth(230); // 최소 너비 줄임
        section.setAlignment(Pos.CENTER); // 중앙 정렬
        
        Button remainderBtn = new Button("선택한 단위로 나머지 구하기");
        remainderBtn.setMinWidth(180); // 최소 너비 줄임
        remainderBtn.setPrefWidth(220); // 선호 너비 조정
        remainderBtn.setMaxWidth(Double.MAX_VALUE);
        remainderBtn.setMaxHeight(Double.MAX_VALUE); // 높이 제한 해제
        remainderBtn.setMinHeight(50); // 최소 높이 설정
        
        Button lengthDivisionBtn = new Button("선택한 단위로 숫자 분할하기");
        lengthDivisionBtn.setMinWidth(180); // 최소 너비 줄임
        lengthDivisionBtn.setPrefWidth(220); // 선호 너비 조정
        lengthDivisionBtn.setMaxWidth(Double.MAX_VALUE);
        lengthDivisionBtn.setMaxHeight(Double.MAX_VALUE); // 높이 제한 해제
        lengthDivisionBtn.setMinHeight(50); // 최소 높이 설정
        
        Button distributeBtn = new Button("나머지 분배하기");
        distributeBtn.setMinWidth(180); // 최소 너비 줄임
        distributeBtn.setPrefWidth(220); // 선호 너비 조정
        distributeBtn.setMaxWidth(Double.MAX_VALUE);
        distributeBtn.setMaxHeight(Double.MAX_VALUE); // 높이 제한 해제
        distributeBtn.setMinHeight(50); // 최소 높이 설정
        
        // 버튼 이벤트 연결
        remainderBtn.setOnAction(e -> controller.handleRemainderCalculation());
        lengthDivisionBtn.setOnAction(e -> controller.handleLengthDivisionCalculation());
        distributeBtn.setOnAction(e -> controller.handleDistributionCalculation());
        
        section.getChildren().addAll(remainderBtn, lengthDivisionBtn, distributeBtn);
        
        // 각 버튼이 VBox의 높이를 균등하게 나누어 가지도록 설정
        VBox.setVgrow(remainderBtn, Priority.ALWAYS);
        VBox.setVgrow(lengthDivisionBtn, Priority.ALWAYS);
        VBox.setVgrow(distributeBtn, Priority.ALWAYS);
        
        return section;
    }
    


    public static void main(String[] args) {
        launch(args);
    }
} 