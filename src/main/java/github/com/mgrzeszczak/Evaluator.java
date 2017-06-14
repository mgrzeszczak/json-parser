package github.com.mgrzeszczak;

import github.com.mgrzeszczak.json.JsonNode;
import github.com.mgrzeszczak.lexical.Tokenizer;
import github.com.mgrzeszczak.structural.Parser;

import java.util.Scanner;

public class Evaluator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                Tokenizer tokenizer = Tokenizer.of(input);
                Parser parser = Parser.of(tokenizer);
                JsonNode parse = parser.parse();
                System.out.println(parse.getValue());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
