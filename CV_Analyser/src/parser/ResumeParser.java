//Student ID- 2370967
package parser;

import model.CV;


// Java Standard Libraries
import java.io.*;                         // File handling
import java.nio.file.Files;              // Read file content
import java.nio.file.Path;               // File path support
import java.util.*;                      // Collections (List, Set, etc.)
import java.util.stream.Collectors;      // Stream operations


// Apache POI - for reading DOCX files
import org.apache.poi.xwpf.usermodel.XWPFDocument;   // .docx reading
import org.apache.poi.xwpf.usermodel.XWPFParagraph;  // .docx paragraphs


// Apache PDFBox - for reading PDF files
import org.apache.pdfbox.pdmodel.PDDocument;         // .pdf document handler
import org.apache.pdfbox.text.PDFTextStripper;       // Extract text from .pdf


//Parses a resume file (TXT, DOCX, or PDF) and extracts known technical skills.
public class ResumeParser {

    //set of recognizable technical skills
    private final Set<String> knownSkills = new HashSet<>(Arrays.asList(
        "java", "python", "html", "css", "sql", "javascript", "spring", "c++"
    ));

    //Parses a resume file to extract skills and returns a CV object.
    public CV parse(String email, String filePath) throws IOException {
        String text = readTextFromFile(filePath);
        Set<String> detectedSkills = new HashSet<>();

        for (String skill : knownSkills) {
            if (text.toLowerCase().contains(skill)) {
                detectedSkills.add(capitalize(skill));
            }
        }

        return new CV(email, new ArrayList<>(detectedSkills));
    }

    //Reads the content from a .txt, .docx, or .pdf file.
    private String readTextFromFile(String path) throws IOException {
        Path file = Path.of(path);
        String lowerPath = path.toLowerCase();

        if (lowerPath.endsWith(".txt")) {
            return Files.readString(file);
        } else if (lowerPath.endsWith(".docx")) {
            try (FileInputStream fis = new FileInputStream(path);
                 XWPFDocument doc = new XWPFDocument(fis)) {
                return doc.getParagraphs().stream()
                          .map(XWPFParagraph::getText)
                          .collect(Collectors.joining("\n"));
            }
        } else if (lowerPath.endsWith(".pdf")) {
            try (PDDocument document = PDDocument.load(new File(path))) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        }

        throw new IOException("Unsupported file type: " + path);
    }


    //Capitalizes the first letter of a skill (e.g., "java" → "Java").
    private String capitalize(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
