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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Magnus, Patrik.
 */
public class RecipeHTMLReader {
    private final static String RECIPE_NAME_HEADER = "<h1 class=\"recipepage__headline\">(.+)</h1>";
    private final static String INSTRUCTIONS_START = "<div class=\"howto-steps";
    private final static String INSTRUCTION_LINE = "cooking-step__content__instruction\">(.+)</div";
    private final static String INSTRUCTIONS_END = "<h3>";
     private static final String INIT_RECIPE_LIST_PATTERN = "<ul class=\"ingredients__list\">";
    private static final String INGREDIENT_PATTERN = "amount=\"(\\d+[\\.\\d]\\d*)\".+type=\"(.*)\">(?:\\d+ )*(?:\\2 )*(.+)</s";
    private static final String LIST_END_PATTERN = "</ul>";
    
    /**
     * 
     * @param htmlContent
     * @return 
     */
    public Recipe getURLIngredients(String[] htmlContent){
        Pattern listIngredientItemPattern = Pattern.compile(INGREDIENT_PATTERN);
        Pattern instructionPattern = Pattern.compile(INSTRUCTION_LINE);
        Pattern recName = Pattern.compile(RECIPE_NAME_HEADER);
        ArrayList<Ingredient> ingList = new ArrayList();
        String recipeName = "";
        String instructions = "";
        int[] inList = new int[2];
        for(String s : htmlContent){
            if(recipeName.length() == 0 && s.matches(RECIPE_NAME_HEADER)){
                Matcher m = recName.matcher(s);
                m.find();
                recipeName = m.group(1);
            }
            if(inList[0] == 0 && inList[1] == 0){
                if (s.matches(INIT_RECIPE_LIST_PATTERN))
                    inList[0] = 1;
                else if(s.matches(INSTRUCTIONS_START))
                    inList[1] = 1;
            }
            else if (inList[0] == 1){
                Matcher ingredient = listIngredientItemPattern.matcher(s);
                if(ingredient.find()){
                    Ingredient ing = new Ingredient(ingredient.group(3));
                    boolean exists = false;
                    for(Ingredient e : ingList)
                        if(e.compareTo(ing) == 0)
                            exists = true;
                    if(!exists)
                        ingList.add(ing);
                }
                else if (s.matches(LIST_END_PATTERN)){
                    inList[0] = 0;
                }
            }
            else if (inList[1] == 1){
                Matcher instruction = instructionPattern.matcher(s);
                if(instruction.find()){
                    instructions += instruction.group(1);
                }
                else if(s.matches(INSTRUCTIONS_END))
                    inList[1] = 0;
            }
        }
        Recipe output = new Recipe(recipeName, instructions, ingList.toArray(new Ingredient[1]));
        return output;
    }
}
