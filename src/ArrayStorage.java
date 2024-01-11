import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int countResumes;

    void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    void save(Resume r) {
        storage[countResumes] = r;
        countResumes++;
    }

    Resume get(String uuid) {
        return Arrays.stream(storage)
                .limit(countResumes)
                .filter(r -> uuid.equals(r.uuid))
                .findFirst().orElse(null);
    }

    void delete(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].uuid)) {
                storage[i] = storage[countResumes - 1];
                storage[countResumes] = null;
                countResumes--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    int size() {
        return countResumes;
    }
}
