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
package Temp;

import Model.RecipeHTMLReader;
import Model.UrlReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class Main {
    public static void main(String args[]){
        String[] content;
        try {
            content = new UrlReader().getHTMLContent("https://www.ica.se/recept/bananpannkaka-med-chiafron-724233/");
            RecipeHTMLReader test = new RecipeHTMLReader();
            test.getURLIngredients(content);
        } catch (Exception ex) {
            System.err.println("Något gick fel fid öppningen av url.");
        }
    }
}
