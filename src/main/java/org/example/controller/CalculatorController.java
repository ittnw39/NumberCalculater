package org.example.controller;

import javafx.scene.control.TextField;
import org.example.model.CalculationResult;
import org.example.service.CalculationService;
import org.example.ui.ResultDisplayComponent;
import org.example.ui.NumberSelectionComponent;
import java.util.List;

public class CalculatorController {
    
    private final CalculationService calculationService;
    private final ResultDisplayComponent resultDisplay;
    private final NumberSelectionComponent numberSelection;
    private TextField inputField;
    
    public CalculatorController() {
        this.calculationService = new CalculationService();
        this.resultDisplay = new ResultDisplayComponent();
        this.numberSelection = new NumberSelectionComponent();
    }
    
    public void setInputField(TextField inputField) {
        this.inputField = inputField;
        // 입력 필드에 리스너 추가하여 입력 시 전체 길이 표시
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.trim().isEmpty()) {
                try {
                    int totalLength = Integer.parseInt(newValue.trim());
                    if (totalLength > 0) {
                        resultDisplay.displayTotalLength(totalLength);
                    }
                } catch (NumberFormatException e) {
                    // 숫자가 아닌 경우 무시
                }
            }
        });
    }
    
    public ResultDisplayComponent getResultDisplay() {
        return resultDisplay;
    }
    
    public NumberSelectionComponent getNumberSelection() {
        return numberSelection;
    }
    
    public void handleRemainderCalculation() {
        if (inputField == null) return;
        
        String input = inputField.getText();
        
        if (!calculationService.isValidInput(input)) {
            resultDisplay.showMessage("유효한 숫자를 입력하세요.");
            return;
        }
        
        List<Integer> selectedNumbers = numberSelection.getSelectedNumbers();
        
        if (!numberSelection.hasValidSelection()) {
            resultDisplay.showMessage("계산할 숫자를 하나 이상 선택해주세요.");
            return;
        }
        
        try {
            int num = Integer.parseInt(input);
            List<CalculationResult> results = calculationService.calculateRemainders(num, selectedNumbers);
            resultDisplay.displayRemainderResults(results);
        } catch (Exception e) {
            resultDisplay.showMessage("계산 중 오류가 발생했습니다.");
        }
    }
    
    public void handleLengthDivisionCalculation() {
        if (inputField == null) return;
        
        String input = inputField.getText();
        
        if (!calculationService.isValidInput(input)) {
            resultDisplay.showMessage("유효한 숫자를 입력하세요.");
            return;
        }
        
        List<Integer> selectedNumbers = numberSelection.getSelectedNumbers();
        
        if (!numberSelection.hasValidSelection()) {
            resultDisplay.showMessage("계산할 숫자를 하나 이상 선택해주세요.");
            return;
        }
        
        try {
            int num = Integer.parseInt(input);
            List<CalculationResult> results = calculationService.calculateRemainders(num, selectedNumbers);
            resultDisplay.displayLengthDivisionResults(results);
        } catch (Exception e) {
            resultDisplay.showMessage("계산 중 오류가 발생했습니다.");
        }
    }
    
    public void handleDistributionCalculation() {
        if (inputField == null) return;
        
        String input = inputField.getText();
        
        if (!calculationService.isValidInput(input)) {
            resultDisplay.showMessage("유효한 숫자를 입력하세요.");
            return;
        }
        
        List<Integer> selectedNumbers = numberSelection.getSelectedNumbers();
        
        if (!numberSelection.hasValidSelection()) {
            resultDisplay.showMessage("계산할 숫자를 하나 이상 선택해주세요.");
            return;
        }
        
        try {
            int num = Integer.parseInt(input);
            List<CalculationResult> results = calculationService.calculateRemainders(num, selectedNumbers);
            resultDisplay.displayDistributionResults(results);
        } catch (Exception e) {
            resultDisplay.showMessage("계산 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 개수 지정 분배 계산 처리
     */
    public void handleCustomDistributionCalculation() {
        if (inputField == null) return;
        
        String input = inputField.getText();
        
        if (!calculationService.isValidInput(input)) {
            resultDisplay.showMessage("유효한 숫자를 입력하세요.");
            return;
        }
        
        List<Integer> selectedNumbers = numberSelection.getSelectedNumbers();
        
        if (!numberSelection.hasValidSelection()) {
            resultDisplay.showMessage("계산할 숫자를 하나 이상 선택해주세요.");
            return;
        }
        
        if (!numberSelection.hasValidDistributionCount()) {
            numberSelection.highlightDistributionCountFieldError();
            resultDisplay.showMessage("유효한 분배 개수를 입력해주세요.");
            return;
        }
        
        // 유효한 경우 에러 스타일 제거
        numberSelection.clearDistributionCountFieldError();
        
        try {
            int num = Integer.parseInt(input);
            int distributionCount = numberSelection.getDistributionCountValue();
            
            // 각 단위별로 검증하여 가능한 것과 불가능한 것을 분리
            List<CalculationResult> validResults = new java.util.ArrayList<>();
            List<int[]> errorUnits = new java.util.ArrayList<>();
            
            for (int divisor : selectedNumbers) {
                int quotient = num / divisor;
                if (distributionCount > quotient) {
                    // 계산 불가능한 단위 정보 저장
                    errorUnits.add(new int[]{num, divisor});
                } else {
                    // 계산 가능한 단위는 계산 수행
                    CalculationResult result = new CalculationResult(num, divisor);
                    result.calculateCustomDistribution(distributionCount);
                    validResults.add(result);
                }
            }
            
            // 결과 표시 (에러 포함)
            if (validResults.isEmpty() && errorUnits.isEmpty()) {
                resultDisplay.showMessage("계산할 숫자를 하나 이상 선택해주세요.");
            } else {
                resultDisplay.displayCustomDistributionResultsWithErrors(validResults, errorUnits);
            }
        } catch (Exception e) {
            resultDisplay.showMessage("계산 중 오류가 발생했습니다.");
        }
    }
} 