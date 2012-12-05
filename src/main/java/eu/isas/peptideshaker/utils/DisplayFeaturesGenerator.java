package eu.isas.peptideshaker.utils;

import com.compomics.util.Util;
import com.compomics.util.experiment.biology.PTM;
import com.compomics.util.experiment.biology.PTMFactory;
import com.compomics.util.experiment.biology.Peptide;
import com.compomics.util.experiment.identification.Identification;
import com.compomics.util.experiment.identification.SequenceFactory;
import com.compomics.util.experiment.identification.matches.ModificationMatch;
import com.compomics.util.experiment.identification.matches.PeptideMatch;
import com.compomics.util.preferences.ModificationProfile;
import com.compomics.util.protein.Header;
import com.compomics.util.protein.Header.DatabaseType;
import eu.isas.peptideshaker.gui.PeptideShakerGUI;
import eu.isas.peptideshaker.myparameters.PSPtmScores;
import eu.isas.peptideshaker.preferences.DisplayPreferences;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class creates the display features needed for the GUI.
 *
 * @author Marc Vaudel
 */
public class DisplayFeaturesGenerator {

    /**
     * The main GUI instance.
     */
    private PeptideShakerGUI peptideShakerGUI;
    /**
     * The sequence factory.
     */
    private SequenceFactory sequenceFactory = SequenceFactory.getInstance();
    /**
     * The compomics PTM factory.
     */
    private PTMFactory ptmFactory = PTMFactory.getInstance();

    /**
     * Constructor.
     *
     * @param peptideShakerGUI the main instance of the GUI
     */
    public DisplayFeaturesGenerator(PeptideShakerGUI peptideShakerGUI) {
        this.peptideShakerGUI = peptideShakerGUI;
    }

    /**
     * Transforms the protein accession number into an HTML link to the
     * corresponding database. Note that this is a complete HTML with HTML and a
     * href tags, where the main use is to include it in the protein tables.
     *
     * @param proteinAccession the protein to get the database link for
     * @return the transformed accession number
     */
    public String addDatabaseLink(String proteinAccession) {

        String accessionNumberWithLink = proteinAccession;

        try {
            if (sequenceFactory.getHeader(proteinAccession) != null) {

                // try to find the database from the SequenceDatabase
                Header.DatabaseType databaseType = sequenceFactory.getHeader(proteinAccession).getDatabaseType();

                // create the database link
                if (databaseType != null) {

                    // @TODO: support more databases

                    if (databaseType == Header.DatabaseType.IPI || databaseType == Header.DatabaseType.UniProt) {
                        accessionNumberWithLink = "<html><a href=\"" + getUniProtAccessionLink(proteinAccession)
                                + "\"><font color=\"" + peptideShakerGUI.getNotSelectedRowHtmlTagFontColor() + "\">"
                                + proteinAccession + "</font></a></html>";
                    } else if (databaseType == Header.DatabaseType.NCBI) {
                        accessionNumberWithLink = "<html><a href=\"" + getNcbiAccessionLink(proteinAccession)
                                + "\"><font color=\"" + peptideShakerGUI.getNotSelectedRowHtmlTagFontColor() + "\">"
                                + proteinAccession + "</font></a></html>";
                    } else {
                        // unknown database!
                    }
                }
            }
        } catch (Exception e) {
            peptideShakerGUI.catchException(e);
        }

        return accessionNumberWithLink;
    }

    /**
     * Transforms the protein accesion number into an HTML link to the
     * corresponding database. Note that this is a complete HTML with HTML and a
     * href tags, where the main use is to include it in the protein tables.
     *
     * @param proteins the list of proteins to get the database links for
     * @return the transformed accession number
     */
    public String addDatabaseLinks(ArrayList<String> proteins) {

        if (proteins.isEmpty()) {
            return "";
        }

        String accessionNumberWithLink = "<html>";

        for (int i = 0; i < proteins.size(); i++) {

            String proteinAccession = proteins.get(i);

            try {
                if (!SequenceFactory.isDecoy(proteins.get(i)) && sequenceFactory.getHeader(proteinAccession) != null) {

                    // try to find the database from the SequenceDatabase
                    DatabaseType database = sequenceFactory.getHeader(proteinAccession).getDatabaseType();

                    // create the database link
                    if (database != null) {

                        // @TODO: support more databases

                        if (database == DatabaseType.IPI || database == DatabaseType.UniProt) {
                            accessionNumberWithLink += "<a href=\"" + getUniProtAccessionLink(proteinAccession)
                                    + "\"><font color=\"" + peptideShakerGUI.getNotSelectedRowHtmlTagFontColor() + "\">"
                                    + proteinAccession + "</font></a>, ";
                        } else if (database == DatabaseType.NCBI) {
                            accessionNumberWithLink += "<a href=\"" + getNcbiAccessionLink(proteinAccession)
                                    + "\"><font color=\"" + peptideShakerGUI.getNotSelectedRowHtmlTagFontColor() + "\">"
                                    + proteinAccession + "</font></a>, ";
                        } else {
                            // unknown database!
                            accessionNumberWithLink += proteinAccession + ", ";
                        }
                    }
                } else {
                    accessionNumberWithLink += proteinAccession + ", ";
                }
            } catch (Exception e) {
                accessionNumberWithLink += proteinAccession + ", ";
            }
        }

        // remove the last ', '
        accessionNumberWithLink = accessionNumberWithLink.substring(0, accessionNumberWithLink.length() - 2);
        accessionNumberWithLink += "</html>";

        return accessionNumberWithLink;
    }

    /**
     * Returns the protein accession number as a web link to the given protein
     * at http://srs.ebi.ac.uk.
     *
     * @param proteinAccession the protein accession number
     * @param database the protein database
     * @return the protein accession web link
     */
    public String getSrsAccessionLink(String proteinAccession, String database) {
        return "http://srs.ebi.ac.uk/srsbin/cgi-bin/wgetz?-e+%5b" + database + "-AccNumber:" + proteinAccession + "%5d";
    }

    /**
     * Returns the protein accession number as a web link to the given protein
     * at http://www.uniprot.org/uniprot.
     *
     * @param proteinAccession the protein accession number
     * @return the protein accession web link
     */
    public String getUniProtAccessionLink(String proteinAccession) {
        return "http://www.uniprot.org/uniprot/" + proteinAccession;
    }

    /**
     * Returns the protein accession number as a web link to the given protein
     * at http://www.ncbi.nlm.nih.gov/protein.
     *
     * @param proteinAccession the protein accession number
     * @return the protein accession web link
     */
    public String getNcbiAccessionLink(String proteinAccession) {
        return "http://www.ncbi.nlm.nih.gov/protein/" + proteinAccession;
    }

    /**
     * Returns the project accession number as a web link to the given project
     * in PRIDE.
     *
     * @param projectAccession the project accession number
     * @return the project accession web link
     */
    public String getPrideAccessionLink(String projectAccession) {
        return "http://www.ebi.ac.uk/pride/directLink.do?experimentAccessionNumber=" + projectAccession;
    }

    /**
     * Returns a String with the HTML tooltip for the peptide indicating the
     * modification details.
     *
     * @param peptide
     * @param showFixedMods if true, the fixed mods are annotated
     * @return a String with the HTML tooltip for the peptide
     */
    public String getPeptideModificationTooltipAsHtml(Peptide peptide) {

        String tooltip = "<html>";
        ArrayList<String> alreadyAnnotated = new ArrayList<String>();

        DisplayPreferences displayPreferences = peptideShakerGUI.getDisplayPreferences();
        
        for (ModificationMatch modMatch : peptide.getModificationMatches()) {
            String modName = modMatch.getTheoreticPtm();
            PTM ptm = ptmFactory.getPTM(modName);

            if ((ptm.getType() == PTM.MODAA && modMatch.isVariable())
                    || displayPreferences.isDisplayedPTM(modName)) {

                int modSite = modMatch.getModificationSite();

                if (modSite > 0) {
                    char affectedResidue = peptide.getSequence().charAt(modSite - 1);
                    Color ptmColor = peptideShakerGUI.getSearchParameters().getModificationProfile().getColor(modName);

                    if (!alreadyAnnotated.contains(modName + "_" + affectedResidue)) {
                        tooltip += "<span style=\"color:#" + Util.color2Hex(Color.WHITE) + ";background:#" + Util.color2Hex(ptmColor) + "\">"
                                + affectedResidue
                                + "</span>"
                                + ": " + modName + "<br>";

                        alreadyAnnotated.add(modName + "_" + affectedResidue);
                    }
                }
            }
        }

        if (!tooltip.equalsIgnoreCase("<html>")) {
            tooltip += "</html>";
        } else {
            tooltip = null;
        }

        return tooltip;
    }

    /**
     * Returns the peptide with modification sites colored on the sequence based on PeptideShaker site inference results.
     * Shall be used for peptides, not PSMs.
     *
     * @param peptideKey the peptide key
     * @param includeHtmlStartEndTag if true, html start and end tags are added
     * @return the colored peptide sequence
     */
    public String getColoredPeptideSequence(String peptideKey, boolean includeHtmlStartEndTag) {
        try {
            DisplayPreferences displayPreferences = peptideShakerGUI.getDisplayPreferences();
            Identification identification = peptideShakerGUI.getIdentification();
            PeptideMatch peptideMatch = identification.getPeptideMatch(peptideKey);
            Peptide peptide = peptideMatch.getTheoreticPeptide();
            HashMap<Integer, ArrayList<String>> fixedModifications = getFilteredModifications(peptide.getIndexedFixedModifications(), displayPreferences),
                    mainLocations = new HashMap<Integer, ArrayList<String>>(),
                    secondaryLocations = new HashMap<Integer, ArrayList<String>>();
            PSPtmScores ptmScores = new PSPtmScores();
            ptmScores = (PSPtmScores) peptideMatch.getUrParam(ptmScores);
            if (ptmScores != null) {
                mainLocations = getFilteredModifications(ptmScores.getMainModificationSites(), displayPreferences);
                secondaryLocations = getFilteredModifications(ptmScores.getSecondaryModificationSites(), displayPreferences);
            }
            return Peptide.getModifiedSequenceAsHtml(peptideShakerGUI.getSearchParameters().getModificationProfile(),
                    includeHtmlStartEndTag, peptide,
                    mainLocations, secondaryLocations, fixedModifications);
        } catch (Exception e) {
            peptideShakerGUI.catchException(e);
            return "Error";
        }
    }

    /**
     * Returns the modified sequence as an HTML string with potential
     * modification sites color coding. /!\ this method will work only if the
     * ptm found in the peptide are in the PTMFactory. /!\ this method uses the
     * modifications as set in the modification matches of this peptide and
     * displays all of them
     *
     * @param modificationProfile the modification profile of the search
     * @param includeHtmlStartEndTag if true, start and end html tags are added
     * @return the modified sequence as an HTML string
     */
    public String getColoredPeptideSequence(Peptide peptide, boolean includeHtmlStartEndTag) {
        HashMap<Integer, ArrayList<String>> mainModificationSites = new HashMap<Integer, ArrayList<String>>(),
                secondaryModificationSites = new HashMap<Integer, ArrayList<String>>(),
                fixedModificationSites = new HashMap<Integer, ArrayList<String>>();
        ModificationProfile modificationProfile = peptideShakerGUI.getSearchParameters().getModificationProfile();
        DisplayPreferences displayPreferences = peptideShakerGUI.getDisplayPreferences();
        for (ModificationMatch modMatch : peptide.getModificationMatches()) {
            String modName = modMatch.getTheoreticPtm();
            if (displayPreferences.isDisplayedPTM(modName)) {
            int modSite = modMatch.getModificationSite();
            if (modMatch.isVariable()) {
                if (modMatch.isConfident()) {
                    if (!mainModificationSites.containsKey(modSite)) {
                        mainModificationSites.put(modSite, new ArrayList<String>());
                    }
                    mainModificationSites.get(modSite).add(modName);
                } else {
                    if (!secondaryModificationSites.containsKey(modSite)) {
                        secondaryModificationSites.put(modSite, new ArrayList<String>());
                    }
                    secondaryModificationSites.get(modSite).add(modName);
                }
            } else {
                if (!fixedModificationSites.containsKey(modSite)) {
                    fixedModificationSites.put(modSite, new ArrayList<String>());
                }
                fixedModificationSites.get(modSite).add(modName);
            }
            }
        }
        return Peptide.getModifiedSequenceAsHtml(modificationProfile, includeHtmlStartEndTag, peptide, mainModificationSites, secondaryModificationSites, fixedModificationSites);
    }

    /**
     * Filters the modification map according to the user's display preferences
     *
     * @param modificationMap the map of modifications to filter (amino acid ->
     * list of modifications, 1 is the first aa)
     * @param displayPreferences the display preferences
     * @return a map of filtered modifications based on the user display
     * preferences
     */
    public static HashMap<Integer, ArrayList<String>> getFilteredModifications(HashMap<Integer, ArrayList<String>> modificationMap, DisplayPreferences displayPreferences) {
        HashMap<Integer, ArrayList<String>> result = new HashMap<Integer, ArrayList<String>>();
        for (int aa : modificationMap.keySet()) {
            for (String ptm : modificationMap.get(aa)) {
                if (displayPreferences.isDisplayedPTM(ptm)) {
                    if (!result.containsKey(aa)) {
                        result.put(aa, new ArrayList<String>());
                    }
                    result.get(aa).add(ptm);
                }
            }
        }
        return result;
    }
}
