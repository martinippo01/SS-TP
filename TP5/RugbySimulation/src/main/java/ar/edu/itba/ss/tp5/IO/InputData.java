package ar.edu.itba.ss.tp5.IO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class InputData {

    private final InputFile inputFile;

    public InputData(String inputFileName) {
        try (JsonReader fileReader = new JsonReader(new FileReader(inputFileName))) {
            Gson gson = new GsonBuilder()
                    .create();
            this.inputFile = gson.fromJson(fileReader, InputFile.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Input file not found");
        }
    }

    public static class InputFile {
        // Listado de parametros de entrada
        private boolean pretty;
        private double field_w;
        private double field_h;
        private int N_j;
        private double v_azul_max;
        private double v_rojo_max;
        private double tau_azu;
        private double tau_rojo;
        private double radios_min_azul;
        private double radios_max_azul;
        private double radios_min_rojo;
        private double radios_max_rojo;
        private int repetitions;
        private double min_distance_to_red;
        private double v_azul_escape;
        private double v_rojo_escape;
        private String output_dir;
        // Heuristica
        private double Ap;
        private double Bp;
        private double visibilityAngle;
        private double beta;
        private boolean forAnimation;



        // List of DynamicFields for the dynamic array
        @SerializedName("dynamic")
        private List<DynamicField> dynamic;

        public boolean isPretty() {
            return pretty;
        }

        public String getOutputDir(){
            return output_dir;
        }

        public double getFieldW() {
            return field_w;
        }

        public double getFieldH() {
            return field_h;
        }

        public int getNj() {
            return N_j;
        }

        public double getV_azul_max() {
            return v_azul_max;
        }

        public double getV_rojo_max() {
            return v_rojo_max;
        }

        public double getTau_azu() {
            return tau_azu;
        }

        public double getTau_rojo() {
            return tau_rojo;
        }

        public double getRadios_min_azul() {
            return radios_min_azul;
        }

        public double getRadios_max_azul() {
            return radios_max_azul;
        }

        public double getRadios_min_rojo() {
            return radios_min_rojo;
        }

        public double getRadios_max_rojo() {
            return radios_max_rojo;
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

        public double getV_azul_escape(){
            return v_azul_escape;
        }

        public double getV_rojo_escape(){
            return v_rojo_escape;
        }

        public double getBeta(){
            return beta;
        }

        public boolean getForAnimation() { return forAnimation; }

        public List<DynamicField> getDynamic() {
            return dynamic;
        }

        // Nested class for dynamic objects
        public static class DynamicField {
            // Listado de parametros sujetos a modifcarse en multiples corridas
            private String id;
            private Integer Nj;
            private Double v_azul_max;
            private Double v_rojo_max;
            private Double tau_azu;
            private Double tau_rojo;
            private Double radios_min_azul;
            private Double radios_max_azul;
            private Double radios_min_rojo;
            private Double radios_max_rojo;
            private Integer repetitions;
            private Double min_distance_to_red;
            private Double v_azul_escape;
            private Double v_rojo_escape;
            // Heuristica
            private Double Ap;
            private Double Bp;
            private Double visibilityAngle;
            private Double beta;
            private Boolean forAnimation;

            // Getters

            public String getId() {
                return id;
            }

            public Integer getNj() {
                return Nj;
            }

            public Double getV_azul_max() {
                return v_azul_max;
            }

            public Double getV_rojo_max() {
                return v_rojo_max;
            }

            public Double getTau_azu() {
                return tau_azu;
            }

            public Double getTau_rojo() {
                return tau_rojo;
            }

            public Double getRadios_min_azul() {
                return radios_min_azul;
            }

            public Double getRadios_max_azul() {
                return radios_max_azul;
            }

            public Double getRadios_min_rojo() {
                return radios_min_rojo;
            }

            public Double getRadios_max_rojo() {
                return radios_max_rojo;
            }

            public Integer getRepetitions() {
                return repetitions;
            }

            public Double getMin_distance_to_red() {
                return min_distance_to_red;
            }

            public Double getVAzulEscape() {
                return v_azul_escape;
            }

            public Double getVRojoEscape() {
                return v_rojo_escape;
            }

            public Double getAp() {
                return Ap;
            }

            public Double getBp() {
                return Bp;
            }

            public Double getVisibilityAngle() {
                return visibilityAngle;
            }

            public Double getBeta() {
                return beta;
            }

            public Boolean getForAnimation() { return forAnimation; }
        }
    }

    // Getters
    public boolean getPretty(){
        return inputFile.pretty;
    }

    public String getOutputDir(){
        return inputFile.output_dir;
    }

    public double getFieldW() {
        return inputFile.field_w;
    }

    public double getFieldH() {
        return inputFile.field_h;
    }

    public int getNj() {
        return inputFile.N_j;
    }

    public double getV_azul_max() {
        return inputFile.v_azul_max;
    }

    public double getV_rojo_max() {
        return inputFile.v_rojo_max;
    }

    public double getTau_azu() {
        return inputFile.tau_azu;
    }

    public double getTau_rojo() {
        return inputFile.tau_rojo;
    }

    public double getRadios_min_azul() {
        return inputFile.radios_min_azul;
    }

    public double getRadios_max_azul() {
        return inputFile.radios_max_azul;
    }

    public double getRadios_min_rojo() {
        return inputFile.radios_min_rojo;
    }

    public double getRadios_max_rojo() {
        return inputFile.radios_max_rojo;
    }

    public int getRepetitions() {
        return inputFile.repetitions;
    }

    public double getMin_distance_to_red() {
        return inputFile.min_distance_to_red;
    }

    public double getAp() {
        return inputFile.Ap;
    }

    public double getBp() {
        return inputFile.Bp;
    }

    public double getVisibilityAngle() {
        return inputFile.visibilityAngle;
    }

    public List<InputFile.DynamicField> getDynamic() {
        return inputFile.dynamic;
    }

    public double getVAzulEscape(){
        return inputFile.v_azul_escape;
    }

    public double getVRojoEscape(){
        return inputFile.v_rojo_escape;
    }

    public double getBeta(){
        return inputFile.beta;
    }

    public boolean getForAnimation() { return inputFile.forAnimation;}
}


