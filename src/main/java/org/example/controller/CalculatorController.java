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
    

} 