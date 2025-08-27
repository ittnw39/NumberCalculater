package org.example.model;

public class CalculationResult {
    private int dividend;
    private int divisor;
    private int quotient;
    private int remainder;
    private double distributedPerPiece;
    private double finalPieceSize;
    
    // 개수 지정 분배를 위한 새로운 필드들
    private int customDistributionCount;
    private double customDistributedPerPiece;
    private double customFinalPieceSize;
    
    public CalculationResult(int dividend, int divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
        this.customDistributionCount = 0; // 기본값
        calculate();
    }
    
    private void calculate() {
        this.quotient = dividend / divisor;
        this.remainder = dividend % divisor;
        
        if (remainder != 0 && quotient != 0) {
            this.distributedPerPiece = (double) remainder / quotient;
            this.finalPieceSize = divisor + distributedPerPiece;
        } else {
            this.distributedPerPiece = 0;
            this.finalPieceSize = divisor;
        }
    }
    
    /**
     * 개수 지정 분배 계산
     */
    public void calculateCustomDistribution(int distributionCount) {
        this.customDistributionCount = distributionCount;
        
        if (distributionCount > 0 && distributionCount <= quotient) {
            // 지정된 개수만큼 분배
            this.customDistributedPerPiece = (double) remainder / distributionCount;
            this.customFinalPieceSize = divisor + customDistributedPerPiece;
        } else {
            // 유효하지 않은 개수인 경우 기본값으로 설정
            this.customDistributedPerPiece = 0;
            this.customFinalPieceSize = divisor;
        }
    }
    
    // Getters
    public int getDividend() { return dividend; }
    public int getDivisor() { return divisor; }
    public int getQuotient() { return quotient; }
    public int getRemainder() { return remainder; }
    public double getDistributedPerPiece() { return distributedPerPiece; }
    public double getFinalPieceSize() { return finalPieceSize; }
    public boolean hasRemainder() { return remainder != 0; }
    
    // 개수 지정 분배를 위한 새로운 getters
    public int getCustomDistributionCount() { return customDistributionCount; }
    public double getCustomDistributedPerPiece() { return customDistributedPerPiece; }
    public double getCustomFinalPieceSize() { return customFinalPieceSize; }
    public boolean hasCustomDistribution() { return customDistributionCount > 0; }
    
    // 길이 시각화를 위한 메서드들
    public int getTotalLength() { return dividend; }
    public int getUnitLength() { return divisor; }
    public int getFullUnits() { return quotient; }
    public int getRemainingLength() { return remainder; }
    
    @Override
    public String toString() {
        return String.format("%d ÷ %d = %d...%d", dividend, divisor, quotient, remainder);
    }
} 