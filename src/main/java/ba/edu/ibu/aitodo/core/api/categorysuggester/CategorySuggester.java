package ba.edu.ibu.aitodo.core.api.categorysuggester;

import ba.edu.ibu.aitodo.core.model.Task;

public interface CategorySuggester {
    String suggestCategory(String description);
}
