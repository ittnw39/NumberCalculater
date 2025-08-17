package org.example.model;

public class CalculationResult {
    private int dividend;
    private int divisor;
    private int quotient;
    private int remainder;
    private double distributedPerPiece;
    private double finalPieceSize;
    
    public CalculationResult(int dividend, int divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
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
    
    // Getters
    public int getDividend() { return dividend; }
    public int getDivisor() { return divisor; }
    public int getQuotient() { return quotient; }
    public int getRemainder() { return remainder; }
    public double getDistributedPerPiece() { return distributedPerPiece; }
    public double getFinalPieceSize() { return finalPieceSize; }
    public boolean hasRemainder() { return remainder != 0; }
    
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