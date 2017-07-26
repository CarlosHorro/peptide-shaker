package eu.isas.peptideshaker.gui.pride.annotationdialogs;

import com.compomics.util.Util;
import com.compomics.util.pride.prideobjects.Reference;
import com.compomics.util.pride.prideobjects.ReferenceGroup;
import eu.isas.peptideshaker.gui.pride.ProjectExportDialog;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 * A dialog for creating new reference groups and editing old ones.
 *
 * @author Harald Barsnes
 */
public class NewReferenceGroupDialog extends javax.swing.JDialog {

    /**
     * A reference to the PRIDE export dialog.
     */
    private ProjectExportDialog prideExportDialog;
    /**
     * The last valid input for reference name.
     */
    private String lastNameInput = "";

    /**
     * Creates a new NewReferenceGroupDialog.
     *
     * @param prideExportDialog the ProjectExportDialog parent
     * @param modal if the dialog is to be modal or not
     */
    public NewReferenceGroupDialog(ProjectExportDialog prideExportDialog, boolean modal) {
        super(prideExportDialog, modal);
        this.prideExportDialog = prideExportDialog;
        initComponents();
        setUpGUI();
        validateInput();
        setLocationRelativeTo(prideExportDialog);
        referencesJTableMouseReleased(null);
        setVisible(true);
    }

    /**
     * Creates a new NewReferenceGroupDialog.
     *
     * @param prideExportDialog the ProjectExportDialog parent
     * @param modal if the dialog is to be modal or not
     * @param referenceGroup the reference group
     */
    public NewReferenceGroupDialog(ProjectExportDialog prideExportDialog, boolean modal, ReferenceGroup referenceGroup) {
        super(prideExportDialog, modal);
        this.prideExportDialog = prideExportDialog;
        initComponents();
        setUpGUI();

        groupNameTextField.setText(referenceGroup.getName());

        for (int i = 0; i < referenceGroup.getReferences().size(); i++) {
            ((DefaultTableModel) referencesJTable.getModel()).addRow(new Object[]{
                        referencesJTable.getRowCount() + 1,
                        referenceGroup.getReferences().get(i).getReference(),
                        referenceGroup.getReferences().get(i).getPmid(),
                        referenceGroup.getReferences().get(i).getDoi()
                    });
        }

        validateInput();
        referencesJTableMouseReleased(null);
        setTitle("Edit References");
        setLocationRelativeTo(prideExportDialog);
        setVisible(true);
    }
    
    /**
     * Set up the GUI.
     */
    private void setUpGUI() {
        referencesScrollPane.getViewport().setOpaque(false);
        referencesJTable.getTableHeader().setReorderingAllowed(false);

        // correct the color for the upper right corner
        JPanel proteinCorner = new JPanel();
        proteinCorner.setBackground(referencesJTable.getTableHeader().getBackground());
        referencesScrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, proteinCorner);
        
        // the index column
        referencesJTable.getColumn(" ").setMaxWidth(50);
        referencesJTable.getColumn(" ").setMinWidth(50);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupJMenu = new javax.swing.JPopupMenu();
        editJMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        moveUpJMenuItem = new javax.swing.JMenuItem();
        moveDownJMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        deleteSelectedRowJMenuItem = new javax.swing.JMenuItem();
        backgroundPanel = new javax.swing.JPanel();
        referencestPanel = new javax.swing.JPanel();
        referencesScrollPane = new javax.swing.JScrollPane();
        referencesJTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        groupNameLabel = new javax.swing.JLabel();
        groupNameTextField = new javax.swing.JTextField();
        deleteGroupButton = new javax.swing.JButton();
        okJButton = new javax.swing.JButton();
        groupNameNoteLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();

        editJMenuItem.setMnemonic('E');
        editJMenuItem.setText("Edit");
        editJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJMenuItemActionPerformed(evt);
            }
        });
        popupJMenu.add(editJMenuItem);
        popupJMenu.add(jSeparator3);

        moveUpJMenuItem.setMnemonic('U');
        moveUpJMenuItem.setText("Move Up");
        moveUpJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpJMenuItemActionPerformed(evt);
            }
        });
        popupJMenu.add(moveUpJMenuItem);

        moveDownJMenuItem.setMnemonic('D');
        moveDownJMenuItem.setText("Move Down");
        moveDownJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownJMenuItemActionPerformed(evt);
            }
        });
        popupJMenu.add(moveDownJMenuItem);
        popupJMenu.add(jSeparator4);

        deleteSelectedRowJMenuItem.setText("Delete");
        deleteSelectedRowJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedRowJMenuItemActionPerformed(evt);
            }
        });
        popupJMenu.add(deleteSelectedRowJMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Reference Group");

        backgroundPanel.setBackground(new java.awt.Color(230, 230, 230));

        referencestPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("References"));
        referencestPanel.setOpaque(false);

        referencesScrollPane.setOpaque(false);

        referencesJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " ", "Reference", "PMID", "DOI"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        referencesJTable.setOpaque(false);
        referencesJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        referencesJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                referencesJTableMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                referencesJTableMouseReleased(evt);
            }
        });
        referencesScrollPane.setViewportView(referencesJTable);

        addButton.setText("Add Reference");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        groupNameLabel.setText("Group Name*");

        groupNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                groupNameTextFieldKeyReleased(evt);
            }
        });

        deleteGroupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Error_3.png"))); // NOI18N
        deleteGroupButton.setToolTipText("Delete Reference Group");
        deleteGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteGroupButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteGroupButtonMouseExited(evt);
            }
        });
        deleteGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGroupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout referencestPanelLayout = new javax.swing.GroupLayout(referencestPanel);
        referencestPanel.setLayout(referencestPanelLayout);
        referencestPanelLayout.setHorizontalGroup(
            referencestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(referencestPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(referencestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(referencesScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, referencestPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addButton))
                    .addGroup(referencestPanelLayout.createSequentialGroup()
                        .addComponent(groupNameLabel)
                        .addGap(18, 18, 18)
                        .addComponent(groupNameTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        referencestPanelLayout.setVerticalGroup(
            referencestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(referencestPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(referencestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(groupNameLabel)
                    .addComponent(groupNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(referencesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton)
                .addContainerGap())
        );

        referencestPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {deleteGroupButton, groupNameTextField});

        okJButton.setText("OK");
        okJButton.setEnabled(false);
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okJButtonActionPerformed(evt);
            }
        });

        groupNameNoteLabel.setFont(groupNameNoteLabel.getFont().deriveFont((groupNameNoteLabel.getFont().getStyle() | java.awt.Font.ITALIC)));
        groupNameNoteLabel.setText("Note that Group Name is only for internal reference and is not included in the PRIDE XML.");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(referencestPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(groupNameNoteLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(okJButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        backgroundPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okJButton});

        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(referencestPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okJButton)
                    .addComponent(groupNameNoteLabel)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Saves the reference and closes the dialog.
     *
     * @param evt
     */
    private void okJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okJButtonActionPerformed

        ArrayList<Reference> references = new ArrayList<>();

        for (int i = 0; i < referencesJTable.getRowCount(); i++) {
            references.add(new Reference(
                    (String) referencesJTable.getValueAt(i, 1),
                    (String) referencesJTable.getValueAt(i, 2),
                    (String) referencesJTable.getValueAt(i, 3)));
        }

        prideExportDialog.setReferences(new ReferenceGroup(references, groupNameTextField.getText()));
        dispose();
    }//GEN-LAST:event_okJButtonActionPerformed

    /**
     * Enable/disable the delete button.
     *
     * @param evt
     */
    private void referencesJTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_referencesJTableMouseReleased
        int selectedRow = referencesJTable.getSelectedRow();
        deleteSelectedRowJMenuItem.setEnabled(selectedRow != -1);
    }//GEN-LAST:event_referencesJTableMouseReleased

    /**
     * Open the New Reference dialog.
     *
     * @param evt
     */
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        new NewReferenceDialog(this, true);
    }//GEN-LAST:event_addButtonActionPerformed

    /**
     * Checks if all mandatory information is filled in. Enables or disables the
     * OK button.
     */
    private void groupNameTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_groupNameTextFieldKeyReleased
        validateInput();
    }//GEN-LAST:event_groupNameTextFieldKeyReleased

    private void editJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJMenuItemActionPerformed
        int selectedRow = referencesJTable.getSelectedRow();

        this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        new NewReferenceDialog(this, true, new Reference(
                    (String) referencesJTable.getValueAt(selectedRow, 1),
                    (String) referencesJTable.getValueAt(selectedRow, 2),
                    (String) referencesJTable.getValueAt(selectedRow, 3)), 
                    selectedRow);
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_editJMenuItemActionPerformed

    private void moveUpJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpJMenuItemActionPerformed
        int selectedRow = referencesJTable.getSelectedRow();
        int selectedColumn = referencesJTable.getSelectedColumn();

        Object[] tempRow = new Object[]{
            referencesJTable.getValueAt(selectedRow - 1, 0),
            referencesJTable.getValueAt(selectedRow - 1, 1),
            referencesJTable.getValueAt(selectedRow - 1, 2),
            referencesJTable.getValueAt(selectedRow - 1, 3)
        };

        ((DefaultTableModel) referencesJTable.getModel()).removeRow(selectedRow - 1);
        ((DefaultTableModel) referencesJTable.getModel()).insertRow(selectedRow, tempRow);

        referencesJTable.changeSelection(selectedRow - 1, selectedColumn, false, false);

        updateTableIndexes();
    }//GEN-LAST:event_moveUpJMenuItemActionPerformed

    private void moveDownJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownJMenuItemActionPerformed
        int selectedRow = referencesJTable.getSelectedRow();
        int selectedColumn = referencesJTable.getSelectedColumn();

        Object[] tempRow = new Object[]{
            referencesJTable.getValueAt(selectedRow + 1, 0),
            referencesJTable.getValueAt(selectedRow + 1, 1),
            referencesJTable.getValueAt(selectedRow + 1, 2),
            referencesJTable.getValueAt(selectedRow + 1, 3)
        };

        ((DefaultTableModel) referencesJTable.getModel()).removeRow(selectedRow + 1);
        ((DefaultTableModel) referencesJTable.getModel()).insertRow(selectedRow, tempRow);

        referencesJTable.changeSelection(selectedRow + 1, selectedColumn, false, false);

        updateTableIndexes();
    }//GEN-LAST:event_moveDownJMenuItemActionPerformed

    private void deleteSelectedRowJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSelectedRowJMenuItemActionPerformed

        int selectedRow = referencesJTable.getSelectedRow();

        if (selectedRow != -1) {

            ((DefaultTableModel) referencesJTable.getModel()).removeRow(selectedRow);
            updateTableIndexes();
            validateInput();
        }
    }//GEN-LAST:event_deleteSelectedRowJMenuItemActionPerformed

    /**
     * Open the popup menu.
     *
     * @param evt
     */
    private void referencesJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_referencesJTableMouseClicked
        if (evt.getButton() == 3) {

            int row = referencesJTable.rowAtPoint(evt.getPoint());
            int column = referencesJTable.columnAtPoint(evt.getPoint());

            referencesJTable.changeSelection(row, column, false, false);

            this.moveUpJMenuItem.setEnabled(true);
            this.moveDownJMenuItem.setEnabled(true);

            if (row == referencesJTable.getRowCount() - 1) {
                this.moveDownJMenuItem.setEnabled(false);
            }

            if (row == 0) {
                this.moveUpJMenuItem.setEnabled(false);
            }

            popupJMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        } else if (evt.getButton() == 1 && evt.getClickCount() == 2) {
            editJMenuItemActionPerformed(null);
        }
    }//GEN-LAST:event_referencesJTableMouseClicked

    /**
     * Delete the given group and close the dialog.
     * 
     * @param evt 
     */
    private void deleteGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGroupButtonActionPerformed
        dispose();
        prideExportDialog.deleteReferenceGroup(new ReferenceGroup(new ArrayList<>(), groupNameTextField.getText()));
    }//GEN-LAST:event_deleteGroupButtonActionPerformed

    /**
     * Changes the cursor into a hand cursor.
     *
     * @param evt
     */
    private void deleteGroupButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteGroupButtonMouseEntered
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_deleteGroupButtonMouseEntered

    /**
     * Changes the cursor back to the default cursor.
     *
     * @param evt
     */
    private void deleteGroupButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteGroupButtonMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_deleteGroupButtonMouseExited

     /**
     * Close the dialog without saving.
     * 
     * @param evt 
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteGroupButton;
    private javax.swing.JMenuItem deleteSelectedRowJMenuItem;
    private javax.swing.JMenuItem editJMenuItem;
    private javax.swing.JLabel groupNameLabel;
    private javax.swing.JLabel groupNameNoteLabel;
    private javax.swing.JTextField groupNameTextField;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JMenuItem moveDownJMenuItem;
    private javax.swing.JMenuItem moveUpJMenuItem;
    private javax.swing.JButton okJButton;
    private javax.swing.JPopupMenu popupJMenu;
    private javax.swing.JTable referencesJTable;
    private javax.swing.JScrollPane referencesScrollPane;
    private javax.swing.JPanel referencestPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Checks if all mandatory information is filled in. Enables or disables the
     * OK button.
     */
    private void validateInput() {

        String input = groupNameTextField.getText();
        for (String forbiddenCharacter : Util.forbiddenCharacters) {
            if (input.contains(forbiddenCharacter)) {
                JOptionPane.showMessageDialog(null, "'" + forbiddenCharacter + "' is not allowed in group names.",
                        "Forbidden Character", JOptionPane.WARNING_MESSAGE);
                groupNameTextField.setText(lastNameInput);
                return;
            }
        }
        lastNameInput = input;

        if (groupNameTextField.getText().length() > 0
                && referencesJTable.getRowCount() > 0) {
            okJButton.setEnabled(true);
        } else {
            okJButton.setEnabled(false);
        }

        // highlight the fields that have not been filled
        if (groupNameTextField.getText().length() > 0) {
            groupNameLabel.setForeground(Color.BLACK);
        } else {
            groupNameLabel.setForeground(Color.RED);
        }
    }

    /**
     * Add a new reference to the table.
     *
     * @param reference the reference
     */
    public void insertReference(Reference reference) {
        ((DefaultTableModel) referencesJTable.getModel()).addRow(new Object[]{
                    referencesJTable.getRowCount() + 1,
                    reference.getReference(),
                    reference.getPmid(),
                    reference.getDoi()
                });
        validateInput();
    }
    
    /**
     * Add a new reference to the table.
     *
     * @param reference the reference
     * @param row the index of the row to edit 
     */
    public void editReference(Reference reference, int row) {
        referencesJTable.setValueAt(reference.getReference(), row, 1);
        referencesJTable.setValueAt(reference.getPmid(), row, 2);
        referencesJTable.setValueAt(reference.getDoi(), row, 3);
    }
    
    /**
     * Update the table indexes.
     */
    private void updateTableIndexes() {
        for (int i=0;i<referencesJTable.getRowCount(); i++) {
            referencesJTable.setValueAt(i+1, i, 0);
        }
    }
}
