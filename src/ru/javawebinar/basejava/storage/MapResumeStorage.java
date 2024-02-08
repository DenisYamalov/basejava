package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {
    @Override
    protected Resume getSearchKey(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return resumeMap.containsValue(searchKey);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return resumeMap.get(searchKey.getUuid());
    }

    @Override
    protected void doSave(Resume searchKey, Resume r) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume r) {
        resumeMap.replace(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Resume searchKey) {
        resumeMap.remove(searchKey.getUuid());
    }
}
