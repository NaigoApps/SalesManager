/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics.dialogs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import salesmanager.beans.DeliveryDocument;
import salesmanager.graphics.dialogs.internal.NewDeliveryDocumentInternalPanel;

/**
 *
 * @author Lorenzo
 * Create an instance of DeliveryDocument that contains:
 * - Date of document
 * - Customer
 * - List of products with PCV movements
 */
public class NewDeliveryDocumentDialog extends OkCancelDialog{

    private NewDeliveryDocumentInternalPanel panel;
    private DeliveryDocument document;
    
    public NewDeliveryDocumentDialog(JFrame parent) {
        super(parent, true);
        setTitle("Documento di consegna");
        document = null;
        pack();
        setLocationRelativeTo(null);
    }
    
    @Override
    protected JPanel createNorthPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createEastPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createWestPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createCenterPanel() {
        panel = new NewDeliveryDocumentInternalPanel();
        return panel;
    }

    @Override
    protected void onOk() {
        if(panel.updateData()){
            this.document = panel.getDocument();
            dispose();
        }else{
            this.document = null;
        }
    }

    @Override
    protected void onCancel() {
        this.document = null;
        dispose();
    }

    public DeliveryDocument getDocument() {
        return document;
    }
    
    public void setDocument(DeliveryDocument doc){
        panel.setDocument(doc);
        pack();
        setLocationRelativeTo(null);
    }
    
    
}
