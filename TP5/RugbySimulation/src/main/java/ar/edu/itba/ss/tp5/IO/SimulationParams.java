package ar.edu.itba.ss.tp5.IO;

import java.util.Optional;

public class SimulationParams {
    private final String id;
    private final double fieldW;
    private final double fieldH;
    private final int Nj;
    private final double vAzulMax;
    private final double vRojoMax;
    private final double tauAzul;
    private final double tauRojo;
    private final double radiosMinAzul;
    private final double radiosMaxAzul;
    private final double radiosMinRojo;
    private final double radiosMaxRojo;
    private final int repetitions;
    private final double min_distance_to_red;
    private double vAzulEscape;
    private double vRojoEscape;
    // Heuristica
    private final double Ap;
    private final double Bp;
    private final double visibilityAngle;
    private final double beta;
    private final boolean forAnimation;

    public SimulationParams(String id, InputData inputData, InputData.InputFile.DynamicField dynamicFields) {
        this.id = id;
        this.fieldW = inputData.getFieldW();
        this.fieldH = inputData.getFieldH();
        this.Nj = Optional.ofNullable(dynamicFields.getNj()).orElse(inputData.getNj());
        this.vAzulMax = Optional.ofNullable(dynamicFields.getV_azul_max()).orElse(inputData.getV_azul_max());
        this.vRojoMax = Optional.ofNullable(dynamicFields.getV_rojo_max()).orElse(inputData.getV_rojo_max());
        this.tauAzul = Optional.ofNullable(dynamicFields.getTau_azu()).orElse(inputData.getTau_azu());
        this.tauRojo = Optional.ofNullable(dynamicFields.getTau_rojo()).orElse(inputData.getTau_rojo());
        this.radiosMinAzul = Optional.ofNullable(dynamicFields.getRadios_min_azul()).orElse(inputData.getRadios_min_azul());
        this.radiosMaxAzul = Optional.ofNullable(dynamicFields.getRadios_max_azul()).orElse(inputData.getRadios_max_azul());
        this.radiosMinRojo = Optional.ofNullable(dynamicFields.getRadios_min_rojo()).orElse(inputData.getRadios_min_rojo());
        this.radiosMaxRojo = Optional.ofNullable(dynamicFields.getRadios_max_rojo()).orElse(inputData.getRadios_max_rojo());
        this.repetitions = Optional.ofNullable(dynamicFields.getRepetitions()).orElse(inputData.getRepetitions());
        this.min_distance_to_red = Optional.ofNullable(dynamicFields.getMin_distance_to_red()).orElse(inputData.getMin_distance_to_red());
        this.vAzulEscape = Optional.ofNullable(dynamicFields.getVAzulEscape()).orElse(inputData.getVAzulEscape());
        this.vRojoEscape = Optional.ofNullable(dynamicFields.getVRojoEscape()).orElse(inputData.getVRojoEscape());
        // Heuristica
        this.Ap = Optional.ofNullable(dynamicFields.getAp()).orElse(inputData.getAp());
        this.Bp = Optional.ofNullable(dynamicFields.getBp()).orElse(inputData.getBp());
        this.visibilityAngle = Optional.ofNullable(dynamicFields.getVisibilityAngle()).orElse(inputData.getVisibilityAngle());
        this.beta = Optional.ofNullable(dynamicFields.getBeta()).orElse(inputData.getBeta());
        this.forAnimation = Optional.ofNullable(dynamicFields.getForAnimation()).orElse(inputData.getForAnimation());
    }

    //getters
    public double getvAzulEscape(){
        return vAzulEscape;
    }

    public double getvRojoEscape(){
        return vRojoEscape;
    }

    public double getBeta(){
        return beta;
    }

    public String getId() {
        return id;
    }

    public double getFieldW() {
        return fieldW;
    }

    public double getFieldH() {
        return fieldH;
    }

    public int getNj() {
        return Nj;
    }

    public double getvAzulMax() {
        return vAzulMax;
    }

    public double getvRojoMax() {
        return vRojoMax;
    }

    public double getTauAzul() {
        return tauAzul;
    }

    public double getTauRojo() {
        return tauRojo;
    }

    public double getRadiosMinAzul() {
        return radiosMinAzul;
    }

    public double getRadiosMaxAzul() {
        return radiosMaxAzul;
    }

    public double getRadiosMinRojo() {
        return radiosMinRojo;
    }

    public double getRadiosMaxRojo() {
        return radiosMaxRojo;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public double getMin_distance_to_red() {
        return min_distance_to_red;
    }

    public double getAp() {
        return Ap;
    }

    public double getBp() {
        return Bp;
    }

    public double getVisibilityAngle() {
        return visibilityAngle;
    }

    public boolean getForAnimation() {return forAnimation;}
}
