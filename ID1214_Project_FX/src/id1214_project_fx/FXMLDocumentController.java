/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1214_project_fx;

import Model.Ingredient;
import Model.Pantry;
import Model.PantryFileHandler;
import Model.Recipe;
import Model.RecipeFileHandler;
import Model.RecipeTree;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Magnus
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private TextField ingredientField;
    @FXML
    private Button addToPntryBttn;
    private TextArea pantryTextaArea;
    
    private Pantry pantry;
    private ArrayList<Recipe> recipes;
    private PantryFileHandler pfh;
    private RecipeFileHandler rfh;
    private RecipeTree rTree;
    @FXML
    private Button pntryDelete;
    @FXML
    private Button URLButton;
    @FXML
    private TextField URLField;
    @FXML
    private TextArea recipeInstructionField;
    @FXML
    private TextArea recipeIngredientField;
    @FXML
    private TextField recipeNameField;
    @FXML
    private Button recipeSaveButton;
    @FXML
    private TextField errorMargainField;
    @FXML
    private Button recipeSearchButton;
    private TextArea foundRecipeArea;
    private VBox testBox;
    @FXML
    private VBox pantryBox;
    private TextFlow selectedRecipeFLow;
    @FXML
    private VBox foundRecipesBox;
    @FXML
    private TextFlow selectedRecipeFlow;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pantry = new Pantry();
        recipes = new ArrayList();
        pfh = new PantryFileHandler();
        rfh = new RecipeFileHandler();
        rTree = new RecipeTree();
        ObservableList testFlow = selectedRecipeFlow.getChildren();
        
        Text testText = new Text("massa testmassa testmassa testmassa testmassa testmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\nmassa test\n");
        testFlow.add(testText);
        try {
            ArrayList<Ingredient> tempIng = pfh.loadPantry();
            if (tempIng != null)
                for(Ingredient i: tempIng)
                    pantry.addIngredient(i);
            recipes = rfh.readFile();
            for(Recipe r : recipes){
                rTree.addRecipe(r);
            }
            updatePantryBox();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void updatePantry(ActionEvent event) {
        String temp = ingredientField.getText();
        Ingredient tempIng = new Ingredient(temp);
        for (Ingredient i: pantry.getPantryIngredients())
            if(i.compareTo(tempIng)==0)
                return;
        pantry.addIngredient(tempIng);
        updatePantryBox();
        ingredientField.setText("");
        try {
            pfh.savePantryToFile(pantry.getPantryIngredients());
        } catch (IOException ex) {
        }
    }
    
    private void updatePantryBox(){
        ObservableList temp = pantryBox.getChildren();
        temp.clear();
        for(Ingredient i : pantry.getPantryIngredients())
            temp.add(new SelectablePane(i.getName()));
        //pantryTextaArea.setText(output);
    }

    @FXML
    private void deletePantryItem(ActionEvent event) {
        ArrayList<Ingredient> toBeDeleted = new ArrayList();
        ArrayList<Ingredient> inPantry = pantry.getPantryIngredients();
        ObservableList<Node> pantryList= pantryBox.getChildren();
        for(int i = 0; i < pantryList.size(); i ++){
            SelectablePane sP = (SelectablePane) pantryList.get(i);
            if (sP.isSelected())
                toBeDeleted.add(inPantry.get(i));
        }
        pantry.removeIngredients(toBeDeleted);
        updatePantryBox();
        try {
            pfh.savePantryToFile(pantry.getPantryIngredients());
        } catch (IOException ex) {
        }
        return;
        }

    @FXML
    private void getRecipeFromURL(ActionEvent event) {
    }

    @FXML
    private void saveRecipe(ActionEvent event) {
        String name = recipeNameField.getText();
        String instructions = recipeInstructionField.getText();
        String allIngredients = recipeIngredientField.getText();
        String[] recipeIngredients = allIngredients.split("\n");
        Ingredient[] tempIng = new Ingredient[recipeIngredients.length];
        for(int i = 0; i < tempIng.length; i++)
            tempIng[i] = new Ingredient(recipeIngredients[i]);
        Recipe newRec = new Recipe(name, instructions, tempIng);
        recipes.add(newRec);
        try {
            rfh.saveRecipeToFile(recipes);
        } catch (IOException ex) {
        }
    }

    @FXML
    /**
     * doesn't catch exceptions caused by user inputting NAN in errorMargainField
     */
    private void searchRecipes(ActionEvent event) {
        Ingredient[] ingredientsInPantry = pantry.getPantryIngredients().toArray(new Ingredient[1]);
        int errorMargain = Integer.parseInt(errorMargainField.getText());
        Recipe[] result = rTree.searchRecipesInTree(ingredientsInPantry, errorMargain);
        ObservableList temp = foundRecipesBox.getChildren();
        temp.clear();
        if(result != null){
            for (Recipe r : result)
                if(r != null)
                    temp.add(new ToggleableRecipePane(r));
        }
    }
    
    private class SelectablePane extends Pane{
        private boolean isSelected;
        private Label text;
        
        public SelectablePane (String input){
            text = new Label();
            text.setText(input);
            this.getChildren().add(text);
            this.isSelected = false;
            setStyle();
            this.setMaxWidth(250);
            this.setMinHeight(25);
        }
        
        public boolean isSelected(){
            return isSelected;
        }
        
        public String getLabel(){
            return label.getText();
        }
        
        private void setStyle(){
            this.setStyle("-fx-border-color: LIGHTGREY; -fx-background-color: #F2F2F2");
            this.setOnMouseClicked((MouseEvent event)-> {
                this.isSelected = !this.isSelected;
                String style = (this.isSelected)?"-fx-background-color: #D0F5A9; -fx-border-color: RED":"-fx-background-color: #F2F2F2;-fx-border-color: LIGHTGREY";
                this.setStyle(style);
            });
            this.setOnMouseEntered((MouseEvent event) -> {
                String style = (this.isSelected)?"-fx-background-color: #D0F5A9;-fx-border-color: GREEN":"-fx-background-color: #F2F2F2;-fx-border-color: BLACK";
                this.setStyle(style);
            });
            this.setOnMouseExited((MouseEvent me) -> {
                String style = (this.isSelected)?"-fx-background-color: #D0F5A9; -fx-border-color: RED":"-fx-background-color: #F2F2F2; -fx-border-color: LIGHTGREY";
                this.setStyle(style);
            });
        }
    }
    
    private class ToggleableRecipePane extends Pane{
        private boolean isSelected;
        private Label text;
        private Recipe r;
        
        public ToggleableRecipePane (Recipe input){
            text = new Label();
            r = input;
            text.setText(input.getName());
            this.getChildren().add(text);
            this.isSelected = false;
            setStyle();
            this.setMaxWidth(250);
            this.setMinHeight(25);
        }
        
        public void unSet(){
            isSelected = false;
            setStyle("-fx-background-color: #F2F2F2; -fx-border-color: LIGHTGREY");
        }
        
        public boolean isSelected(){
            return isSelected;
        }
        
        public String getLabel(){
            return label.getText();
        }
        
        private void setStyle(){
            this.setStyle("-fx-border-color: LIGHTGREY; -fx-background-color: #F2F2F2");
            this.setOnMouseClicked((MouseEvent event)-> {
                clearSiblings();
                this.isSelected = true;
                updateTextFlow();
                String style = (this.isSelected)?"-fx-background-color: #D0F5A9; -fx-border-color: RED":"-fx-background-color: #F2F2F2;-fx-border-color: LIGHTGREY";
                this.setStyle(style);
            });
            this.setOnMouseEntered((MouseEvent event) -> {
                String style = (this.isSelected)?"-fx-background-color: #D0F5A9;-fx-border-color: GREEN":"-fx-background-color: #F2F2F2;-fx-border-color: BLACK";
                this.setStyle(style);
            });
            this.setOnMouseExited((MouseEvent me) -> {
                String style = (this.isSelected)?"-fx-background-color: #D0F5A9; -fx-border-color: RED":"-fx-background-color: #F2F2F2; -fx-border-color: LIGHTGREY";
                this.setStyle(style);
            });
        }

        private void updateTextFlow() {
            superEmptyTextFlow();
            ObservableList temp = selectedRecipeFlow.getChildren();
            String toAdd = "";
            toAdd += r.getInstructions()+"\n";
            for(Ingredient s : r.getIngredients())
                toAdd += "---"+s.getName()+"\n";
            temp.add(new Text(toAdd));
        }
        
        private void superEmptyTextFlow(){
            selectedRecipeFlow.getChildren().clear();
        }

        private void clearSiblings() {
            for(Node n : foundRecipesBox.getChildren()){
                ToggleableRecipePane trp = (ToggleableRecipePane) n;
                trp.unSet();
            }
        }
        
    }
}
