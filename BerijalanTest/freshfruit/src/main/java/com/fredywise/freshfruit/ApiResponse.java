package com.fredywise.freshfruit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {
    @JsonProperty("OUT_STAT")
    private String outStat;

    @JsonProperty("OUT_MESS")
    private String outMess;

    @JsonProperty("OUT_DATA")
    private Buah[] outData;

    // Getters and Setters
    public String getOutStat() { return outStat; }
    public void setOutStat(String outStat) { this.outStat = outStat; }

    public String getOutMess() { return outMess; }
    public void setOutMess(String outMess) { this.outMess = outMess; }

    public Buah[] getOutData() { return outData; }
    public void setOutData(Buah[] outData) { this.outData = outData; }
}