
public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid arguments count\n" + "Usage: lab4.exe <homelink>\n");
            return;
        }

        try {
            UrlParser urlParser = new UrlParser(args[0]);
            urlParser.WriteResultsInFiles("all_links.txt", "broken_links.txt");
        } catch (Exception er) {
            System.out.println(er.getMessage());
        }
    }
}
