package io.thoughtworksarts.riot.facialrecognition;

import io.thoughtworksarts.riot.logger.PerceptionLogger;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;
import java.util.logging.Level;

public class DeepLearningProcessor {

    private final String modelConfigFile;
    private MultiLayerNetwork model;
    private PerceptionLogger logger;
    public DeepLearningProcessor(String modelConfigFile) {
        this.modelConfigFile = modelConfigFile;
        this.logger = new PerceptionLogger("DeepLearningProcessor");

        initModel();
    }

    private void initModel() {
        try {
            model = KerasModelImport.importKerasSequentialModelAndWeights(modelConfigFile);
        } catch (IOException e) {
            logger.log(Level.INFO, "initModel", e.getMessage(), null);
            e.printStackTrace();
        } catch (InvalidKerasConfigurationException e) {
            logger.log(Level.INFO, "initModel", e.getMessage(), null);
            e.printStackTrace();
        } catch (UnsupportedKerasConfigurationException e) {
            logger.log(Level.INFO, "initModel", e.getMessage(), null);
            e.printStackTrace();
        }
    }

    public float[] getEmotionPrediction(INDArray imageData) {
        return Nd4j.toFlattened(model.output(imageData)).toFloatVector();
    }

}
