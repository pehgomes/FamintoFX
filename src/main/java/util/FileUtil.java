package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtil {
    public static String readFile(String fileName){
        String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return content;
    }
    public static void writeFile(String fileName, String content){
        try {
        	if(existsFile(fileName)) 
        		Files.deleteIfExists(Paths.get(fileName));
        	Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static boolean existsFile(String fileName){
        boolean existe = true;
		try {
			Files.readAllBytes(Paths.get(fileName));
		} catch (IOException e) { existe = false; }
        return existe;
    }
}
