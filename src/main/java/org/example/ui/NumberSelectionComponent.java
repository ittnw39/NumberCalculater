package org.example.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import org.example.service.CalculationService;
import java.util.List;
import java.util.ArrayList;

public class NumberSelectionComponent {
    
    private final CheckBox[] numberCheckBoxes;
    private final RadioButton customUnitRadio;
    private final TextField customUnitField;
    private final ToggleGroup unitToggleGroup;
    private final CalculationService calculationService;
    
    // 분배 개수 입력 필드 추가
    private final TextField distributionCountField;
    private final Label distributionCountLabel;
    
    public NumberSelectionComponent() {
        this.calculationService = new CalculationService();
        this.numberCheckBoxes = createCheckBoxes();
        this.unitToggleGroup = new ToggleGroup();
        this.customUnitRadio = new RadioButton("사용자 지정 단위");
        this.customUnitRadio.setFont(Font.font("Malgun Gothic", 14)); // 폰트 설정
        this.customUnitField = new TextField();
        this.customUnitField.setPromptText("단위 입력");
        this.customUnitField.setFont(Font.font("Malgun Gothic", 14)); // 폰트 설정
        this.customUnitField.setDisable(true);
        
        // 분배 개수 입력 필드 초기화
        this.distributionCountField = new TextField();
        this.distributionCountField.setPromptText("분배할 개수 입력");
        this.distributionCountField.setFont(Font.font("Malgun Gothic", 14));
        this.distributionCountField.setPrefWidth(120);
        
        this.distributionCountLabel = new Label("분배 개수:");
        this.distributionCountLabel.setFont(Font.font("Malgun Gothic", FontWeight.BOLD, 14));
        
        setupCustomUnitControls();
    }
    
    private CheckBox[] createCheckBoxes() {
        int[] divisors = calculationService.getDefaultDivisors();
        CheckBox[] checkBoxes = new CheckBox[divisors.length];
        
        for (int i = 0; i < divisors.length; i++) {
            checkBoxes[i] = new CheckBox(String.valueOf(divisors[i]));
            checkBoxes[i].setSelected(false); // 기본 해제
            checkBoxes[i].setMinWidth(60); // 최소 너비 증가
            checkBoxes[i].setPrefWidth(70); // 선호 너비 증가
            checkBoxes[i].setMaxWidth(Double.MAX_VALUE); // 최대 너비 제한 해제
            checkBoxes[i].setFont(Font.font("Malgun Gothic", 14)); // 폰트 설정
        }
        
        return checkBoxes;
    }
    
    private void setupCustomUnitControls() {
        customUnitRadio.setToggleGroup(unitToggleGroup);
        
        // 사용자 지정 라디오 버튼 선택 시 입력 필드 활성화
        customUnitRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            customUnitField.setDisable(!newValue);
            if (newValue) {
                customUnitField.requestFocus();
            }
        });
        
        // 기본 단위 체크박스들과 사용자 지정 라디오 버튼을 독립적으로 동작하도록 설정
        // 상호 배타적 제한 제거 - 동시 선택 가능
        for (CheckBox cb : numberCheckBoxes) {
            cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
                // 기본 단위 선택 시 사용자 지정 라디오 버튼 해제하지 않음
                // 동시 선택 허용
            });
        }
        
        // 사용자 지정 라디오 버튼 선택 시에도 기본 단위들 해제하지 않음
        customUnitRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // 사용자 지정 라디오 버튼 선택 시에도 기본 단위들 유지
            // 동시 선택 허용
        });
    }
    
    public VBox createSelectionPanel() {
        VBox panel = new VBox(15); // 간격 증가
        panel.setPadding(new Insets(15)); // 패딩 증가
        
        // 제목
        Label selectLabel = new Label("분할할 단위를 선택하세요:");
        selectLabel.setFont(Font.font("Malgun Gothic", FontWeight.BOLD, 14));
        
        // 기본 단위 체크박스 그리드
        GridPane checkboxGrid = new GridPane();
        checkboxGrid.setHgap(20); // 체크박스 세트 간 간격 증가
        checkboxGrid.setVgap(20); // 간격 증가
        checkboxGrid.setMinWidth(200); // 최소 너비 줄임
        checkboxGrid.setPrefWidth(320); // 선호 너비 조정 (간격 증가로 인해)
        checkboxGrid.setMaxWidth(Double.MAX_VALUE); // 최대 너비 제한 해제
        
        for (int i = 0; i < numberCheckBoxes.length; i++) {
            checkboxGrid.add(numberCheckBoxes[i], i, 0);
        }
        
        // 사용자 지정 단위 영역
        VBox customUnitBox = new VBox(8); // 간격 증가
        customUnitBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 3; -fx-padding: 12;"); // 패딩 증가
        
        Label customLabel = new Label("또는 사용자 지정 단위:");
        customLabel.setFont(Font.font("Malgun Gothic", FontWeight.BOLD, 14));
        
        HBox customInputBox = new HBox(8); // 간격 증가
        customInputBox.getChildren().addAll(customUnitRadio, customUnitField);
        
        customUnitBox.getChildren().addAll(customLabel, customInputBox);
        
        // 분배 개수 입력 영역 추가
        VBox distributionCountBox = new VBox(8);
        distributionCountBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 3; -fx-padding: 12;");
        
        HBox distributionInputBox = new HBox(8);
        distributionInputBox.getChildren().addAll(distributionCountLabel, distributionCountField);
        
        distributionCountBox.getChildren().addAll(distributionInputBox);
        
        // 버튼들
        HBox buttonBox = createButtonBox();
        
        panel.getChildren().addAll(selectLabel, checkboxGrid, customUnitBox, distributionCountBox, buttonBox);
        return panel;
    }
    
    private HBox createButtonBox() {
        Button selectAllBtn = new Button("전체 선택");
        selectAllBtn.setFont(Font.font("Malgun Gothic", FontWeight.BOLD, 14));
        Button deselectAllBtn = new Button("전체 해제");
        deselectAllBtn.setFont(Font.font("Malgun Gothic", FontWeight.BOLD, 14));
        Button resetBtn = new Button("초기화");
        resetBtn.setFont(Font.font("Malgun Gothic", FontWeight.BOLD, 14));
        
        selectAllBtn.setOnAction(e -> selectAll());
        deselectAllBtn.setOnAction(e -> deselectAll());
        resetBtn.setOnAction(e -> reset());
        
        return new HBox(10, selectAllBtn, deselectAllBtn, resetBtn);
    }
    
    public void selectAll() {
        for (CheckBox cb : numberCheckBoxes) {
            cb.setSelected(true);
        }
        customUnitRadio.setSelected(false);
    }
    
    public void deselectAll() {
        for (CheckBox cb : numberCheckBoxes) {
            cb.setSelected(false);
        }
        customUnitRadio.setSelected(false);
    }
    
    public void reset() {
        deselectAll();
        customUnitField.clear();
        distributionCountField.clear(); // 분배 개수 필드도 초기화
    }
    
    public List<Integer> getSelectedNumbers() {
        List<Integer> selected = new ArrayList<>();
        int[] numbers = calculationService.getDefaultDivisors();
        
        // 기본 단위들 추가
        for (int i = 0; i < numberCheckBoxes.length; i++) {
            if (numberCheckBoxes[i].isSelected()) {
                selected.add(numbers[i]);
            }
        }
        
        // 사용자 지정 단위 추가
        if (customUnitRadio.isSelected() && !customUnitField.getText().trim().isEmpty()) {
            try {
                int customValue = Integer.parseInt(customUnitField.getText().trim());
                if (customValue > 0) {
                    selected.add(customValue);
                }
            } catch (NumberFormatException e) {
                // 잘못된 입력은 무시
            }
        }
        
        return selected;
    }
    
    public boolean hasValidSelection() {
        // 기본 단위가 선택되었거나 사용자 지정 단위가 유효한 경우
        boolean hasDefaultSelection = false;
        for (CheckBox cb : numberCheckBoxes) {
            if (cb.isSelected()) {
                hasDefaultSelection = true;
                break;
            }
        }
        
        boolean hasCustomSelection = false;
        if (customUnitRadio.isSelected() && !customUnitField.getText().trim().isEmpty()) {
            try {
                int customValue = Integer.parseInt(customUnitField.getText().trim());
                hasCustomSelection = customValue > 0;
            } catch (NumberFormatException e) {
                hasCustomSelection = false;
            }
        }
        
        return hasDefaultSelection || hasCustomSelection;
    }
    
    public String getCustomUnitValue() {
        if (customUnitRadio.isSelected()) {
            return customUnitField.getText().trim();
        }
        return "";
    }
    
    /**
     * 분배 개수 가져오기
     */
    public String getDistributionCount() {
        return distributionCountField.getText().trim();
    }
    
    /**
     * 분배 개수가 유효한지 확인
     */
    public boolean hasValidDistributionCount() {
        String countText = getDistributionCount();
        if (countText.isEmpty()) {
            return false;
        }
        
        try {
            int count = Integer.parseInt(countText);
            return count > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 분배 개수 값을 정수로 반환
     */
    public int getDistributionCountValue() {
        try {
            return Integer.parseInt(getDistributionCount());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
} 