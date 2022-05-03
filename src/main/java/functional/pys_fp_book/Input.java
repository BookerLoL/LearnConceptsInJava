package functional.pys_fp_book;

public interface Input {
    Result<Tuple<String, Input>> readString();
    Result<Tuple<Integer, Input>> readInt();
    default Result<Tuple<String, Input>> readString(String message) {
        return readString();
    }

    default Result<Tuple<Integer, Input>> readInt(String message) {
        return readInt();
    }

    //Could create AbstractReader, ConsoleReader
}
