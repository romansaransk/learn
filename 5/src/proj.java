import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class proj {
    public static void main(String[] args) throws Exception {
        TreeMap<Character, Integer> hashMap = new TreeMap<Character, Integer>();
        File file = new File("C:/Tolstoy.txt");
        Scanner scanner = new Scanner(file,"utf-8");
        while (scanner.hasNext()) {
            char[] chars = scanner.nextLine().toLowerCase().toCharArray();
            for (Character c : chars) {
                if(!Character.isLetter(c)){
                    continue;
                }
                else if (hashMap.containsKey(c)) {
                    hashMap.put(c, hashMap.get(c) + 1);
                } else {
                    hashMap.put(c, 1);
                }
            }
        }
        for (Map.Entry<Character, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}