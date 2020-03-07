package com.praxello.smartdoctor.model.allinstruction;

public class InstructionData {
    private String instruction;
    private int instructionId;
    private int isActive;
    private String hindi;
    private String marathi;

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(int instructionId) {
        this.instructionId = instructionId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getHindi() {
        return hindi;
    }

    public void setHindi(String hindi) {
        this.hindi = hindi;
    }

    public String getMarathi() {
        return marathi;
    }

    public void setMarathi(String marathi) {
        this.marathi = marathi;
    }
}
