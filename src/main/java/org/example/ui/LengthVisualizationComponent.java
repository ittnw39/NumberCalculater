package org.example.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.model.CalculationResult;
import javafx.scene.layout.StackPane;

public class LengthVisualizationComponent {
    
    private static final double BAR_HEIGHT = 40;
    private static final double TOTAL_BAR_WIDTH = 1000; // 전체 바의 고정 너비를 800에서 600으로 조정
    private static final Color TOTAL_COLOR = Color.GRAY;
    private static final Color UNIT_COLOR = Color.BLUE;
    private static final Color REMAINDER_COLOR = Color.RED;
    private static final Color DISTRIBUTED_COLOR = Color.GREEN;
    
    public VBox createTotalLengthBar(int totalLength) {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        
        // 제목
        Label titleLabel = new Label("전체: " + totalLength);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // 전체 길이 바
        Rectangle totalBar = new Rectangle(TOTAL_BAR_WIDTH, BAR_HEIGHT);
        totalBar.setFill(TOTAL_COLOR);
        totalBar.setStroke(Color.BLACK);
        totalBar.setStrokeWidth(2);
        
        HBox bar = new HBox(1);
        bar.setPadding(new Insets(5));
        bar.setPrefWidth(TOTAL_BAR_WIDTH);
        bar.getChildren().add(totalBar);
        
        container.getChildren().addAll(titleLabel, bar);
        return container;
    }
    
    public VBox createLengthVisualization(CalculationResult result) {
        VBox container = new VBox(15);
        container.setPadding(new Insets(15));
        container.setStyle("-fx-border-color: #4CAF50; -fx-border-radius: 8; -fx-background-color: #f0f8f0; -fx-border-width: 2;");
        
        // 제목
        Label titleLabel = new Label(String.format("%d ÷ %d", result.getTotalLength(), result.getUnitLength()));
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: #2E7D32;");
        
        // 1단계: 기본 분할
        VBox step1Box = createStep1BasicDivision(result);
        
        container.getChildren().add(titleLabel);
        container.getChildren().add(step1Box);
        
        return container;
    }
    
    public VBox createDistributionVisualization(CalculationResult result) {
        System.out.println("=== createDistributionVisualization 호출됨 ===");
        System.out.println("Total Length: " + result.getTotalLength());
        System.out.println("Unit Length: " + result.getUnitLength());
        System.out.println("Remaining Length: " + result.getRemainingLength());
        System.out.println("Full Units: " + result.getFullUnits());
        System.out.println("==========================================");
        
        VBox container = new VBox(15);
        container.setPadding(new Insets(15));
        container.setStyle("-fx-border-color: #FF9800; -fx-border-radius: 8; -fx-background-color: #FFF3E0; -fx-border-width: 2;");
        
        // 제목
        Label titleLabel = new Label(String.format("%d ÷ %d - 나머지 분배", result.getTotalLength(), result.getUnitLength()));
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: #F57C00;");
        
        // 2단계: 분배 (나머지가 있는 경우만)
        VBox step2Box = null;
        // 나머지가 0인 경우에도 2단계 표시
        step2Box = createStep2Distribution(result);
        
        container.getChildren().add(titleLabel);
        if (step2Box != null) {
            container.getChildren().add(step2Box);
        }
        
        return container;
    }
    
    private VBox createStep1BasicDivision(CalculationResult result) {
        VBox stepBox = new VBox(10);
        stepBox.setPadding(new Insets(10));
        stepBox.setStyle("-fx-border-color: #2196F3; -fx-border-radius: 5; -fx-background-color: #E3F2FD; -fx-border-width: 1;");
        
        // 단계 제목
        Label stepTitle = new Label("1단계: 기본 분할");
        stepTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        stepTitle.setStyle("-fx-text-fill: #1976D2;");
        
        // 수식
        Label formula = new Label(String.format("%d = %d × %d + %d", 
            result.getTotalLength(), result.getFullUnits(), result.getUnitLength(), result.getRemainingLength()));
        formula.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        // 시각적 바와 라벨을 포함하는 컨테이너
        VBox barContainer = new VBox(5);
        barContainer.setPadding(new Insets(5));
        
        // 바
        HBox bar = new HBox(0); // 간격을 0으로 설정
        bar.setPrefWidth(TOTAL_BAR_WIDTH);
        bar.setSpacing(0); // 추가로 간격 명시적 제거
        
        // 전체 바를 하나의 큰 Rectangle로 생성
        Rectangle totalBar = new Rectangle(TOTAL_BAR_WIDTH, BAR_HEIGHT);
        totalBar.setFill(Color.LIGHTGRAY); // 기본 배경색
        totalBar.setStroke(Color.BLACK);
        totalBar.setStrokeWidth(1);
        
        // StackPane을 사용하여 여러 Rectangle을 겹쳐서 표시
        StackPane barStack = new StackPane();
        barStack.setPrefWidth(TOTAL_BAR_WIDTH);
        barStack.setPrefHeight(BAR_HEIGHT);
        barStack.getChildren().add(totalBar);
        
        // 몫만큼의 단위 바 (비율 계산) - 각 단위의 실제 비율로 계산
        double unitRatio = (double) result.getUnitLength() / result.getTotalLength();
        double unitWidth = unitRatio * TOTAL_BAR_WIDTH;
        
        // 단위 바들을 왼쪽부터 순서대로 배치
        double currentX = 0;
        for (int i = 0; i < result.getFullUnits(); i++) {
            Rectangle unitBar = new Rectangle(unitWidth, BAR_HEIGHT);
            unitBar.setFill(UNIT_COLOR);
            unitBar.setStroke(Color.BLACK);
            unitBar.setStrokeWidth(1);
            
            // 위치 설정
            unitBar.setTranslateX(currentX - TOTAL_BAR_WIDTH / 2 + unitWidth / 2);
            barStack.getChildren().add(unitBar);
            currentX += unitWidth;
        }
        
        // 나머지 바 (비율 계산) - 나머지가 0이면 표시하지 않음
        double remainderRatio = (double) result.getRemainingLength() / result.getTotalLength();
        double remainderWidth = remainderRatio * TOTAL_BAR_WIDTH;
        
        // Debug: Print calculated values
        System.out.println("=== Debug Information ===");
        System.out.println("Total Length: " + result.getTotalLength());
        System.out.println("Unit Length: " + result.getUnitLength());
        System.out.println("Quotient Count: " + result.getFullUnits());
        System.out.println("Remainder: " + result.getRemainingLength());
        System.out.println("Unit Ratio: " + unitRatio);
        System.out.println("Unit Width: " + unitWidth);
        System.out.println("Remainder Ratio: " + remainderRatio);
        System.out.println("Remainder Width: " + remainderWidth);
        System.out.println("Total Bar Width: " + TOTAL_BAR_WIDTH);
        System.out.println("========================");
        
        // 나머지가 0이 아닌 경우에만 나머지 바 표시
        if (result.getRemainingLength() > 0) {
            Rectangle remainderBar = new Rectangle(remainderWidth, BAR_HEIGHT);
            remainderBar.setFill(REMAINDER_COLOR);
            remainderBar.setStroke(Color.BLACK);
            remainderBar.setStrokeWidth(1);
            
            // 나머지 바 위치 설정
            remainderBar.setTranslateX(currentX - TOTAL_BAR_WIDTH / 2 + remainderWidth / 2);
            barStack.getChildren().add(remainderBar);
        }
        
        bar.getChildren().add(barStack);
        
        // 바 아래 라벨들 - 간격을 0으로 설정하여 정확한 위치 매칭
        HBox labelRow = new HBox(0); // 간격을 0으로 설정
        labelRow.setPrefWidth(TOTAL_BAR_WIDTH);
        labelRow.setSpacing(0); // 추가로 간격 명시적 제거
        
        // 단위 라벨 - 바 전체의 가장 왼쪽에 배치
        if (result.getFullUnits() > 0) {
            Label unitLabel = new Label(String.valueOf(result.getUnitLength()));
            unitLabel.setFont(Font.font("Arial", 14)); // 폰트 크기 증가
            unitLabel.setStyle("-fx-text-fill: #1976D2; -fx-font-weight: bold;");
            unitLabel.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            unitLabel.setPrefWidth(100); // 너비 증가
            labelRow.getChildren().add(unitLabel);
            
            // 중간 공간을 빈 라벨로 채움
            Label middleSpace = new Label("");
            middleSpace.setPrefWidth(TOTAL_BAR_WIDTH - 180); // 단위 라벨(100) + 나머지 라벨(80) 공간 제외
            labelRow.getChildren().add(middleSpace);
        } else {
            // 단위가 없는 경우 전체 공간을 빈 라벨로 채움
            Label middleSpace = new Label("");
            middleSpace.setPrefWidth(TOTAL_BAR_WIDTH - 80); // 나머지 라벨(80) 공간 제외
            labelRow.getChildren().add(middleSpace);
        }
        
        // 나머지 라벨 - 바 전체의 가장 오른쪽에 배치
        Label remainderLabel = new Label(String.valueOf(result.getRemainingLength()));
        remainderLabel.setFont(Font.font("Arial", 14)); // 폰트 크기 증가
        remainderLabel.setStyle("-fx-text-fill: #D32F2F; -fx-font-weight: bold;");
        remainderLabel.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        remainderLabel.setPrefWidth(80); // 고정 너비 설정
        labelRow.getChildren().add(remainderLabel);
        
        // 총 길이 라벨 (바의 오른쪽 끝)
        Label totalLabel = new Label(String.valueOf(result.getTotalLength()));
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        totalLabel.setStyle("-fx-text-fill: #2E7D32;");
        totalLabel.setPadding(new Insets(0, 0, 0, 10));
        
        HBox barAndTotal = new HBox(5);
        barAndTotal.getChildren().addAll(bar, totalLabel);
        
        barContainer.getChildren().addAll(barAndTotal, labelRow);
        
        // 길이 표시
        HBox lengthLabels = new HBox(10);
        lengthLabels.setPadding(new Insets(5));
        
        Label unitInfoLabel = new Label(String.format("단위: %d (%.1f%%)", result.getUnitLength(), unitRatio * 100));
        unitInfoLabel.setFont(Font.font("Arial", 14)); // 크게 표시
        unitInfoLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1976D2;"); // 강조 스타일
        
        Label countLabel = new Label(String.format("개수: %d개", result.getFullUnits()));
        countLabel.setFont(Font.font("Arial", 14)); // 개수 크게 표시
        countLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1976D2;"); // 강조 스타일
        
        Label remainderInfoLabel = new Label(String.format("나머지: %d (%.1f%%)", result.getRemainingLength(), 
            result.hasRemainder() ? (double) result.getRemainingLength() / result.getTotalLength() * 100 : 0));
        remainderInfoLabel.setFont(Font.font("Arial", 14)); // 크게 표시
        remainderInfoLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #D32F2F;"); // 강조 스타일
        
        lengthLabels.getChildren().addAll(unitInfoLabel, countLabel, remainderInfoLabel);
        
        stepBox.getChildren().addAll(stepTitle, formula, barContainer, lengthLabels);
        return stepBox;
    }
    
    private VBox createStep2Distribution(CalculationResult result) {
        VBox stepBox = new VBox(10);
        stepBox.setPadding(new Insets(10));
        stepBox.setStyle("-fx-border-color: #FF9800; -fx-border-radius: 5; -fx-background-color: #FFF3E0; -fx-border-width: 1;");
        
        // 단계 제목
        Label stepTitle = new Label("2단계: 나머지 분배");
        stepTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        stepTitle.setStyle("-fx-text-fill: #F57C00;");
        
        // 나머지가 0인 경우 1단계와 동일한 결과 표시
        if (result.getRemainingLength() == 0) {
            System.out.println("=== 2단계 나머지 0 처리 ===");
            System.out.println("Remaining Length: " + result.getRemainingLength());
            System.out.println("Full Units: " + result.getFullUnits());
            System.out.println("Unit Length: " + result.getUnitLength());
            System.out.println("========================");
            
            Label noRemainderMessage = new Label("나머지가 0이므로 1단계 결과와 동일합니다.");
            noRemainderMessage.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            noRemainderMessage.setStyle("-fx-text-fill: #4CAF50;");
            
            // 1단계와 동일한 바 표시
            VBox barContainer = new VBox(5);
            barContainer.setPadding(new Insets(5));
            
            HBox bar = new HBox(0);
            bar.setPrefWidth(TOTAL_BAR_WIDTH);
            bar.setSpacing(0);
            
            Rectangle totalBar = new Rectangle(TOTAL_BAR_WIDTH, BAR_HEIGHT);
            totalBar.setFill(Color.LIGHTGRAY);
            totalBar.setStroke(Color.BLACK);
            totalBar.setStrokeWidth(1);
            
            StackPane barStack = new StackPane();
            barStack.setPrefWidth(TOTAL_BAR_WIDTH);
            barStack.setPrefHeight(BAR_HEIGHT);
            barStack.getChildren().add(totalBar);
            
            // 단위 바들 표시 (1단계와 동일)
            double unitRatio = (double) result.getUnitLength() / result.getTotalLength();
            double unitWidth = unitRatio * TOTAL_BAR_WIDTH;
            
            double currentX = 0;
            for (int i = 0; i < result.getFullUnits(); i++) {
                Rectangle unitBar = new Rectangle(unitWidth, BAR_HEIGHT);
                unitBar.setFill(UNIT_COLOR);
                unitBar.setStroke(Color.BLACK);
                unitBar.setStrokeWidth(1);
                
                unitBar.setTranslateX(currentX - TOTAL_BAR_WIDTH / 2 + unitWidth / 2);
                barStack.getChildren().add(unitBar);
                currentX += unitWidth;
            }
            
            bar.getChildren().add(barStack);
            
            // 바 아래 라벨들
            HBox labelRow = new HBox(0);
            labelRow.setPrefWidth(TOTAL_BAR_WIDTH);
            labelRow.setSpacing(0);
            
            if (result.getFullUnits() > 0) {
                Label unitLabel = new Label(String.valueOf(result.getUnitLength()));
                unitLabel.setFont(Font.font("Arial", 14)); // 1단계와 동일한 크기
                unitLabel.setStyle("-fx-text-fill: #1976D2; -fx-font-weight: bold;");
                unitLabel.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                unitLabel.setPrefWidth(100); // 1단계와 동일한 너비
                labelRow.getChildren().add(unitLabel);
                
                Label middleSpace = new Label("");
                middleSpace.setPrefWidth(TOTAL_BAR_WIDTH - 180); // 1단계와 동일한 계산
                labelRow.getChildren().add(middleSpace);
            } else {
                Label middleSpace = new Label("");
                middleSpace.setPrefWidth(TOTAL_BAR_WIDTH - 80);
                labelRow.getChildren().add(middleSpace);
            }
            
            Label remainderLabel = new Label("0");
            remainderLabel.setFont(Font.font("Arial", 14)); // 1단계와 동일한 크기
            remainderLabel.setStyle("-fx-text-fill: #D32F2F; -fx-font-weight: bold;");
            remainderLabel.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
            remainderLabel.setPrefWidth(80);
            labelRow.getChildren().add(remainderLabel);
            
            Label totalLabel = new Label(String.valueOf(result.getTotalLength()));
            totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            totalLabel.setStyle("-fx-text-fill: #2E7D32;");
            totalLabel.setPadding(new Insets(0, 0, 0, 10));
            
            HBox barAndTotal = new HBox(5);
            barAndTotal.getChildren().addAll(bar, totalLabel);
            
            barContainer.getChildren().addAll(barAndTotal, labelRow);
            
            stepBox.getChildren().addAll(stepTitle, noRemainderMessage, barContainer);
            return stepBox;
        }
        
        // 분배 수식
        Label formula = new Label(String.format("%d ÷ %d = %.2f (각 단위당 추가)", 
            result.getRemainingLength(), result.getFullUnits(), result.getDistributedPerPiece()));
        formula.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        // 분배된 바와 라벨을 포함하는 컨테이너
        VBox barContainer = new VBox(5);
        barContainer.setPadding(new Insets(5));
        
        // 분배된 바
        HBox bar = new HBox(0); // 간격을 0으로 설정
        bar.setPrefWidth(TOTAL_BAR_WIDTH);
        bar.setSpacing(0); // 추가로 간격 명시적 제거
        
        // 전체 바를 하나의 큰 Rectangle로 생성
        Rectangle totalBar = new Rectangle(TOTAL_BAR_WIDTH, BAR_HEIGHT);
        totalBar.setFill(Color.LIGHTGRAY); // 기본 배경색
        totalBar.setStroke(Color.BLACK);
        totalBar.setStrokeWidth(1);
        
        // StackPane을 사용하여 여러 Rectangle을 겹쳐서 표시
        StackPane barStack = new StackPane();
        barStack.setPrefWidth(TOTAL_BAR_WIDTH);
        barStack.setPrefHeight(BAR_HEIGHT);
        barStack.getChildren().add(totalBar);
        
        // 분배된 길이로 각 단위 바 (비율 계산) - 최종 단위의 실제 비율로 계산
        double finalUnitRatio = result.getFinalPieceSize() / result.getTotalLength();
        double distributedWidth = finalUnitRatio * TOTAL_BAR_WIDTH;
        
        // 단위 바들을 왼쪽부터 순서대로 배치
        double currentX = 0;
        for (int i = 0; i < result.getFullUnits(); i++) {
            Rectangle distributedBar = new Rectangle(distributedWidth, BAR_HEIGHT);
            distributedBar.setFill(DISTRIBUTED_COLOR);
            distributedBar.setStroke(Color.BLACK);
            distributedBar.setStrokeWidth(1);
            
            // 위치 설정
            distributedBar.setTranslateX(currentX - TOTAL_BAR_WIDTH / 2 + distributedWidth / 2);
            barStack.getChildren().add(distributedBar);
            currentX += distributedWidth;
        }
        
        bar.getChildren().add(barStack);
        
        // 바 아래 라벨들 - 간격을 0으로 설정하여 정확한 위치 매칭
        HBox labelRow = new HBox(0); // 간격을 0으로 설정
        labelRow.setPrefWidth(TOTAL_BAR_WIDTH);
        labelRow.setSpacing(0); // 추가로 간격 명시적 제거
        
        // 최종 단위 라벨 - 바 전체의 가장 왼쪽에 배치
        if (result.getFullUnits() > 0) {
            Label finalUnitLabel = new Label(String.format("%.1f", result.getFinalPieceSize()));
            finalUnitLabel.setFont(Font.font("Arial", 14)); // 폰트 크기 증가
            finalUnitLabel.setStyle("-fx-text-fill: #2E7D32; -fx-font-weight: bold;");
            finalUnitLabel.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            finalUnitLabel.setPrefWidth(100); // 너비 증가
            labelRow.getChildren().add(finalUnitLabel);
            
            // 중간 공간을 빈 라벨로 채움
            Label middleSpace = new Label("");
            middleSpace.setPrefWidth(TOTAL_BAR_WIDTH - 100); // 단위 라벨(100) 공간 제외
            labelRow.getChildren().add(middleSpace);
        }
        
        // 총 길이 라벨 (바의 오른쪽 끝)
        Label totalLabel = new Label(String.valueOf(result.getTotalLength()));
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        totalLabel.setStyle("-fx-text-fill: #2E7D32;");
        totalLabel.setPadding(new Insets(0, 0, 0, 10));
        
        HBox barAndTotal = new HBox(5);
        barAndTotal.getChildren().addAll(bar, totalLabel);
        
        barContainer.getChildren().addAll(barAndTotal, labelRow);
        
        // 최종 정보
        HBox finalInfo = new HBox(10);
        finalInfo.setPadding(new Insets(5));
        
        Label originalLabel = new Label(String.format("원래: %d", result.getUnitLength()));
        originalLabel.setFont(Font.font("Arial", 14)); // 크게 표시
        originalLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1976D2;"); // 강조 스타일
        
        Label countLabel = new Label(String.format("개수: %d개", result.getFullUnits()));
        countLabel.setFont(Font.font("Arial", 14)); // 개수 크게 표시
        countLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E7D32;"); // 강조 스타일
        
        Label addedLabel = new Label(String.format("추가: %.2f", result.getDistributedPerPiece()));
        addedLabel.setFont(Font.font("Arial", 14)); // 크게 표시
        addedLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #FF9800;"); // 강조 스타일
        
        Label finalLabel = new Label(String.format("최종: %.2f (%.1f%%)", result.getFinalPieceSize(), finalUnitRatio * 100));
        finalLabel.setFont(Font.font("Arial", 14)); // 크게 표시
        finalLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #D32F2F;"); // 강조 스타일
        
        finalInfo.getChildren().addAll(originalLabel, countLabel, addedLabel, finalLabel);
        
        stepBox.getChildren().addAll(stepTitle, formula, barContainer, finalInfo);
        return stepBox;
    }
} 