import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import entities.Product;

public class App {
    public static void main(String[] args) throws Exception {
        
        Locale.setDefault(Locale.US);

        Path currentDirectory = Paths.get(System.getProperty("user.dir"));
        Path filePath = currentDirectory.resolve("produtos.csv");
        String path = filePath.toString();

        // File path = new File("D:\\Estudos\\java\\exercicio-stream\\produtos.csv");

        try (BufferedReader br = new BufferedReader( new FileReader(path))) {

            List<Product> list = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }
            double avg = list.stream()
                .map(p -> p.getPrice())
                .reduce(0.0, (x,y) -> x + y) / list.size();

            System.out.println("Average Price: " + String.format("%.2f", avg));

            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> nameList = list.stream()
                .filter(p -> p.getPrice() < avg)
                .map(p -> p.getName())
                .sorted(comp.reversed())
                .collect(Collectors.toList());

            nameList.forEach(p -> System.out.println(p));
                    
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
