package compilersproject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import sun.org.mozilla.javascript.internal.ScriptRuntime;

public class CompilersProject {

    public static String[] reservedWords = {"if", "then", "else", "end", "repeat", "until", "read", "write"};
    public static String[] specialSymbols = {"+", "_", "-", "*", "/", "=", "<", ">", "(", ")", ";", ":="};

    public static String checkString(String s) {
        for (int i = 0; i < 8; i++) {
            if (s.equals(reservedWords[i])) {
                return "reservedWords";
            }
        }
        for (int i = 0; i < 12; i++) {
            if (s.equals(specialSymbols[i])) {
                return "specialSymbols";
            }
        }

        return "other";
    }

    public boolean containsSpecialCharacter(String s) {
        return (s == null) ? false : s.matches("[^A-Za-z0-9 ]");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        FileInputStream in = null;
        FileOutputStream out = null;
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

//        System.out.println("Other : ");
//        for (String other1 : content) {
//
//            String result = checkString(other1);
//            switch (result) {
//                case ("reservedWords"): {
//                    reserved_words.add(other1);
//                }
//                break;
//                default:
//                    other.add(other1);
//            }
//
//        }
        boolean character = false;
        boolean digit = false;

        ArrayList<Character> chars = new ArrayList<>();
        ArrayList<Character> digits = new ArrayList<>();

        for (String other1 : content) {
            if (character) {
                StringBuilder result = new StringBuilder(chars.size());
                for (Character c : chars) {
                    result.append(c);
                }
                String output = result.toString();
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
                digits.clear();
                digit = false;
            }
//            System.out.println(other1);

            /*aRRAY LIST FOR THE IDENTIFIER   INCREASES STOPS WHEN OPERATOR COMES ADD OP THEN NEW IDENTIFIER */
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
                        chars.clear();
                        character = false;
                    } else if (digit) {
                        StringBuilder result = new StringBuilder(digits.size());
                        for (Character c : digits) {
                            result.append(c);
                        }
                        String output = result.toString();
                        number.add(output);
                        digits.clear();
                        digit = false;
                    }

                    special_symbols.add(temp + "");
                }

            }

        }

//        System.out.println("Reserved Words are :");
//        
//        for (String other1 : reserved_words) {
//            System.out.println(other1);
//        }
        try {
            PrintWriter writer = new PrintWriter("scanner_output.txt", "UTF-8");
            for (String other1 : special_symbols) {
                writer.println(other1 + " -> Special Symbol");
            }

            for (String other1 : identifier) {

                boolean res = false;
                for (i = 0; i < 8; i++) {
                    if (other1.equals(reservedWords[i])) {
                        res = true;
                    }
                }
                if (res) {
                    writer.println(other1 + " -> Reserved Word ");
                } else {
                    writer.println(other1 + " -> Identifier ");
                }
            }

            for (String other1 : number) {
                writer.println(other1 + " -> number");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Cann't open new file");
        }

    }

}
