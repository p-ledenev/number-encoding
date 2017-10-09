package task.nuber.encoding.testing;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.stream.IntStream;

public class GenerateStressTestsPhoneFiles {

    @Test
    @Ignore
    public void generate() throws IOException {
        Random random = new Random();
        Path path = Paths.get("F:/phones.txt");
        try {
            Files.deleteIfExists(path);
        } catch (Exception ignored) {

        }
        for (int i = 0; i < 100_000; i++) {
            Files.write(
                    path,
                    (buildPhone(random) + "\n").getBytes(),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE
            );
        }
    }

    private String buildPhone(Random random) {
        return IntStream.range(0, 50)
                .boxed()
                .map(i -> random.nextInt(9))
                .map(Object::toString)
                .reduce("", (i1, i2) -> i1 + i2);
    }
}
