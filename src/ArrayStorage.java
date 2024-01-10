import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int counter = 0;

    void clear() {
        for (int i = 0; i < counter; i++) {
            storage[i] = null;
        }
        counter = 0;
    }

    void save(Resume r) {
        storage[counter] = r;
        counter++;
    }

    Resume get(String uuid) {

        return Arrays.stream(storage)
                .filter(Objects::nonNull)
                .filter(r -> uuid.equals(r.uuid))
                .findFirst().orElse(null);
    }

    void delete(String uuid) {

        int deleteIndex = -1;

        for (int i = 0; i < counter; i++) {
            if (uuid.equals(storage[i].uuid)) {
                deleteIndex = i;
            }
        }

        if (deleteIndex >= 0) {
            for (int i = deleteIndex + 1; i < counter; i++) {
                storage[i - 1] = storage[i];
                storage[i] = null;
            }
            counter--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, counter);
    }

    int size() {
        return counter;
    }
}
