package compilersproject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import sun.org.mozilla.javascript.internal.ScriptRuntime;

public class CompilersProject {

    public static String[] reservedWords = {"if", "then", "else", "end", "repeat", "until", "read", "write"};
    public static String[] specialSymbols = {"+", "_", "-", "*", "/", "=", "<", ">", "(", ")", ";", ":="};


    public static void main(String[] args) throws FileNotFoundException, IOException {

        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> reserved_words = new ArrayList<>();
        ArrayList<String> special_symbols = new ArrayList<>();
        ArrayList<String> content = new ArrayList<>();
        ArrayList<String> other = new ArrayList<>();
        ArrayList<String> number = new ArrayList<>();
        ArrayList<String> identifier = new ArrayList<>();
        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("tiny_sample_code.txt")));
            while (s.hasNext()) {
                String inputString = s.next();
                data.add(inputString);
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }

        int i = 0;
        boolean comment = false;
        for (i = 0; i < data.size(); i++) {

            String current = data.get(i);
            String rest;

            if (current.startsWith("{")) {
                comment = true;
            } else if (current.contains("{") && !(current.startsWith("{"))) {
                comment = true;
                rest = current.split("\\{")[0];
                current = rest;
                
                content.add(current);
            }

            if (current.endsWith("}")) {
                comment = false;
                continue;
            }

            if (current.contains("}")) {
                comment = false;
                rest = current.substring(data.get(i).indexOf("}") + 1);
                current = rest;
            }

            
            
            if (!comment) {

                content.add(current);

            }

        }

        boolean character = false;
        boolean digit = false;

        ArrayList<Character> chars = new ArrayList<>();
        ArrayList<Character> digits = new ArrayList<>();

        try {

            try (PrintWriter writer = new PrintWriter("scanner_output.txt", "UTF-8")) {
                for (String other1 : content) {
                    if (character) {
                        StringBuilder result = new StringBuilder(chars.size());
                        for (Character c : chars) {
                            result.append(c);
                        }
                        String output = result.toString();
                        boolean res = false;
                        for (i = 0; i < 8; i++) {
                            if (output.equals(reservedWords[i])) {
                                res = true;
                            }
                        }
                        if (res) {
                            writer.println(output + "      -> Reserved Word ");
                        } else {
                            writer.println(output + "      -> Identifier ");
                        }
                        identifier.add(output);
                        chars.clear();
                        character = false;
                    }
                    
                    if (digit) {
                        StringBuilder result = new StringBuilder(digits.size());
                        for (Character c : digits) {
                            result.append(c);
                        }
                        String output = result.toString();
                        number.add(output);
                        writer.println(output + "         -> number");
                        digits.clear();
                        digit = false;
                    }
                    
                    for (int j = 0; j < other1.length(); j++) {
                        char temp = other1.charAt(j);
                        if (temp == ' ') {
                            continue;
                        }
                        boolean isLetter = Character.isLetter(temp);
                        boolean isDigit = Character.isDigit(temp);
                        if (isLetter) {
                            chars.add(temp);
                            character = true;
                        } else if (isDigit) {
                            character = false;
                            digit = true;
                            digits.add(temp);
                        } else {
                            if (character) {
                                StringBuilder result = new StringBuilder(chars.size());
                                for (Character c : chars) {
                                    result.append(c);
                                }
                                String output = result.toString();
                                identifier.add(output);
                                boolean res = false;
                                for (i = 0; i < 8; i++) {
                                    if (output.equals(reservedWords[i])) {
                                        res = true;
                                    }
                                }
                                if (res) {
                                    writer.println(output + "        -> Reserved Word ");
                                } else {
                                    writer.println(output + "        -> Identifier ");
                                }
                                chars.clear();
                                character = false;
                            } else if (digit) {
                                StringBuilder result = new StringBuilder(digits.size());
                                for (Character c : digits) {
                                    result.append(c);
                                }
                                String output = result.toString();
                                number.add(output);
                                writer.println(output + "         -> number");
                                digits.clear();
                                digit = false;
                            }
                            
                            special_symbols.add(temp + "");
                            writer.println(temp + "         -> Special Symbol");
                            
                        }

                    }

                }
                   if (character) {
                        StringBuilder result = new StringBuilder(chars.size());
                        for (Character c : chars) {
                            result.append(c);
                        }
                        String output = result.toString();
                        boolean res = false;
                        for (i = 0; i < 8; i++) {
                            if (output.equals(reservedWords[i])) {
                                res = true;
                            }
                        }
                        if (res) {
                            writer.println(output + " -> Reserved Word ");
                        } else {
                            writer.println(output + " -> Identifier ");
                        }
                        identifier.add(output);
                        chars.clear();
                        character = false;
                    }
                    
                    if (digit) {
                        StringBuilder result = new StringBuilder(digits.size());
                        for (Character c : digits) {
                            result.append(c);
                        }
                        String output = result.toString();
                        number.add(output);
                        writer.println(output + " -> number");
                        digits.clear();
                        digit = false;
                    }
                        writer.close();
                    
            }

        } catch (Exception e) {

        }
  
    }

}
