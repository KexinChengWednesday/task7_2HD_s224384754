package task7_2_HD_s224384754;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class TodoService {
    private final Map<Long, Todo> todos = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Todo create(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        long id = idGenerator.getAndIncrement();
        Todo todo = new Todo(id, title, false);
        todos.put(id, todo);
        return todo;
    }

    public List<Todo> getAll() {
        return new ArrayList<>(todos.values());
    }

    public Todo getById(long id) {
        Todo todo = todos.get(id);
        if (todo == null) {
            throw new NoSuchElementException("Todo not found: " + id);
        }
        return todo;
    }

    public Todo complete(long id) {
        Todo todo = getById(id);
        todo.setCompleted(true);
        return todo;
    }

    public boolean delete(long id) {
        return todos.remove(id) != null;
    }

    public int count() {
        return todos.size();
    }
}