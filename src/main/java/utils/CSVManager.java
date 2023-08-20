package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class CSVManager {
	public void createCSV(String dir) {
        try {
            File file = new File(dir);
            file.createNewFile();
            System.out.println("Archivo creado: " + dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public boolean validateFileExist(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        return archivo.exists();
    }
	

	
	public List<String> getColumnFromCSV(String csvDir, int columnIndex) {
        List<String> columnValues = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvDir));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                if (columnIndex >= 0 && columnIndex < values.length) {
                    columnValues.add(values[columnIndex]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return columnValues;
    }

    public void addLine(String csvDir, String line) {
    	if (validateFileExist(csvDir)) {
    		try {
                FileWriter fileWriter = new FileWriter(csvDir, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(line);
                bufferedWriter.newLine();

                bufferedWriter.close();
                System.out.println("Línea agregada al archivo: " + line);
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
    	else {
    		createCSV(csvDir);
    		try {
                FileWriter fileWriter = new FileWriter(csvDir, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(line);
                bufferedWriter.newLine();

                bufferedWriter.close();
                System.out.println("Línea agregada al archivo: " + line);
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
    }

    public void deleteFile(String dir) {
        File file = new File(dir);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo eliminado: " + dir);
            } else {
                System.out.println("Error al eliminar el archivo: " + dir);
            }
        } else {
            System.out.println("El archivo no existe: " + dir);
        }
    }

    public void deleteLineToCSV(String dir, int line) {
        try {
            List<String> lines = Files.readAllLines(Path.of(dir), StandardCharsets.UTF_8);
            if (line >= 1 && line <= lines.size()) {
                lines.remove(line - 1);
                Files.write(Path.of(dir), lines, StandardCharsets.UTF_8);
                System.out.println("Línea eliminada del archivo: " + line);
            } else {
                System.out.println("El número de línea es inválido: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLastLine(String dir) {
        try {
            List<String> lines = Files.readAllLines(Path.of(dir), StandardCharsets.UTF_8);
            if (!lines.isEmpty()) {
                lines.remove(lines.size() - 1);
                Files.write(Path.of(dir), lines, StandardCharsets.UTF_8);
                System.out.println("Última línea eliminada del archivo.");
            } else {
                System.out.println("El archivo está vacío.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
