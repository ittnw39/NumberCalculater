package org.example.service;

import org.example.model.CalculationResult;
import java.util.List;
import java.util.ArrayList;

public class CalculationService {
    
    private static final int[] DEFAULT_DIVISORS = {1400, 1500, 1600, 1700, 1800};
    
    /**
     * 선택된 숫자들로 나머지 계산
     */
    public List<CalculationResult> calculateRemainders(int dividend, List<Integer> selectedDivisors) {
        List<CalculationResult> results = new ArrayList<>();
        
        for (int divisor : selectedDivisors) {
            results.add(new CalculationResult(dividend, divisor));
        }
        
        return results;
    }
    
    /**
     * 개수 지정 분배 계산
     */
    public List<CalculationResult> calculateCustomDistribution(int dividend, List<Integer> selectedDivisors, int distributionCount) {
        List<CalculationResult> results = new ArrayList<>();
        
        for (int divisor : selectedDivisors) {
            CalculationResult result = new CalculationResult(dividend, divisor);
            result.calculateCustomDistribution(distributionCount);
            results.add(result);
        }
        
        return results;
    }
    
    /**
     * 사용자 지정 숫자로 나누기
     */
    public CalculationResult calculateCustomDivision(int dividend, int divisor) {
        return new CalculationResult(dividend, divisor);
    }
    
    /**
     * 기본 제수 목록 반환
     */
    public int[] getDefaultDivisors() {
        return DEFAULT_DIVISORS.clone();
    }
    
    /**
     * 입력값 검증
     */
    public boolean isValidInput(String input) {
        try {
            int num = Integer.parseInt(input);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 분배 개수 검증
     */
    public boolean isValidDistributionCount(String input, int maxCount) {
        try {
            int count = Integer.parseInt(input);
            return count > 0 && count <= maxCount;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 선택된 제수 목록 검증
     */
    public boolean hasValidSelection(List<Integer> selectedDivisors) {
        return selectedDivisors != null && !selectedDivisors.isEmpty();
    }
} 