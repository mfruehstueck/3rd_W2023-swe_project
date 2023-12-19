package at.onlyquiz.util.csvParser;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static at.onlyquiz.util.Configuration.DEFAULT_CSV_SEPERATOR;

public class CSV_Reader extends CSV_Parser {
    private final CSVParser csvParser;
    private final CSVReader csvReader;

    //NSEC: Singleton
    private static CSV_Reader __instance__;
    public static CSV_Reader get_instance(URL pathToFile) throws FileNotFoundException { return (__instance__ != null) ? __instance__ : new CSV_Reader(pathToFile); }
    private CSV_Reader(URL pathToFile) throws FileNotFoundException {
        super(pathToFile);

        this.csvParser = new CSVParserBuilder()
                .withSeparator(DEFAULT_CSV_SEPERATOR)
                .withIgnoreQuotations(true)
                .build();

        this.csvReader = new CSVReaderBuilder(new FileReader(super.get_pathToCSV().getFile()))
                .withSkipLines(1) //skips this number of lines
                .withCSVParser(csvParser)
                .build();
    }
    public static void close() { __instance__ = null; }

    public List<String[]> read() throws IOException, CsvValidationException {
        List<String[]> lines = new ArrayList<>();
        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null) { lines.add(nextLine); }

        return lines;
    }

    public <T> List<T> read_mrBean(Class<? extends T> T) throws CsvValidationException, IOException {
        CsvToBean<T> csv = new CsvToBean<>();
        csv.setCsvReader(this.csvReader);
        csv.setMappingStrategy(get_mappingStrategy_for(T));

        return csv.parse();
    }

    private <T> ColumnPositionMappingStrategy<T> get_mappingStrategy_for(Class<? extends T> T) {
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(T);
        String[] columns = new String[]{"1", "2"};
        strategy.setColumnMapping(columns);
        return strategy;
    }

    public List<Integer> get_lineIDs() throws CsvValidationException, IOException {
        List<Integer> lineIDs = new ArrayList<>();

        for (String[] line : this.read()) {
            lineIDs.add(Integer.parseInt(line[0]));
        }

        return lineIDs;
    }

    public String[] get_line_withID(int lineID) throws CsvValidationException, IOException { return this.read().get(lineID); }
}
