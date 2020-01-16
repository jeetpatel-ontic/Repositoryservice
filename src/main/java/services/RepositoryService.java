package services;

import java.util.List;

public interface RepositoryService {
    List<Object> fetchItem(Object query);

    boolean upsertItem(Object query);

    boolean isPresent(Object query);

    boolean removeItem(Object query);

    void showItems();
}
