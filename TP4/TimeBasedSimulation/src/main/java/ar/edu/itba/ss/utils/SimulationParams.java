package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.AlgorithmType;
import ar.edu.itba.ss.SimulationType;

import java.util.Optional;

public class SimulationParams {

    private final Integer n;
    private final Double mass;
    private final Double k;
    private final Double gamma;
    private final Double tf;
    private final Double r0;
    private final Double v0;
    private final Double dt;
    private final Integer dtJumps;
    private final AlgorithmType algorithmType;
    private final SimulationType simulationType;
    private final Double a;

    public SimulationParams(InputData inputData, InputData.InputFile.DynamicField dynamicFields) {
        this.n = inputData.getN();
        this.mass = inputData.getMass();
        this.k = Optional.ofNullable(dynamicFields.getK()).orElse(inputData.getK());
        this.gamma = inputData.getGamma();
        this.tf = Optional.ofNullable(dynamicFields.getTf()).orElse(inputData.getTf());
        this.r0 = Optional.ofNullable(dynamicFields.getR0()).orElse(inputData.getR0());
        this.v0 = Optional.ofNullable(dynamicFields.getV0()).orElse(inputData.getV0());
        this.dt = Optional.ofNullable(dynamicFields.getDt()).orElse(inputData.dt());
        this.dtJumps = Optional.ofNullable(dynamicFields.getDt_jumps()).orElse(inputData.getDtJumps());
        this.algorithmType = Optional.ofNullable(dynamicFields.getAlgorithmType()).orElse(inputData.getAlgorithmType());
        this.simulationType = inputData.getSimulationType();
        this.a = inputData.getA();
    }

    public Integer getN() {
        return n;
    }

    public Double getK() {
        return k;
    }

    public Double getGamma() {
        return gamma;
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

    public Integer getDtJumps() {
        return dtJumps;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public SimulationType getSimulationType() {
        return simulationType;
    }

    public Double getA() {
        return a;
    }

    public double getMass() { return mass;
    }
}
