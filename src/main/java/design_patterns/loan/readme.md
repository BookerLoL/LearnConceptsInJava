# Loan Design Pattern

- Functional Design Pattern
- Lender Lendee Pattern

- Ensures resource has been disposed of once out of scope

  - Caller can pass their objects method to manage state, instead of the user

- [Source](https://www.arabicprogrammer.com/article/8220607420/)

```java
public class ResourceLender {
    public interface WriteBlock {
        void call(BufferedWriter writer) throws IOException;
    }

    public interface ReadBlock {
        void call(BufferedReader reader) throws IOException;
    }

    public static void writeUsing(String fileName, WriteBlock block)
            throws IOException {
        File csvFile = new File(fileName);
        if (!csvFile.exists()) {
            csvFile.createNewFile();
        }
        FileWriter fw = new FileWriter(csvFile.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        block.call(bufferedWriter);
        bufferedWriter.close();
    }

    public static void readUsing(String fileName, ReadBlock block)
            throws IOException {
        File inputFile = new File(fileName);
        FileReader fileReader = new FileReader(inputFile.getAbsoluteFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        block.call(bufferedReader);
        bufferedReader.close();
    }

    public void writeColumnNameToMetaFile(final String attrName, String fileName, final String[] colNames) throws IOException {
        writeUsing(fileName,
                out -> {
                    StringBuilder buffer = new StringBuilder();
                    for (String string : colNames) {
                        buffer.append(string);
                        buffer.append(",");
                    }
                    out.append(attrName + " = " + buffer.toString());
                    out.newLine();
                });
    }
}
```
