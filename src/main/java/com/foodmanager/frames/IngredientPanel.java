package com.foodmanager.frames;

import com.foodmanager.entities.Ingredient;
import com.foodmanager.repository.FoodRepository;
import com.foodmanager.utils.InputValidator;
import javax.swing.DefaultListModel;

/**
 *
 * @author roman
 */
public final class IngredientPanel extends javax.swing.JPanel {

    private final MainFrame mainFrame;
    private final DefaultListModel model;
    /**
     * Creates new form Ingredients
     * 
     * @param mainFrame Frame that contains all the graphic components.
     */
    public IngredientPanel(MainFrame mainFrame) {
        initComponents();
        this.mainFrame = mainFrame;
        this.model = new DefaultListModel();
        lstIngredients.setModel(model);
        updateList();
    }
    
    public void updateList() {
        model.removeAllElements();
        new FoodRepository().getIngredientList().forEach((ingredient) -> {
            model.addElement(ingredient.getName());
        });
    }
    
    public void updateInformationForm() {
            txtName.setText(null);
            txtPresentation.setText(null);
            txtWeight.setText(null);
            txtPrice.setText(null);
            txtDescription.setText(null);
    }
    
    public void updateNewIngredientForm() {
        txtAddName.setText(null);
        txtAddPresentation.setText(null);
        txtAddWeight.setText(null);
        txtAddPrice.setText(null);
        txtAddDescription.setText(null);
    }
    
    private void displayIngredient() {
        int i = lstIngredients.getSelectedIndex();
        if(i != -1) {
            Ingredient ing = new FoodRepository().getIngredientList().get(i);
            txtName.setText(ing.getName());
            txtPresentation.setText(ing.getPresentation());
            txtWeight.setText(String.valueOf(ing.getWeight()));
            txtPrice.setText(String.valueOf(ing.getPrice()));
            txtDescription.setText(ing.getDescription());
        }
    }
    
    private void processRemoveIngredient() {
        String name = lstIngredients.getSelectedValue();
        if(name != null) {
            if(new FoodRepository().removeIngredient(name)){
                mainFrame.updateIngredients();
                updateInformationForm();
                return;
            }
            System.out.println("Remove failed, cannot find in repository");
        }
    }
    
    private void processAddIngredient() {
        if(addValidation()) {
            Ingredient add = new Ingredient(txtAddName.getText(),
                    txtAddPresentation.getText(),
                    Double.valueOf(txtAddWeight.getText()),
                    Double.valueOf(txtAddPrice.getText()),
                    txtAddDescription.getText());
            if(new FoodRepository().getIngredientList().contains(add))
                return;
            new FoodRepository().addIngredient(add);
            updateNewIngredientForm();
            System.out.println("Added");
            mainFrame.updateIngredients();
        } else {
            System.out.println("Not added");
        }
    }
    
    private void processModifyIngredient() {
        int i = lstIngredients.getSelectedIndex();
        if(modifyValidation() && i != -1) {
            Ingredient mod = new Ingredient(txtName.getText(),
                    txtPresentation.getText(),
                    Double.valueOf(txtWeight.getText()),
                    Double.valueOf(txtPrice.getText()),
                    txtDescription.getText());
            new FoodRepository().getIngredientList().add(i, mod);
            new FoodRepository().getIngredientList().remove(i+1);
            mainFrame.updateIngredients();
        }
    }
    
    private boolean addValidation() {
        
        if(!InputValidator.isTextValid(txtAddName.getText())) {
            System.out.println("Invalid name, it cannot be empty or start with empty spaces");
            return false;
        }
        if(!InputValidator.isTextValid(txtAddPresentation.getText())) {
            System.out.println("Invalid presentation, it cannot be empty or start with empty spaces");
            return false;
        }
        if (!InputValidator.isNumberValid(txtAddWeight.getText())) {
            System.out.println("Invalid weight, it cannot be empty, start with emply spaces or use characters");
            return false;
        }
        if (!InputValidator.isNumberValid(txtAddPrice.getText())) {
            System.out.println("Invalid price, it cannot be empty, start with emply spaces or use characters");
            return false;
        }
        if(!InputValidator.isTextValid(txtAddDescription.getText())) {
            System.out.println("Invalid description, it cannot be empty or start with empty spaces");
            return false;
        }
        return true;
    }
    
    private boolean modifyValidation() {
        
        if(!InputValidator.isTextValid(txtName.getText())) {
            System.out.println("Invalid name, it cannot be empty or start with empty spaces");
            return false;
        }
        if(!InputValidator.isTextValid(txtPresentation.getText())) {
            System.out.println("Invalid presentation, it cannot be empty or start with empty spaces");
            return false;
        }
        if (!InputValidator.isNumberValid(txtWeight.getText())) {
            System.out.println("Invalid weight, it cannot be empty, start with emply spaces or use characters");
            return false;
        }
        if (!InputValidator.isNumberValid(txtPrice.getText())) {
            System.out.println("Invalid price, it cannot be empty, start with emply spaces or use characters");
            return false;
        }
        if(!InputValidator.isTextValid(txtDescription.getText())) {
            System.out.println("Invalid description, it cannot be empty or start with empty spaces");
        }
        if(!InputValidator.isPointerValid(lstIngredients.getSelectedIndex())) {
            System.out.println("Invalid ingredient, you must select a recipe to modify");
            return false;
        }
        
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblFrameDesc = new javax.swing.JLabel();
        sepTitle = new javax.swing.JSeparator();
        lblIngredientList = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstIngredients = new javax.swing.JList<>();
        btnRemoveIngredient = new javax.swing.JButton();
        sep1 = new javax.swing.JSeparator();
        sep2 = new javax.swing.JSeparator();
        pnlIngredient = new javax.swing.JPanel();
        btnModifyIngredient = new javax.swing.JButton();
        txtWeight = new javax.swing.JTextField();
        lblIngredientPrice = new javax.swing.JLabel();
        lblIngredientInfo = new javax.swing.JLabel();
        lblIngredientWeight = new javax.swing.JLabel();
        lblIngredientName = new javax.swing.JLabel();
        txtPresentation = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        lblIngredientPresentation = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        lblIngredientDescription = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        pnlAddIngredient = new javax.swing.JPanel();
        lblAddWeight = new javax.swing.JLabel();
        txtAddPrice = new javax.swing.JTextField();
        txtAddPresentation = new javax.swing.JTextField();
        lblAddDescription = new javax.swing.JLabel();
        txtAddName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddDescription = new javax.swing.JTextArea();
        lblAddName = new javax.swing.JLabel();
        btnAddIngredient = new javax.swing.JButton();
        txtAddWeight = new javax.swing.JTextField();
        lblAddPrice = new javax.swing.JLabel();
        lblAddPresentation = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        lblTitle.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Ingredients");

        lblFrameDesc.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblFrameDesc.setText("In this panel you are able to add new ingredients, modify them or remove them from the database.");

        lblIngredientList.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblIngredientList.setText("Ingredient list:");

        lstIngredients.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lstIngredients.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstIngredientsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstIngredients);

        btnRemoveIngredient.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnRemoveIngredient.setText("Remove");
        btnRemoveIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveIngredientActionPerformed(evt);
            }
        });

        sep1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        sep2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnModifyIngredient.setText("Modify this existing ingredient");
        btnModifyIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyIngredientActionPerformed(evt);
            }
        });

        txtWeight.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        lblIngredientPrice.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientPrice.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientPrice.setText("Price (arg$):");

        lblIngredientInfo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblIngredientInfo.setText("Ingredient information:");

        lblIngredientWeight.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientWeight.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientWeight.setText("Weight (grams):");

        lblIngredientName.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientName.setText("Ingredient name:");

        txtPresentation.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        txtName.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        lblIngredientPresentation.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientPresentation.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientPresentation.setText("Presentation:");

        txtPrice.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        lblIngredientDescription.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientDescription.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientDescription.setText("Description:");

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtDescription.setRows(5);
        jScrollPane3.setViewportView(txtDescription);

        javax.swing.GroupLayout pnlIngredientLayout = new javax.swing.GroupLayout(pnlIngredient);
        pnlIngredient.setLayout(pnlIngredientLayout);
        pnlIngredientLayout.setHorizontalGroup(
            pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIngredientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnModifyIngredient, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(lblIngredientInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlIngredientLayout.createSequentialGroup()
                        .addGroup(pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblIngredientPresentation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblIngredientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblIngredientPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblIngredientWeight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIngredientDescription, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtWeight, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(txtPrice)
                            .addComponent(txtPresentation)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        pnlIngredientLayout.setVerticalGroup(
            pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIngredientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIngredientInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIngredientName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIngredientPresentation)
                    .addComponent(txtPresentation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIngredientWeight))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIngredientPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIngredientLayout.createSequentialGroup()
                        .addComponent(lblIngredientDescription)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addGap(18, 18, 18)
                .addComponent(btnModifyIngredient)
                .addContainerGap())
        );

        lblAddWeight.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblAddWeight.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAddWeight.setText("Weight (grams):");

        lblAddDescription.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblAddDescription.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAddDescription.setText("Description:");

        txtAddName.setMaximumSize(new java.awt.Dimension(6, 22));

        txtAddDescription.setColumns(20);
        txtAddDescription.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtAddDescription.setRows(5);
        jScrollPane2.setViewportView(txtAddDescription);

        lblAddName.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblAddName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAddName.setText("Ingredient name:");

        btnAddIngredient.setText("Add new ingredient");
        btnAddIngredient.setMaximumSize(new java.awt.Dimension(201, 25));
        btnAddIngredient.setMinimumSize(new java.awt.Dimension(201, 25));
        btnAddIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddIngredientActionPerformed(evt);
            }
        });

        lblAddPrice.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblAddPrice.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAddPrice.setText("Price (arg$):");

        lblAddPresentation.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblAddPresentation.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAddPresentation.setText("Presentation:");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel13.setText("New ingredient form:");

        javax.swing.GroupLayout pnlAddIngredientLayout = new javax.swing.GroupLayout(pnlAddIngredient);
        pnlAddIngredient.setLayout(pnlAddIngredientLayout);
        pnlAddIngredientLayout.setHorizontalGroup(
            pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddIngredientLayout.createSequentialGroup()
                .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddIngredientLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblAddWeight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAddPresentation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAddPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblAddName)
                            .addComponent(lblAddDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAddName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAddPresentation)
                            .addComponent(txtAddWeight, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAddPrice)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(pnlAddIngredientLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlAddIngredientLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnAddIngredient, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlAddIngredientLayout.setVerticalGroup(
            pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddIngredientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddName)
                    .addComponent(txtAddName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddPresentation)
                    .addComponent(txtAddPresentation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAddWeight))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAddPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddDescription)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnAddIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sepTitle)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIngredientList)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemoveIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sep1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlIngredient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sep2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlAddIngredient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFrameDesc)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sepTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFrameDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sep1)
                    .addComponent(sep2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIngredientList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnRemoveIngredient)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(pnlIngredient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAddIngredient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnAddIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddIngredientActionPerformed
        processAddIngredient();
    }//GEN-LAST:event_btnAddIngredientActionPerformed

    private void btnRemoveIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveIngredientActionPerformed
        processRemoveIngredient();
    }//GEN-LAST:event_btnRemoveIngredientActionPerformed

    private void lstIngredientsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstIngredientsValueChanged
        displayIngredient();
    }//GEN-LAST:event_lstIngredientsValueChanged

    private void btnModifyIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyIngredientActionPerformed
        processModifyIngredient();
    }//GEN-LAST:event_btnModifyIngredientActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddIngredient;
    private javax.swing.JButton btnModifyIngredient;
    private javax.swing.JButton btnRemoveIngredient;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAddDescription;
    private javax.swing.JLabel lblAddName;
    private javax.swing.JLabel lblAddPresentation;
    private javax.swing.JLabel lblAddPrice;
    private javax.swing.JLabel lblAddWeight;
    private javax.swing.JLabel lblFrameDesc;
    private javax.swing.JLabel lblIngredientDescription;
    private javax.swing.JLabel lblIngredientInfo;
    private javax.swing.JLabel lblIngredientList;
    private javax.swing.JLabel lblIngredientName;
    private javax.swing.JLabel lblIngredientPresentation;
    private javax.swing.JLabel lblIngredientPrice;
    private javax.swing.JLabel lblIngredientWeight;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList<String> lstIngredients;
    private javax.swing.JPanel pnlAddIngredient;
    private javax.swing.JPanel pnlIngredient;
    private javax.swing.JSeparator sep1;
    private javax.swing.JSeparator sep2;
    private javax.swing.JSeparator sepTitle;
    private javax.swing.JTextArea txtAddDescription;
    private javax.swing.JTextField txtAddName;
    private javax.swing.JTextField txtAddPresentation;
    private javax.swing.JTextField txtAddPrice;
    private javax.swing.JTextField txtAddWeight;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPresentation;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtWeight;
    // End of variables declaration//GEN-END:variables
}
