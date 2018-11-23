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
import java.util.Queue;

/**
 *
 * @author Magnus
 */
public class RecipeTree {
    private Node root;
    
    public RecipeTree(){
        root = new Node(new Ingredient("ROOT"));
    }
    
    public Recipe[] searchRecipesInTree(Ingredient[] ingredients, int errorMargain){
        return null;
    }
    
    public void addRecipe(Recipe r){
        Ingredient[] ingredientsToAdd = r.getIngredients();
        ArrayDeque<Ingredient> ingredients = new ArrayDeque();
        for(Ingredient i : ingredientsToAdd)
            ingredients.add(i);
        Node lastNode = addNodeToTree(root, ingredients);
        lastNode.addRecipeToNode(r);
    }
    
    private void clearTree(){
        this.root = new Node(new Ingredient("Root"));
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
