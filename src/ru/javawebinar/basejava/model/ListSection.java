package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> stories = new ArrayList<>();

    public void addStory(String story) {
        if (story != null) stories.add(story);
    }

    public List<String> getStories() {
        return new ArrayList<>(stories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(stories, that.stories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stories);
    }

    @Override
    public String toString() {
        return "StorySection{" +
                "stories=" + stories +
                "} " + super.toString();
    }
}
