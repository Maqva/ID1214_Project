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
package Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;

/**
 *
 * @author Magnus
 */
public class RecipeTree {
    private Node root;
    private ArrayList<Recipe> recipesInTree;
    private HashMap<String, Integer> ingredientWeights;
    
    public RecipeTree(){
        root = new Node(new Ingredient("ROOT"));
        ingredientWeights = new HashMap();
    }
    
    public Recipe[] searchRecipesInTree(Ingredient[] ingredients, int errorMargain){
        ArrayList<Recipe> found = new ArrayList();
        recursiveTreeSearch(root, found, ingredients, 0, errorMargain, 0);
        int foundRecipes = found.size();
        if(foundRecipes > 0){
            return found.toArray(new Recipe[foundRecipes]);
        }
        else
            return null;
    }
    
    private void recursiveTreeSearch(Node n, ArrayList<Recipe> recipeList, Ingredient[] ingArray, 
            int ingIndex, int marginError, int matchedIngredientsInBranch){
        for(Node branch : n.getNodeBranches()){
            int weight;
            if(ingIndex < ingArray.length)
                weight = ingArray[ingIndex].compareTo(branch.getNodeIngredient());
            else
                weight = 1;
            if(weight < 0){
                while (weight < 0 && ingIndex < ingArray.length - 1){
                    ingIndex ++;
                    weight = ingArray[ingIndex].compareTo(branch.getNodeIngredient());
                }
            }
            if(weight  == 0){
                if(branch.getNodeRecipe() != null)
                    recipeList.addAll(branch.getNodeRecipe());
                recursiveTreeSearch(branch, recipeList, ingArray, ingIndex + 1, marginError, matchedIngredientsInBranch + 1);   
            }
            else if(marginError > 0 && weight > 0){
                if(branch.getNodeRecipe() != null && matchedIngredientsInBranch > 0)
                    recipeList.addAll(branch.getNodeRecipe());
                recursiveTreeSearch(branch, recipeList, ingArray, ingIndex, marginError - 1, matchedIngredientsInBranch);
            }
            else{
                if(branch.getNodeRecipe() != null && matchedIngredientsInBranch > 0)
                    recipeList.addAll(branch.getNodeRecipe());
                return;
            }
        }
        
    }
    
    public void addRecipe(Recipe r){
        recipesInTree.add(r);
        updateIngredientHashMap(r);
        sortTree();
        Ingredient[] ingredientsToAdd = r.getIngredients();
        ArrayDeque<Ingredient> ingredients = new ArrayDeque();
        ingredients.addAll(Arrays.asList(ingredientsToAdd));
        Node lastNode = addNodeToTree(root, ingredients);
        lastNode.addRecipeToNode(r);
    }
    
    private Node addNodeToTree(Node n, Queue<Ingredient> ingredientQueue){
        Ingredient ing = ingredientQueue.poll();
        if (ing != null){
            ArrayList<Node> branches = n.getNodeBranches();
            int branchSize = branches.size();
            if (branchSize == 0){
                n.addNodeToBranch(ing);
                return addNodeToTree(n.getNodeBranches().get(0), ingredientQueue);
            }
            for (int i = 0; i < branchSize; i++){
                Node temp = branches.get(i);
                int compareValue =  ing.compareTo(temp.getNodeIngredient());
                if ( compareValue == 0)
                    return addNodeToTree(temp, ingredientQueue);
                else if(compareValue < 0){
                    n.addNodeToBranch(ing, i);
                    return addNodeToTree(n.getNodeBranches().get(i), ingredientQueue);
                }
                else if (i+1 == branchSize){
                    n.addNodeToBranch(ing);
                    return addNodeToTree(n.getNodeBranches().get(branchSize), ingredientQueue);
                }
                    
            }
        }
        return n;
    } 

    private void updateIngredientHashMap(Recipe r) {
        for (Ingredient i : r.getIngredients()){
            String name = i.getName();
            Integer temp = ingredientWeights.get(name);
            if (temp != null){
                ingredientWeights.put(name, (int)temp + 1);
            }
            else{
                ingredientWeights.put(name, 0);
            }
        }
    }

    private void sortTree() {
        
    }
    
    private class Node{
        private Ingredient ingredient;
        private ArrayList<Recipe> recipes;
        private ArrayList<Node> branches;
        
        public Node (Ingredient input){
            ingredient = input;
            recipes = new ArrayList();
            branches = new ArrayList();
        }
        
        public void addNodeToBranch (Ingredient ing){
            Node newNode = new Node(ing);
            branches.add(newNode);
        }
        
        /**
         * 
         * @param ing
         * @param index 
         */
        public void addNodeToBranch (Ingredient ing, int index){
            Node newNode = new Node(ing);
            branches.add(index, newNode);
        }
        
        public void addRecipeToNode (Recipe r){
            recipes.add(r);
        }
        
        public Ingredient getNodeIngredient(){
            return this.ingredient;
        }
        
        public ArrayList<Recipe> getNodeRecipe(){
            return recipes;
        }
        
        public ArrayList<Node> getNodeBranches(){
            return branches;
        }
    }
}
