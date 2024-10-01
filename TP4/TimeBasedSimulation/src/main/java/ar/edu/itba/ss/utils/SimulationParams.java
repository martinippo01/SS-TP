package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.AlgorithmType;

public class SimulationParams {
    private Double k;
    private Double gamma;
    private Double m;
    private Double tf;
    private Double r0;
    private Double v0;
    private Double dt;
    private Integer dt_jumps;
    private Integer n;
    private AlgorithmType algorithmType;
    private Double a;
    private Double timeMax;

    public SimulationParams(Double k, Double gamma, Double m, Double tf, Double r0, Double v0, Double dt, Integer dt_jumps, Integer n, AlgorithmType algorithmType, Double a, Double timeMax) {
        this.k = k;
        this.gamma = gamma;
        this.m = m;
        this.tf = tf;
        this.r0 = r0;
        this.v0 = v0;
        this.dt = dt;
        this.dt_jumps = dt_jumps;
        this.n = n;
        this.algorithmType = algorithmType;
        this.a = a;
        this.timeMax = timeMax;
    }

    public SimulationParams(InputData inputData) {
        this(
                inputData.getK(),
                inputData.getGamma(),
                inputData.getM(),
                inputData.getTf(),
                inputData.getR0(),
                inputData.getV0(),
                inputData.dt(),
                inputData.getDtJumps(),
                inputData.getN(),
                inputData.getAlgorithmType(),
                inputData.getA(),
                inputData.getTimeMax()
        );
    }

    public SimulationParams getCopy() {
        return new SimulationParams(k, gamma, m, tf, r0, v0, dt, dt_jumps, n, algorithmType, a, timeMax);
    }

    public void setTf(Double tf) {
        this.tf = tf;
    }

    public void setK(Double k) {
        this.k = k;
    }

    public void setR0(Double r0) {
        this.r0 = r0;
    }

    public void setV0(Double v0) {
        this.v0 = v0;
    }

    public void setDt(Double dt) {
        this.dt = dt;
    }

    public void setDt_jumps(Integer dt_jumps) {
        this.dt_jumps = dt_jumps;
    }

    public Double getK() {
        return k;
    }

    public Double getGamma() {
        return gamma;
    }

    public Double getMass() {
        return m;
    }

    public Double getTf() {
        return tf;
    }

    public Double getR0() {
        return r0;
    }

    public Double getV0() {
        return v0;
    }

    public Double getDt() {
        return dt;
    }

    public Integer getDt_jumps() {
        return dt_jumps;
    }

    public Integer getN() {
        return n;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public Double getTimeMax() {
        return timeMax;
    }

    public Double getA() {
        return a;
    }
}
