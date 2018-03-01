package io.thoughtworksarts.riot.facialrecognition;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FacialEmotionRecognitionAPITest {

    FacialEmotionRecognitionAPI facialRecognition;
    @Mock DeepLearningProcessor deepLearningProcessor;
    @Mock ImageProcessor imageProcessor;

    @Before
    public void setup() {
        initMocks(this);
        when(deepLearningProcessor.getEmotionPrediction(any())).thenReturn(new float[]{1, 2, 3});
        facialRecognition = new FacialEmotionRecognitionAPI(imageProcessor, deepLearningProcessor);
    }

    @Test
    public void shouldRecordEmotionProbabilitiesOnInitialise() {
        facialRecognition.recordEmotionProbabilities();

        verify(imageProcessor).prepareImageForNet(any(), Mockito.anyInt(), Mockito.anyInt(), any());
        verify(deepLearningProcessor).getEmotionPrediction(any());
    }

    @Test
    public void getDominateEmotionShouldReturnCalmWhenCalmHasTheHighestValue() {
        when(deepLearningProcessor.getEmotionPrediction(any())).thenReturn(new float[]{1, 5, 1});
        facialRecognition.recordEmotionProbabilities();

        Emotion dominateEmotion = facialRecognition.getDominateEmotion();
        assertEquals(dominateEmotion, Emotion.CALM);
    }

    @Test
    public void getDominateEmotionShouldReturnCalm() {
        when(deepLearningProcessor.getEmotionPrediction(any())).thenReturn(new float[]{1, 2, 3, 4, 5, 6, 7});
        facialRecognition.recordEmotionProbabilities();

        Emotion dominateEmotion = facialRecognition.getDominateEmotion();
        assertEquals(dominateEmotion, Emotion.DISGUST);
    }
}
