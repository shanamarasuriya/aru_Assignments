//Student ID- 2370967
ReadMe.txt

---

Student ID (SID)- 2370967
Project Name - Smart CV Analyser (NexiGen)
Module-  MOD003484 - Software Principles
Submission- 010-2 Assignment (Individual Coursework)

---

How to Run the Program:

---

Prerequisites:
- JDK 22.0.1 (or any compatible Java version) installed.
- Apache POI library for reading `.docx` files.
- Apache PDFBox library for reading `.pdf` files.

> Note: All required `.jar` libraries are already included in the project directory under the `Other files/` folder.

---

Step-by-Step Instructions:

1. Download and unzip the submitted zip folder.
2. Open IntelliJ IDEA (or any Java IDE of your choice).
3. Open the Project:
   - Go to File → Open → Select the unzipped project folder.
4. Ensure External Libraries are Linked:
   Add the following `.jar` files to the project:
   - `poi-5.2.2.jar`
   - `poi-ooxml-5.2.2.jar`
   - `pdfbox-app-2.0.33.jar`
   - (Plus other libraries inside the `Other files/` folder)

   (In IntelliJ: File → Project Structure → Libraries → Add JARs.)

5. Run the Program:
   - Right-click on `controller/Main.java`.
   - Click Run 'Main.main()'.

---

Program Overview:

---

Candidate Mode:
- Enter email ID.
- View available job postings.
- Select a job.
- Upload CV (`.txt`, `.docx`, or `.pdf` format). Give the path of the CV
- Get a match percentage based on required skills.

---

Recruiter Mode:
- Log in (Password: `123`).
- Add new job postings.
- View ranked candidates based on their match scores.

---

Data Handling:
- All data (Jobs and CVs) is stored temporarily during the program run.
- No external database is used.

---

Skill Extraction:
- The program detects skills from CVs based on a predefined list:
  - Java, Python, HTML, CSS, SQL, JavaScript, Spring, C++.

---

Special Notes:

---

- CVs must be in `.txt`, `.docx`, or `.pdf` format.
- If an unsupported format is uploaded, the system will throw an error.
- The program must be restarted to reset candidate/job data (since there is no database).
- This is a console-based application (no GUI elements).
- Recruiter password is hardcoded as: `123`.

---

Additional Comments:

---

- This program is inspired by the TrackGenesis live brief but is built only for university coursework.
- Advanced NLP techniques (like spaCy) mentioned in the report are not implemented, since this Java project focuses on basic functionality as per coursework requirements.

---

Important:

---

- Student ID (`// 2370967`) is included as a comment inside all source code files.
- Do not rename or move folders after unzipping, or it might break the project structure.

---

End of ReadMe

---
