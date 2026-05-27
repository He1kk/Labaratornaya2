package laba;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Scanner;

@Component("provider")
public class CarServiceMessageProvider implements MessageProvider {

    @Value("${app.file.name}")
    private String fileName;

    @PostConstruct
    public void init() {
        System.out.println("Bean CarServiceMessageProvider successfully initialized in: " + LocalDateTime.now());
    }

    @Override
    public String getMessage() {
        String result = "========================================\n";
        result += "           AUTO PARTS FOR SALE          \n";
        result += "========================================\n";

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                return "Error: file " + fileName + " not found!";
            }

            Scanner scanner = new Scanner(is);
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 3) {
                    result += parts[0] + ". " + parts[1] + " | Price: " + parts[2] + "\n";
                }
            }
            scanner.close();
        } catch (Exception e) {
            return "Error file read!";
        }

        result += "========================================\n";
        return result;
    }
}