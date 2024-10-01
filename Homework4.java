import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Homework4 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        String name = reader.readLine();
        System.out.println("Hello " + name + "!");

    }
}
