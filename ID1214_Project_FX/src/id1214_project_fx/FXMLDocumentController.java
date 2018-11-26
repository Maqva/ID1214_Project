/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id1214_project_fx;

import Model.Ingredient;
import Model.PantryFileHandler;
import Model.Recipe;
import Model.RecipeFileHandler;
import Model.RecipeTree;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    @FXML
    private TextArea pantryTextaArea;
    
    private ArrayList<Ingredient> ingredients;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ingredients = new ArrayList();
        recipes = new ArrayList();
        pfh = new PantryFileHandler();
        rfh = new RecipeFileHandler();
        rTree = new RecipeTree();
        try {
            ingredients = pfh.loadPantry();
            recipes = rfh.readFile();
            updatePantryTextArea();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void updatePantry(ActionEvent event) {
        String temp = ingredientField.getText();
        Ingredient tempIng = new Ingredient(temp);
        for (Ingredient i: ingredients)
            if(i.compareTo(tempIng)==0)
                return;
        ingredients.add(tempIng);
        Collections.sort(ingredients);
        updatePantryTextArea();
        try {
            pfh.savePantryToFile(ingredients);
        } catch (IOException ex) {
        }
    }
    
    private void updatePantryTextArea(){
        String output = "";
        for (Ingredient i : ingredients){
            output+=(i.getName()+"\n");
        }
        pantryTextaArea.setText(output);
    }

    @FXML
    private void deletePantryItem(ActionEvent event) {
        String temp = ingredientField.getText();
        Ingredient tempIng = new Ingredient(temp);
        for (Ingredient i: ingredients)
            if(i.compareTo(tempIng)==0){
                ingredients.remove(i);
                updatePantryTextArea();
                try {
                    pfh.savePantryToFile(ingredients);
                } catch (IOException ex) {
                }
                return;
            }
        
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
    
}
