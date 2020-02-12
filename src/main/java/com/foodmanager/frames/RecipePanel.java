package com.foodmanager.frames;

import com.foodmanager.entities.Ingredient;
import com.foodmanager.entities.Recipe;
import com.foodmanager.repository.FoodRepository;
import com.foodmanager.utils.IngredientSearcher;
import com.foodmanager.utils.InputValidator;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author roman
 */
public final class RecipePanel extends javax.swing.JPanel {

    private final MainFrame mainFrame;
    private final DefaultListModel recipesModel;
    private final DefaultListModel modifyRecipeModel;
    private final DefaultListModel newRecipeModel;
    private final DefaultComboBoxModel ingredientList;
    private final DefaultComboBoxModel newIngredientList;
    
    /**
     *
     * @param mainFrame
     */
    public RecipePanel(MainFrame mainFrame) {
        initComponents();
        this.mainFrame = mainFrame;
        this.recipesModel = new DefaultListModel();
        this.modifyRecipeModel = new DefaultListModel();
        this.newRecipeModel = new DefaultListModel();
        this.ingredientList = new DefaultComboBoxModel<>();
        this.newIngredientList = new DefaultComboBoxModel<>();
        lstRecipeList.setModel(recipesModel);
        lstIngredients.setModel(modifyRecipeModel);
        lstAddIngredients.setModel(newRecipeModel);
        jcbIngredients.setModel(ingredientList);
        jcbNewIngredients.setModel(newIngredientList);
        updateIngredientList();
        updateNewIngredientList();
        updateNewRecipeForm();
        updateInformationForm();
        updateRecipeList();
    }
    
    
    private void updateRecipeList() {
        recipesModel.removeAllElements();
        new FoodRepository().getRecipeList().forEach((recipe) -> {
        recipesModel.addElement(recipe.getName());
    });
    }
    
    public void updateIngredientList() {
        ingredientList.removeAllElements();
        new FoodRepository().getIngredientList().forEach((ingredient) -> {
            ingredientList.addElement(ingredient.getName());
        });
    }
    
    public void updateNewIngredientList() {
        newIngredientList.removeAllElements();
        new FoodRepository().getIngredientList().forEach((ingredient) -> {
            newIngredientList.addElement(ingredient.getName());
        });
    }
    
    private void updateNewRecipeForm() {
        txtAddName.setText(null);
        txtAddPreparation.setText(null);
        newRecipeModel.removeAllElements();
    }
    
    private void updateInformationForm() {
        txtName.setText(null);
        txtPreparation.setText(null);
        modifyRecipeModel.removeAllElements();
    }
    
    private void displayRecipe() {
        updateInformationForm();
        int i = lstRecipeList.getSelectedIndex();
        if(!InputValidator.isPointerValid(i)) {
            return;
        }
        Recipe recipe= new FoodRepository().getRecipeList().get(i);
        String name = recipe.getName();
        recipe.getIngredients().forEach(ing -> {modifyRecipeModel.addElement(ing.getName());});
        txtName.setText(name);
        txtPreparation.setText(new FoodRepository().getRecipeList().get(i).getPreparation());
    }
    
    private void processRemoveRecipe() {
        String name = lstRecipeList.getSelectedValue();
        if(name !=null) {
            if(new FoodRepository().removeRecipe(name)) {
                updateRecipeList();
                updateInformationForm();
                return;
            }
            System.out.println("Remove failed, cannot find in repository");
        }
    }
    
    private void processAddRecipe() {
        if(addValidation()) {
            try {
            Object[] ingredientObjects = newRecipeModel.toArray();
            String[] ingredientNames = new String[ingredientObjects.length];
            for(int i =0; i < ingredientObjects.length; i++) {
                ingredientNames[i] = ingredientObjects[i].toString();
            }
            //Possibly NullPointerException right below
            ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) IngredientSearcher.getIngredientListByNames(Arrays.asList(ingredientNames), new FoodRepository().getIngredientList());
            Recipe add = new Recipe(txtAddName.getText(), txtAddPreparation.getText(), ingredients);
            new FoodRepository().addRecipe(add);
            System.out.println("Added");
            updateRecipeList();
            updateNewRecipeForm();
            } catch (NullPointerException e){
                System.out.println("Cannot add, there's an ingredient that cannot be found.");
            }
        } else {
            System.out.println("Not added");
        }
    }
    
    private void processModifyRecipe() {
        if(modifyValidation()) {
            try {
            Object[] ingredientObjects = modifyRecipeModel.toArray();
            String[] ingredientNames = new String[ingredientObjects.length];
            for(int i =0; i < ingredientObjects.length; i++) {
                ingredientNames[i] = ingredientObjects[i].toString();
            }
            ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) IngredientSearcher.getIngredientListByNames(Arrays.asList(ingredientNames), new FoodRepository().getIngredientList());
            Recipe add = new Recipe(txtName.getText(), txtPreparation.getText(), ingredients);
            new FoodRepository().overwriteRecipe(add, lstRecipeList.getSelectedIndex());
            System.out.println("Added");
            updateRecipeList();
            updateNewRecipeForm();
            } catch (NullPointerException e) {
                System.out.println("Cannot add, there's an ingredient that cannot be found.");
            }
        } else {
            System.out.println("Not modified");
        }
    }
    
    private void processAddIngredient() {
        if(!modifyRecipeModel.contains(jcbIngredients.getSelectedItem()) && !new FoodRepository().getIngredientList().isEmpty()) {
            modifyRecipeModel.addElement(jcbIngredients.getSelectedItem());
        }
        System.out.println(jcbIngredients.getSelectedItem());
        System.out.println(jcbNewIngredients.getSelectedItem().getClass());
        System.out.println(jcbIngredients.getSelectedItem().toString());
        
    }
    
    private void processAddNewIngredient() {
        if(!newRecipeModel.contains(jcbNewIngredients.getSelectedItem()) && !new FoodRepository().getIngredientList().isEmpty()){
            newRecipeModel.addElement(jcbNewIngredients.getSelectedItem().toString());
        }
        System.out.println(jcbNewIngredients.getSelectedItem());
        System.out.println(jcbNewIngredients.getSelectedItem().getClass());
        System.out.println(jcbIngredients.getSelectedItem().toString());
        
    }
    
    private void processRemoveIngredient() {
        if(lstIngredients.getSelectedIndex() != -1) {
            modifyRecipeModel.remove(lstIngredients.getSelectedIndex());
        }
    }
    
    private void processRemoveNewIngredient() {
        if(lstAddIngredients.getSelectedIndex() != -1) {
            newRecipeModel.remove(lstAddIngredients.getSelectedIndex());
        }
    }
    
    private boolean addValidation() {
        
        if(!InputValidator.isTextValid(txtAddName.getText())) {
            System.out.println("Invalid name, it cannot be empty or start with empty spaces");
            return false;
        }
        
        if (lstAddIngredients.getModel().getSize() < 1) {
            System.out.println("The recipe needs at least 2 ingredients");
            return false;
        }

        if(!InputValidator.isTextValid(txtAddPreparation.getText())) {
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
        
        if (lstIngredients.getModel().getSize() < 1) {
            System.out.println("The recipe needs at least 2 ingredients");
            return false;
        }

        if(!InputValidator.isTextValid(txtPreparation.getText())) {
            System.out.println("Invalid description, it cannot be empty or start with empty spaces");
            return false;
        }
        
        if(!InputValidator.isPointerValid(lstRecipeList.getSelectedIndex())) {
            System.out.println("Invalid recipe, you must select a recipe to modify");
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
        sepTitle = new javax.swing.JSeparator();
        lblFrameDesc = new javax.swing.JLabel();
        lblIngredientList = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstRecipeList = new javax.swing.JList<>();
        btnRemove = new javax.swing.JButton();
        sep1 = new javax.swing.JSeparator();
        pnlModifyRecipe = new javax.swing.JPanel();
        lblIngredientInfo = new javax.swing.JLabel();
        lblIngredientName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstIngredients = new javax.swing.JList<>();
        lblIngredientName1 = new javax.swing.JLabel();
        btnRemoveIngredient = new javax.swing.JButton();
        jcbIngredients = new javax.swing.JComboBox<>();
        btnAddIngredient = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPreparation = new javax.swing.JTextArea();
        lblIngredientName2 = new javax.swing.JLabel();
        btnModifyRecipe = new javax.swing.JButton();
        sep2 = new javax.swing.JSeparator();
        pnlAddRecipe = new javax.swing.JPanel();
        lblIngredientInfo1 = new javax.swing.JLabel();
        lblIngredientName3 = new javax.swing.JLabel();
        txtAddName = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstAddIngredients = new javax.swing.JList<>();
        lblIngredientName4 = new javax.swing.JLabel();
        btnRemoveNewIngredient = new javax.swing.JButton();
        jcbNewIngredients = new javax.swing.JComboBox<>();
        btnAddNewIngredient = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAddPreparation = new javax.swing.JTextArea();
        lblIngredientName5 = new javax.swing.JLabel();
        btnAddRecipe = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Recipes");

        lblFrameDesc.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblFrameDesc.setText("In this panel you are able to add recipes, modify them or remove them from the database.");

        lblIngredientList.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblIngredientList.setText("Recipe list:");

        lstRecipeList.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lstRecipeList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstRecipeListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstRecipeList);

        btnRemove.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        sep1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblIngredientInfo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblIngredientInfo.setText("Recipe information:");

        lblIngredientName.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientName.setText("Recipe name:");

        txtName.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        lstIngredients.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstIngredients);

        lblIngredientName1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientName1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientName1.setText("Ingredients:");

        btnRemoveIngredient.setText("Remove");
        btnRemoveIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveIngredientActionPerformed(evt);
            }
        });

        jcbIngredients.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        btnAddIngredient.setText("Add");
        btnAddIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddIngredientActionPerformed(evt);
            }
        });

        txtPreparation.setColumns(20);
        txtPreparation.setRows(5);
        jScrollPane3.setViewportView(txtPreparation);

        lblIngredientName2.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientName2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientName2.setText("Preparation:");

        btnModifyRecipe.setText("Modify this existing ingredient");
        btnModifyRecipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyRecipeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlModifyRecipeLayout = new javax.swing.GroupLayout(pnlModifyRecipe);
        pnlModifyRecipe.setLayout(pnlModifyRecipeLayout);
        pnlModifyRecipeLayout.setHorizontalGroup(
            pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModifyRecipeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlModifyRecipeLayout.createSequentialGroup()
                        .addComponent(lblIngredientInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlModifyRecipeLayout.createSequentialGroup()
                        .addGroup(pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblIngredientName1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRemoveIngredient, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAddIngredient, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(lblIngredientName2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbIngredients, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtName)))
                    .addComponent(btnModifyRecipe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlModifyRecipeLayout.setVerticalGroup(
            pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlModifyRecipeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIngredientInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIngredientName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlModifyRecipeLayout.createSequentialGroup()
                        .addComponent(lblIngredientName1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveIngredient)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbIngredients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddIngredient))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlModifyRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIngredientName2))
                .addGap(18, 18, 18)
                .addComponent(btnModifyRecipe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sep2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblIngredientInfo1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        lblIngredientInfo1.setText("Recipe information:");

        lblIngredientName3.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientName3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientName3.setText("Recipe name:");

        txtAddName.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        lstAddIngredients.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(lstAddIngredients);

        lblIngredientName4.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientName4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientName4.setText("Ingredients:");

        btnRemoveNewIngredient.setText("Remove");
        btnRemoveNewIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveNewIngredientActionPerformed(evt);
            }
        });

        jcbNewIngredients.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        btnAddNewIngredient.setText("Add");
        btnAddNewIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewIngredientActionPerformed(evt);
            }
        });

        txtAddPreparation.setColumns(20);
        txtAddPreparation.setRows(5);
        jScrollPane5.setViewportView(txtAddPreparation);

        lblIngredientName5.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        lblIngredientName5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIngredientName5.setText("Preparation:");

        btnAddRecipe.setText("Add new recipe");
        btnAddRecipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRecipeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAddRecipeLayout = new javax.swing.GroupLayout(pnlAddRecipe);
        pnlAddRecipe.setLayout(pnlAddRecipeLayout);
        pnlAddRecipeLayout.setHorizontalGroup(
            pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddRecipeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddRecipeLayout.createSequentialGroup()
                        .addComponent(lblIngredientInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlAddRecipeLayout.createSequentialGroup()
                        .addGroup(pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblIngredientName3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblIngredientName4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRemoveNewIngredient, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAddNewIngredient, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(lblIngredientName5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcbNewIngredients, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAddName)))
                    .addComponent(btnAddRecipe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlAddRecipeLayout.setVerticalGroup(
            pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddRecipeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIngredientInfo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIngredientName3)
                    .addComponent(txtAddName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAddRecipeLayout.createSequentialGroup()
                        .addComponent(lblIngredientName4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveNewIngredient)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbNewIngredients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddNewIngredient))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddRecipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIngredientName5))
                .addGap(18, 18, 18)
                .addComponent(btnAddRecipe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(lblFrameDesc)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIngredientList)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sep1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlModifyRecipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sep2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlAddRecipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIngredientList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                .addGap(27, 27, 27))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnRemove)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(pnlModifyRecipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sep1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sep2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlAddRecipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lstRecipeListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstRecipeListValueChanged
        displayRecipe();
    }//GEN-LAST:event_lstRecipeListValueChanged

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        processRemoveRecipe();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnModifyRecipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyRecipeActionPerformed
        processModifyRecipe();
    }//GEN-LAST:event_btnModifyRecipeActionPerformed

    private void btnAddRecipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRecipeActionPerformed
        processAddRecipe();
    }//GEN-LAST:event_btnAddRecipeActionPerformed

    private void btnAddIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddIngredientActionPerformed
        processAddIngredient();
    }//GEN-LAST:event_btnAddIngredientActionPerformed

    private void btnRemoveIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveIngredientActionPerformed
        processRemoveIngredient();
    }//GEN-LAST:event_btnRemoveIngredientActionPerformed

    private void btnAddNewIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewIngredientActionPerformed
        processAddNewIngredient();
    }//GEN-LAST:event_btnAddNewIngredientActionPerformed

    private void btnRemoveNewIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveNewIngredientActionPerformed
        processRemoveNewIngredient();
    }//GEN-LAST:event_btnRemoveNewIngredientActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddIngredient;
    private javax.swing.JButton btnAddNewIngredient;
    private javax.swing.JButton btnAddRecipe;
    private javax.swing.JButton btnModifyRecipe;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemoveIngredient;
    private javax.swing.JButton btnRemoveNewIngredient;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JComboBox<String> jcbIngredients;
    private javax.swing.JComboBox<String> jcbNewIngredients;
    private javax.swing.JLabel lblFrameDesc;
    private javax.swing.JLabel lblIngredientInfo;
    private javax.swing.JLabel lblIngredientInfo1;
    private javax.swing.JLabel lblIngredientList;
    private javax.swing.JLabel lblIngredientName;
    private javax.swing.JLabel lblIngredientName1;
    private javax.swing.JLabel lblIngredientName2;
    private javax.swing.JLabel lblIngredientName3;
    private javax.swing.JLabel lblIngredientName4;
    private javax.swing.JLabel lblIngredientName5;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList<String> lstAddIngredients;
    private javax.swing.JList<String> lstIngredients;
    private javax.swing.JList<String> lstRecipeList;
    private javax.swing.JPanel pnlAddRecipe;
    private javax.swing.JPanel pnlModifyRecipe;
    private javax.swing.JSeparator sep1;
    private javax.swing.JSeparator sep2;
    private javax.swing.JSeparator sepTitle;
    private javax.swing.JTextField txtAddName;
    private javax.swing.JTextArea txtAddPreparation;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextArea txtPreparation;
    // End of variables declaration//GEN-END:variables
}
