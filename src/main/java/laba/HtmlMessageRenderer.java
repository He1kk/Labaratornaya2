package laba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.FileWriter;

@Component("renderer")
public class HtmlMessageRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

    @Override
    public void render() {
        if (messageProvider == null) {
            throw new RuntimeException("Error: Provider not installed!");
        }

        String rawText = messageProvider.getMessage();

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n")
                .append("<title>Прайс-лист автосервиса</title>\n")
                .append("<style>\n")
                .append("  table { width: 60%; border-collapse: collapse; margin: 20px 0; font-family: Arial, sans-serif; }\n")
                .append("  th, td { border: 1px solid #dddddd; padding: 8px; text-align: left; }\n")
                .append("  th { background-color: #f2f2f2; }\n")
                .append("</style>\n")
                .append("</head>\n<body>\n")
                .append("  <h2>Список доступных автозапчастей</h2>\n")
                .append("  <table>\n")
                .append("    <tr><th>ID</th><th>Наименование детали</th><th>Стоимость</th></tr>\n");

        String[] lines = rawText.split("\n");
        for (String line : lines) {
            if (line.contains("| Price:")) {
                String[] parts = line.split("\\| Price:");
                String price = parts[1].trim();

                String[] idAndName = parts[0].split("\\. ");
                String id = idAndName[0].trim();
                String name = idAndName[1].trim();

                html.append("    <tr><td>").append(id).append("</td><td>").append(name).append("</td><td>").append(price).append("</td></tr>\n");
            }
        }

        html.append("  </table>\n</body>\n</html>");

        try (FileWriter writer = new FileWriter("table.html")) {
            writer.write(html.toString());
            System.out.println("The result was successfully saved in file: table.html");
        } catch (Exception e) {
            System.out.println("Error save HTML file: " + e.getMessage());
        }
    }

    @Override
    @Autowired
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return this.messageProvider;
    }
}