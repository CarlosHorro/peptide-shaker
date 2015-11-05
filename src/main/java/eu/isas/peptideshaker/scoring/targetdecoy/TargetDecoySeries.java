package eu.isas.peptideshaker.scoring.targetdecoy;

import eu.isas.peptideshaker.parameters.PSParameter;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class will contain the PEP, FDR and FNR values of a target decoy map
 * directly available for plotting.
 *
 * @author Marc Vaudel
 */
public class TargetDecoySeries {

    /**
     * The probabilistic score.
     */
    private double[] scores;
    /**
     * The log transformed probabilistic score.
     */
    private double[] scoresLog;
    /**
     * The confidence.
     */
    private double[] confidence;
    /**
     * The confidence corresponding to the log score.
     */
    private double[] confidenceLog;
    /**
     * The pep.
     */
    private double[] pep;
    /**
     * The scores where a value has been taken for nTP and nFP.
     */
    private double[] scoresP;
    /**
     * The estimated number of true positives in the bin centered on a given
     * score.
     */
    private double[] nTP;
    /**
     * The estimated number of false positives in the bin centered on a given
     * score.
     */
    private double[] nFP;
    /**
     * The classical FDR.
     */
    private double[] classicalFDR;
    /**
     * The probabilistic FDR.
     */
    private double[] probaFDR;
    /**
     * The probabilistic FNR.
     */
    private double[] probaFNR;
    /**
     * The benefit (1-FNR).
     */
    private double[] probaBenefit;
    /**
     * The number of validated target hits.
     */
    private double[] n;
    /**
     * The classically estimated number of false positives.
     */
    private double[] classicalFP;
    /**
     * The probabilistically estimated number of false positives.
     */
    private double[] probaFP;
    /**
     * Indicates whether the current point is only made of decoy hits.
     */
    private boolean[] decoy;
    /**
     * The probabilistically estimated total number of false positives.
     */
    private double probaNTotal;

    /**
     * Constructor.
     *
     * @param hitMap a map as present in target decoy maps
     * @param nTP the true positives series
     * @param nFP the false positives series
     * @param scoresP the scores
     */
    public TargetDecoySeries(HashMap<Double, TargetDecoyPoint> hitMap, double[] nTP, double[] nFP, double[] scoresP) {

        scores = new double[hitMap.size()];
        scoresLog = new double[scores.length];
        probaNTotal = 0;
        int cpt = 0;
        TargetDecoyPoint currentPoint;

        for (double score : hitMap.keySet()) {
            currentPoint = hitMap.get(score);
            scores[cpt] = score;
            scoresLog[cpt] = PSParameter.getScore(score);
            probaNTotal += (1 - currentPoint.p) * currentPoint.nTarget;
            cpt++;
        }

        Arrays.sort(scores);
        Arrays.sort(scoresLog);

        confidence = new double[scores.length];
        confidenceLog = new double[scores.length];
        classicalFDR = new double[scores.length];
        probaFDR = new double[scores.length];
        probaFNR = new double[scores.length];
        n = new double[scores.length];
        probaFP = new double[scores.length];
        classicalFP = new double[scores.length];
        probaBenefit = new double[scores.length];
        pep = new double[scores.length];
        decoy = new boolean[scores.length];

        double nTemp = 0;
        double classicalFPTemp = 0;
        double probaFPTemp = 0;
        double probaTP = 0;
        double probaFnrTemp;

        for (int i = 0; i < scores.length; i++) {
            currentPoint = hitMap.get(scores[i]);
            nTemp += currentPoint.nTarget;
            classicalFPTemp += currentPoint.nDecoy;
            probaFPTemp += currentPoint.nTarget * (currentPoint.p);
            probaTP += currentPoint.nTarget * (1 - currentPoint.p);
            probaFnrTemp = 100 * (probaNTotal - probaTP) / probaNTotal;
            pep[i] = 100 * currentPoint.p;
            double confidenceAtI = 100 * (1 - currentPoint.p);
            confidence[i] = confidenceAtI;
            int iInvert = scores.length - i - 1;
            confidenceLog[iInvert] = confidenceAtI;
            n[i] = nTemp;
            classicalFP[i] = classicalFPTemp;
            probaFP[i] = probaFPTemp;
            classicalFDR[i] = 100 * classicalFPTemp / nTemp;
            probaFDR[i] = 100 * probaFPTemp / nTemp;
            probaFNR[i] = probaFnrTemp;
            probaBenefit[i] = 100 - probaFnrTemp;
            decoy[i] = currentPoint.nTarget == 0;
        }

        this.nTP = nTP;
        this.nFP = nFP;
        this.scoresP = scoresP;
    }

    /**
     * Completes the results at the desired FDR threshold.
     *
     * @param targetDecoyResults the results containing the threshold
     */
    public void getFDRResults(TargetDecoyResults targetDecoyResults) {

        Double threshold = targetDecoyResults.getFdrLimit();

        if (targetDecoyResults.isClassicalEstimators()) {
            targetDecoyResults.setNoValidated(false);
            for (int i = scores.length - 1; i >= 0; i--) {
                if (classicalFDR[i] <= threshold && !decoy[i]) {
                    targetDecoyResults.setConfidenceLimit(confidence[i]);
                    targetDecoyResults.setFdrLimit(classicalFDR[i]);
                    targetDecoyResults.setn(n[i]);
                    targetDecoyResults.setnFP(classicalFP[i]);
                    targetDecoyResults.setFnrLimit(probaFNR[i]);
                    targetDecoyResults.setnTPTotal(probaNTotal);
                    targetDecoyResults.setScoreLimit(scores[i]);
                    return;
                } else if (i == 0) {
                    targetDecoyResults.setNoValidated(true);
                    targetDecoyResults.setFdrLimit(0);
                    targetDecoyResults.setnFP(0);
                    targetDecoyResults.setConfidenceLimit(0);
                    targetDecoyResults.setn(0);
                    targetDecoyResults.setFnrLimit(probaFNR[0]);
                    targetDecoyResults.setnTPTotal(probaNTotal);
                    targetDecoyResults.setScoreLimit(scores[0]);
                }
            }
        } else {
            for (int i = scores.length - 1; i >= 0; i--) {
                if (probaFDR[i] <= threshold && !decoy[i]) {
                    targetDecoyResults.setConfidenceLimit(confidence[i]);
                    targetDecoyResults.setFdrLimit(probaFDR[i]);
                    targetDecoyResults.setn(n[i]);
                    targetDecoyResults.setnFP(probaFP[i]);
                    targetDecoyResults.setFnrLimit(probaFNR[i]);
                    targetDecoyResults.setnTPTotal(probaNTotal);
                    targetDecoyResults.setScoreLimit(scores[i]);
                    return;
                } else if (i == 0) {
                    targetDecoyResults.setNoValidated(true);
                    targetDecoyResults.setFdrLimit(0);
                    targetDecoyResults.setnFP(0);
                    targetDecoyResults.setConfidenceLimit(0);
                    targetDecoyResults.setn(0);
                    targetDecoyResults.setFnrLimit(probaFNR[0]);
                    targetDecoyResults.setnTPTotal(probaNTotal);
                    targetDecoyResults.setScoreLimit(scores[0]);
                }
            }
        }
    }

    /**
     * Completes the results at the desired confidence threshold.
     *
     * @param targetDecoyResults the results containing the threshold
     */
    public void getConfidenceResults(TargetDecoyResults targetDecoyResults) {

        double threshold = targetDecoyResults.getConfidenceLimit();

        for (int i = 0; i < scores.length - 1; i++) {
            if (confidence[i] < threshold) {
                for (int k = i; k >= 0; k--) {
                    if (!decoy[k]) {
                        targetDecoyResults.setNoValidated(false);
                        if (targetDecoyResults.isClassicalEstimators()) {
                            targetDecoyResults.setFdrLimit(classicalFDR[k]);
                            targetDecoyResults.setnFP(classicalFP[k]);
                        } else {
                            targetDecoyResults.setFdrLimit(probaFDR[k]);
                            targetDecoyResults.setnFP(probaFP[k]);
                        }
                        targetDecoyResults.setConfidenceLimit(confidence[k]);
                        targetDecoyResults.setFnrLimit(probaFNR[k]);
                        targetDecoyResults.setn(n[k]);
                        targetDecoyResults.setnTPTotal(probaNTotal);
                        targetDecoyResults.setScoreLimit(scores[k]);
                        return;
                    }
                }
                targetDecoyResults.setNoValidated(true);
                targetDecoyResults.setFdrLimit(0);
                targetDecoyResults.setnFP(0);
                targetDecoyResults.setConfidenceLimit(confidence[0]);
                targetDecoyResults.setn(0);
                targetDecoyResults.setnTPTotal(probaNTotal);
                targetDecoyResults.setFnrLimit(probaNTotal);
                targetDecoyResults.setScoreLimit(scores[0]);
                return;
            }
        }
    }

    /**
     * Completes the results at the desired FNR threshold.
     *
     * @param targetDecoyResults the results containing the threshold
     */
    public void getFNRResults(TargetDecoyResults targetDecoyResults) {

        double threshold = targetDecoyResults.getFnrLimit();
        targetDecoyResults.setNoValidated(false);

        for (int i = scores.length - 1; i >= 0; i--) {
            if (probaFNR[i] > threshold || i == 0) {
                for (int k = i; k < scores.length; k++) {
                    if (!decoy[k]) {
                        targetDecoyResults.setConfidenceLimit(confidence[k]);
                        if (targetDecoyResults.isClassicalEstimators()) {
                            targetDecoyResults.setFdrLimit(classicalFDR[k]);
                            targetDecoyResults.setnFP(classicalFP[k]);
                        } else {
                            targetDecoyResults.setFdrLimit(probaFDR[k]);
                            targetDecoyResults.setnFP(probaFP[k]);
                        }
                        targetDecoyResults.setn(n[k]);
                        targetDecoyResults.setFnrLimit(probaFNR[k]);
                        targetDecoyResults.setnTPTotal(probaNTotal);
                        targetDecoyResults.setScoreLimit(scores[k]);
                        return;
                    }
                }
                targetDecoyResults.setNoValidated(true);
                targetDecoyResults.setFdrLimit(0);
                targetDecoyResults.setnFP(0);
                targetDecoyResults.setConfidenceLimit(confidence[0]);
                targetDecoyResults.setn(0);
                targetDecoyResults.setnTPTotal(probaNTotal);
                targetDecoyResults.setFnrLimit(probaNTotal);
                targetDecoyResults.setScoreLimit(scores[0]);
            }
        }
    }

    /**
     * Returns the scores where a value has been taken for nTP and nFP.
     *
     * @return the scores where a value has been taken for nTP and nFP
     */
    public double[] getScoresP() {
        return scoresP;
    }

    /**
     * Returns the number of true positives series.
     *
     * @return the number of true positives series
     */
    public double[] getNTP() {
        return nTP;
    }

    /**
     * Returns the number of false positives series.
     *
     * @return the number of false positives series
     */
    public double[] getNFP() {
        return nFP;
    }

    /**
     * Returns the classical FDR series.
     *
     * @return the classical FDR series
     */
    public double[] getClassicalFDR() {
        return classicalFDR;
    }

    /**
     * Returns the confidence series.
     *
     * @return the confidence series
     */
    public double[] getConfidence() {
        return confidence;
    }

    /**
     * Returns the confidence log series.
     *
     * @return the confidence log series
     */
    public double[] getConfidenceLog() {
        return confidenceLog;
    }

    /**
     * Returns the probabilistic benefit series.
     *
     * @return the probabilistic benefit series
     */
    public double[] getProbaBenefit() {
        return probaBenefit;
    }

    /**
     * Returns the probabilistic FDR series.
     *
     * @return the probabilistic FDR series
     */
    public double[] getProbaFDR() {
        return probaFDR;
    }

    /**
     * Returns the probabilistic FNR series.
     *
     * @return the probabilistic FNR series
     */
    public double[] getProbaFNR() {
        return probaFNR;
    }

    /**
     * Returns the score series.
     *
     * @return the score series
     */
    public double[] getScores() {
        return scores;
    }

    /**
     * Returns the log score series.
     *
     * @return the log score series
     */
    public double[] getScoresLog() {
        return scoresLog;
    }

    /**
     * Returns the score series.
     *
     * @return the score series
     */
    public double[] getPEP() {
        return pep;
    }
}
