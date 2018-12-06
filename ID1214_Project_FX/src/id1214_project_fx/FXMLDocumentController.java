/*
  * Copyright (C) 2018 Magnus Qvarnstrom, Patrik Karlsten
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package id1214_project_fx;

import Model.Ingredient;
import Model.Pantry;
import Model.PantryFileHandler;
import Model.Recipe;
import Model.RecipeFileHandler;
import Model.RecipeHTMLReader;
import Model.RecipeTree;
import Model.UrlReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The controller class for the <code>JavaFX</code> view.
 * @author Magnus, Patrik
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField ingredientField;
    @FXML
    private TextField URLField;
    @FXML
    private TextArea recipeInstructionField;
    @FXML
    private TextArea recipeIngredientField;
    @FXML
    private TextField recipeNameField;
    @FXML
    private TextField errorMargainField;
    @FXML
    private VBox pantryBox;
    @FXML
    private VBox foundRecipesBox;
    @FXML
    private TextFlow selectedRecipeFlow;
    private Pantry pantry;
    private ArrayList<Recipe> recipes;
    private PantryFileHandler pfh;
    private RecipeFileHandler rfh;
    private RecipeTree rTree;
    private RecipeHTMLReader htmlR;
    private UrlReader urlR;
    
    /**
     * <code>initialize()</code> is run once at startup, all initialisations not performed by <code>JavaFX</code> are done here.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pantry = new Pantry();
        recipes = new ArrayList();
        pfh = new PantryFileHandler();
        rfh = new RecipeFileHandler();
        rTree = new RecipeTree();
        urlR = new UrlReader();
        htmlR = new RecipeHTMLReader();
        try {
            ArrayList<Ingredient> tempIng = pfh.loadPantry();
            if (tempIng != null)
                for(Ingredient i: tempIng)
                    pantry.addIngredient(i);
            recipes = rfh.readFile();
            if (recipes != null)
                for(Recipe r : recipes)
                    rTree.addRecipe(r);
            updatePantryBox();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    /**
     * Creates a new <code>Ingredient</code> object based off of the user input in <code>ingredientField</code> if any.
     * Then adds those to the <code>pantry</code> and updates <code>pantryBox</code>
     */
    @FXML
    private void updatePantry(ActionEvent event) {
        String temp = ingredientField.getText();
        if(temp.length() > 0){
            Ingredient tempIng = new Ingredient(temp);
            for (Ingredient i: pantry.getPantryIngredients())
                if(i.compareTo(tempIng)==0)
                    return;
            pantry.addIngredient(tempIng);
            updatePantryBox();
            ingredientField.setText("");
        }
    }

    /**
     * Iterates through all the <code>SelectablePane</code> instances in <code>pantryBox</code> and removes their <code>Ingredient</code> objects from <code>pantry</code>.
     */
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
    }

    /**
     * Attempts to create a new <code>Recipe</code> object from a ICA-Recept URL.
     * If successful, the object is added to runtime and saved to file.
     */
    @FXML
    private void getRecipeFromURL(ActionEvent event) {
        String url = URLField.getText();
        URLField.clear();
        String[] html;
        try {
            html = urlR.getHTMLContent(url);
            Recipe toAdd = htmlR.getURLIngredients(html);
            if(toAdd != null){
                saveRecipe(toAdd);
            }
        } catch (Exception ex) {
            System.err.println("Något gick fel!");
        }
    }

    /**
     * Creates a new <code>Recipe</code> object based off of the user's input in the "Recipe" tab.
     * If the object is successfully created it is saved to the runtime, and to file.
     */
    @FXML
    private void getRecipeFromUser(ActionEvent event) {
        String name = recipeNameField.getText();
        String instructions = recipeInstructionField.getText();
        String allIngredients = recipeIngredientField.getText();
        if(name.length() == 0||instructions.length() == 0 || allIngredients.length()==0)
            return;
        recipeNameField.clear();
        recipeIngredientField.clear();
        recipeInstructionField.clear();
        String[] recipeIngredients = allIngredients.split("\n");
        Ingredient[] tempIng = new Ingredient[recipeIngredients.length];
        for(int i = 0; i < tempIng.length; i++)
            tempIng[i] = new Ingredient(recipeIngredients[i]);
        Recipe newRec = new Recipe(name, instructions, tempIng);
        saveRecipe(newRec);
    }
    
    /**
     * Adds a <code>Recipe</code> object to <code>rTree</code> and <code>recipes</code>.
     * Then tries to save the updated list to file.
     * @param r the Recipe object to be added.
     */
    private void saveRecipe(Recipe r){
        rTree.addRecipe(r);
        recipes.add(r);
        try {
            rfh.saveRecipeToFile(recipes);
        } catch (IOException ex) {
            System.err.println("Couldn't save recipes to file.");
        }
    }

    /**
     * Searches <code>rTree</code> for all recipes that can be found, given the ingredients in <code>pantry</code> and the <code>errorMargain</code>.
     */
    @FXML
    private void searchRecipes(ActionEvent event) {
        Ingredient[] ingredientsInPantry = pantry.getPantryIngredients().toArray(new Ingredient[1]);
        try{
            int errorMargain = Integer.parseInt(errorMargainField.getText());
            Recipe[] result = rTree.searchRecipesInTree(ingredientsInPantry, errorMargain);
            ObservableList temp = foundRecipesBox.getChildren();
            temp.clear();
            if(result != null)
                for (Recipe r : result)
                    if(r != null)
                        temp.add(new ToggleableRecipePane(r));
        }
        catch (NumberFormatException ex){
             System.err.println("User didn't write a number!");
        }
    }

    /**
     * Deletes all existing <code>Ingredient</code> objects in <code>pantry</code> that match the currently selected <code>Recipe</code> object in <code>foundRecipesBox</code>.
     */
    @FXML
    private void emptyPantry(ActionEvent event) {
        ToggleableRecipePane trp =getSelectedRecipePane();
        Recipe toMake = (trp != null)?trp.getRecipe():null;
        if(toMake != null){
            Ingredient[] ingredientsToRemove = toMake.getIngredients();
            ArrayList<Ingredient> toRemove = new ArrayList();
            toRemove.addAll(Arrays.asList(ingredientsToRemove));
            pantry.removeIngredients(toRemove);
            updatePantryBox();
            clearSelectedRecipeBox();
            updateRecipeTextFlow();
        }
    }
    
    /**
     * Updates <code>pantryBox</code> to correctly display all existing <code>Ingredient</code> objects currently stored in <code>pantry</code>.
     * After it's been written to display, an attempt is made to write the current <code>pantry</code> to file.
     */
    private void updatePantryBox(){
        ObservableList temp = pantryBox.getChildren();
        temp.clear();
        for(Ingredient i : pantry.getPantryIngredients())
            temp.add(new SelectablePane(i.getName()));
        try {
            pfh.savePantryToFile(pantry.getPantryIngredients());
        } catch (IOException ex) {
            System.err.println("Kunde inte spara pantry till fil.");
        }
    }
    
    /**
     * Iterates through the children of <code>foundRecipesBox</code> to find the
     * <code>ToggleableRecipePane</code> that's currently selected.
     * @return The <code>ToggleableRecipePane</code> the user's toggled to in <code>foundRecipesBox</code>.
     */
    private ToggleableRecipePane getSelectedRecipePane(){
        ObservableList<Node> temp = foundRecipesBox.getChildren();
        for(Node n : temp){
            ToggleableRecipePane recipePane = (ToggleableRecipePane) n;
            if (recipePane.isSelected())
                return recipePane;
        }
        return null;
    }
    
    /**
     * Iterates through all <code>ToggleableRecipePane</code> instances in the child list of <code>foundRecipesBox</code> and calls their <code>unset()</code> method.
     */
    private void clearSelectedRecipeBox() {
            ToggleableRecipePane selected = getSelectedRecipePane();
            if(selected != null)
                selected.unSet();
        }

    /**
     * Writes the content of the <code>Recipe</code> instance currently highlighted in <code>foundRecipesBox</code>
     * into <code>selectedRecipeFlow</code>.
     */
    private void updateRecipeTextFlow() {
        selectedRecipeFlow.getChildren().clear();
        ArrayList<Ingredient> ingredientsInPantry = pantry.getPantryIngredients();
        ObservableList temp = selectedRecipeFlow.getChildren();
        ToggleableRecipePane trn = getSelectedRecipePane();
        if(trn != null){
            Recipe r = trn.getRecipe();
            if(r != null){
                temp.add(new Text(r.getInstructions()+"\n"));
                for(Ingredient ing : r.getIngredients()){
                    boolean inPantry = false;
                    for(Ingredient pIng : pantry.getPantryIngredients()){
                        if(ing.compareTo(pIng)==0)
                            inPantry = true;
                    }
                    Text t = new Text("---"+ing.getName()+"\n");
                    if(inPantry)
                        t.setFill(Color.GREEN);
                    else
                        t.setFill(Color.RED);
                    temp.add(t);
                }
            }
        }
    }
    
    /**
    * A custom-made <code>JavaFX</code> component, a subclass of Pane to be displayed in the <code>foundRecipesBox</code>, that can be toggled on or off.
    * Contains a Instance of a <code>Recipe</code> object, based off of which recipes where found during a search.
    * Also contains a <code>Label</code> with a name associated with the <code>Recipe</code> instance name.
    */
    private class ToggleableRecipePane extends Pane{
        private boolean isSelected;
        private Label text;
        private Recipe r;
        
        
        /**
         * Instantiates a new ToggleableRecipePane.
         * @param inputRecipe The recipe to be stored in this instance.
         */
        public ToggleableRecipePane (Recipe inputRecipe){
            text = new Label();
            r = inputRecipe;
            text.setText(inputRecipe.getName());
            getChildren().add(text);
            isSelected = false;
            initializeStyleAndEvents();
            setMaxWidth(250);
            setMinHeight(25);
        }
        
        /**
         * Sets this instance <code>isSelected</code> to <code>false</code>.
         */
        public void unSet(){
            isSelected = false;
            setStyle("-fx-background-color: #F2F2F2; -fx-border-color: LIGHTGREY");
        }
        
        /**
         * Getter of the ToggleablerecipePane's <code>isSelected</code> variable.
         * @return the value of this instance <code>isSelected</code>.
         */
        public boolean isSelected(){
            return isSelected;
        }
        
        /**
         * Call for the content of the instance label.
         * @return String containing the content of the child <code>Label</code>.
         */
        public String getLabel(){
            return text.getText();
        }
        
        /**
         * Style-and Behaviour setter to be called at initialisation of ToggleableRecipePane instance.
         * Defines styles and mouse-related events that this pane should listen for to dynamically change its appearance.
         */
        private void initializeStyleAndEvents(){
            setStyle("-fx-border-color: LIGHTGREY; -fx-background-color: #F2F2F2");
            setOnMouseClicked((MouseEvent event)-> {
                clearSelectedRecipeBox();
                isSelected = true;
                updateRecipeTextFlow();
                String style = (isSelected)?"-fx-background-color: #D0F5A9; -fx-border-color: RED":"-fx-background-color: #F2F2F2;-fx-border-color: LIGHTGREY";
                setStyle(style);
            });
            setOnMouseEntered((MouseEvent event) -> {
                String style = (isSelected)?"-fx-background-color: #D0F5A9;-fx-border-color: GREEN":"-fx-background-color: #F2F2F2;-fx-border-color: BLACK";
                setStyle(style);
            });
            setOnMouseExited((MouseEvent me) -> {
                String style = (isSelected)?"-fx-background-color: #D0F5A9; -fx-border-color: RED":"-fx-background-color: #F2F2F2; -fx-border-color: LIGHTGREY";
                setStyle(style);
            });
        }
        
        /**
         * Returns the <code>Recipe</code> instance stored in this pane.
         * @return instance of Recipe bound to pane.
         */
        public Recipe getRecipe() {
            return r;
        }
    }
}
